package com.mobiliteitsfabriek.ovapp.service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.RouteTransfers;

public class RouteService {
    private static final String BASE_URL = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips";

    public static List<Route> getRoutes(String startStationId, String endStationId, String dateTime, Boolean isArrival) {
        String queryParams = MessageFormat.format("?fromStation={0}&toStation={1}&dateTime={2}&searchForArrival={3}", startStationId, endStationId,dateTime,isArrival.toString());
        String data = GeneralService.sendApiRequest(BASE_URL, queryParams);

        if (data == null) {
            return null;
        }

        return parseRoutes(data);
    }

    private static List<Route> parseRoutes(String jsonString) {
        JSONArray tripsArray = new JSONObject(new JSONTokener(jsonString)).getJSONArray("trips");

        List<Route> routeList = new ArrayList<>();

        for (int i = 0; i < tripsArray.length(); i++) {
            JSONObject trip = tripsArray.getJSONObject(i);
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

            String platformNumber = firstOriginObject.optString("plannedTrack","");

            // TODO: kosten uit de kosten api ophalen en deze hier toevoegen.
            Double cost = null;

            ArrayList<RouteTransfers> routeTransfers = new ArrayList<>();
            for (int j = 0; j < legsArray.length(); j++) {
                JSONObject leg = legsArray.getJSONObject(j);

                String departureLocation = leg.getJSONObject("origin").getString("name");
                String arrivalLocation = leg.getJSONObject("destination").getString("name");

                String departureLocationDetails = leg.getJSONObject("origin").optString("plannedTrack","");
                String arrivalLocationDetails = leg.getJSONObject("destination").optString("plannedTrack","");

                LocalDateTime plannedDepartureDateTime = UtilityFunctions.getDateTimeFromNS(leg.getJSONObject("origin").getString("plannedDateTime"));
                LocalDateTime plannedArrivalDateTime = UtilityFunctions.getDateTimeFromNS(leg.getJSONObject("destination").getString("plannedDateTime"));

                String transportType = leg.getJSONObject("product").getString("longCategoryName");
                String transportName = leg.getJSONObject("product").getString("displayName");
                String transportDirection = leg.getString("direction");

                RouteTransfers routeTransfersV3 = new RouteTransfers(departureLocation, arrivalLocation, departureLocationDetails, arrivalLocationDetails, plannedDepartureDateTime, plannedArrivalDateTime, transportType, transportName, transportDirection);
                routeTransfers.add(routeTransfersV3);
            }

            Route myRoute = new Route(routeTransfers, ctxRecon, startLocation, endLocation, platformNumber, startDateTime, endDateTime, plannedDurationInMinutes, transfersAmount, cost);
            routeList.add(myRoute);
        }
        return routeList;
    }

}
