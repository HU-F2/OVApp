package com.mobiliteitsfabriek.ovapp.service;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.FarePrices;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.RouteTransfers;

public class RouteService {
    private static final String BASE_URL = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips";
    private static final String TRIP_BASE_URL = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips/trip";

    public static ArrayList<Route> getRoutes(String startStationId, String endStationId, String dateTime, Boolean isArrival) {
        String queryParams = MessageFormat.format("?fromStation={0}&toStation={1}&dateTime={2}&searchForArrival={3}", startStationId, endStationId, dateTime, isArrival.toString());
        String data = GeneralService.sendApiRequest(BASE_URL, queryParams);

        if (UtilityFunctions.checkEmpty(data)) {
            return new ArrayList<>();
        }

        return parseRoutes(data);
    }

    public static Route getRoute(String ctxRecon){
        try {
            String queryParams = MessageFormat.format("?ctxRecon={0}", URLEncoder.encode(ctxRecon, "UTF-8"));
            String data = GeneralService.sendApiRequest(TRIP_BASE_URL, queryParams);

            if (UtilityFunctions.checkEmpty(data)) {
                return null;
            }


            FarePrices farePrices = getFarePrice(data);
            JSONObject tripObject = new JSONObject(new JSONTokener(data));
            Route route = parseSingleRoute(tripObject, farePrices);
            return route;
        } catch (Exception exception) {
            return null;
        }
    }

    private static ArrayList<Route> parseRoutes(String jsonString) {
        JSONArray tripsArray = new JSONObject(new JSONTokener(jsonString)).getJSONArray("trips");

        ArrayList<Route> routeList = new ArrayList<>();
        FarePrices farePrices = null;

        for (int i = 0; i < tripsArray.length(); i++) {
            JSONObject trip = tripsArray.getJSONObject(i);
            if (farePrices == null) {
                farePrices = routePrice(trip.getString("ctxRecon"));
            }
            Route myRoute = parseSingleRoute(trip,farePrices);
            routeList.add(myRoute);
        }
        return routeList;
    }

    private static Route parseSingleRoute(JSONObject trip,FarePrices farePrices){
        JSONArray legsArray = trip.getJSONArray("legs");
        JSONObject firstOriginObject = legsArray.getJSONObject(0).getJSONObject("origin");
        JSONObject lastDestinationObject = legsArray.getJSONObject(legsArray.length() - 1).getJSONObject("destination");

        String ctxRecon = trip.getString("ctxRecon");

        String startLocation = firstOriginObject.getString("name");
        String endLocation = lastDestinationObject.getString("name");
        LocalDateTime startDateTime = UtilityFunctions.getDateTimeFromNS(firstOriginObject.getString("plannedDateTime"));
        LocalDateTime endDateTime = UtilityFunctions.getDateTimeFromNS(lastDestinationObject.getString("plannedDateTime"));

        int plannedDurationInMinutes = trip.getInt("plannedDurationInMinutes");
        int transfersAmount = trip.getInt("transfers");

        String departurePlatformNumber = firstOriginObject.optString("plannedTrack", "");

        ArrayList<RouteTransfers> routeTransfers = new ArrayList<>();
        for (int j = 0; j < legsArray.length(); j++) {
            JSONObject leg = legsArray.getJSONObject(j);

            String departureLocation = leg.getJSONObject("origin").getString("name");
            String arrivalLocation = leg.getJSONObject("destination").getString("name");

            String departureLocationDetails = leg.getJSONObject("origin").optString("plannedTrack", "");
            String arrivalLocationDetails = leg.getJSONObject("destination").optString("plannedTrack", "");

            LocalDateTime plannedDepartureDateTime = UtilityFunctions.getDateTimeFromNS(leg.getJSONObject("origin").getString("plannedDateTime"));
            LocalDateTime plannedArrivalDateTime = UtilityFunctions.getDateTimeFromNS(leg.getJSONObject("destination").getString("plannedDateTime"));

            String transportType = leg.getJSONObject("product").getString("longCategoryName");
            String transportName = leg.getJSONObject("product").getString("displayName");
            String transportDirection = leg.getString("direction");

            RouteTransfers routeTransfersV3 = new RouteTransfers(departureLocation, arrivalLocation, departureLocationDetails, arrivalLocationDetails, plannedDepartureDateTime, plannedArrivalDateTime, transportType, transportName, transportDirection);
            routeTransfers.add(routeTransfersV3);
        }

        return new Route(routeTransfers, ctxRecon, startLocation, endLocation, departurePlatformNumber, startDateTime, endDateTime, plannedDurationInMinutes, transfersAmount, farePrices);
    }

    private static FarePrices routePrice(String ctxRecon) {
        try {
            String queryParams = MessageFormat.format("?ctxRecon={0}", URLEncoder.encode(ctxRecon, "UTF-8"));
            String data = GeneralService.sendApiRequest(TRIP_BASE_URL, queryParams);

            if (UtilityFunctions.checkEmpty(data)) {
                return null;
            }

            return getFarePrice(data);
        } catch (Exception exception) {
            return null;
        }
    }

    public static FarePrices getFarePrice(String jsonString) {
        JSONArray faresArray = new JSONObject(new JSONTokener(jsonString)).getJSONArray("fares");

        int firstClassPriceInCents = 0;
        int secondClassPriceInCents = 0;

        for (int i = 0; i < faresArray.length(); i++) {
            JSONObject fare = faresArray.getJSONObject(i);

            Integer priceInCents = fare.getInt("priceInCents");
            String product = fare.getString("product");
            String travelClass = fare.getString("travelClass");
            String discountType = fare.getString("discountType");

            if (product.equals("OVCHIPKAART_ENKELE_REIS") && travelClass.equals("FIRST_CLASS") && discountType.equals("NO_DISCOUNT")) {
                firstClassPriceInCents = priceInCents;
            }

            if (product.equals("OVCHIPKAART_ENKELE_REIS") && travelClass.equals("SECOND_CLASS") && discountType.equals("NO_DISCOUNT")) {
                secondClassPriceInCents = priceInCents;
            }
        }

        return new FarePrices(firstClassPriceInCents, secondClassPriceInCents);
    }
}