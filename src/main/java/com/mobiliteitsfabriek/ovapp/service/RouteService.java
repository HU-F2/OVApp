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
import com.mobiliteitsfabriek.ovapp.model.RouteTransfersV3;
import com.mobiliteitsfabriek.ovapp.model.RouteV3;

public class RouteService {
    private static final String BASE_URL = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips";

    public List<Route> getRoutes(String startStationId, String endStationId) {
        String queryParams = MessageFormat.format("?fromStation={0}&toStation={1}", startStationId, endStationId);
        String data = GeneralService.sendApiRequest(BASE_URL, queryParams);

        if (data == null) {
            return null;
        }

        return parseRoutes(data);
    }

    private List<Route> parseRoutes(String jsonString) {
        JSONArray tripsArray = new JSONObject(new JSONTokener(jsonString)).getJSONArray("trips");

        List<Route> result = new ArrayList<>();
        for (int i = 0; i < tripsArray.length(); i++) {
            JSONObject trip = tripsArray.getJSONObject(i);
            JSONArray legsArray = trip.getJSONArray("legs");
            JSONObject originFirstObject = legsArray.getJSONObject(0).getJSONObject("origin");
            JSONObject destinationLastObject = legsArray.getJSONObject(legsArray.length() - 1).getJSONObject("destination");

            LocalDateTime plannedDepartureTime = UtilityFunctions.getDateTimeFromNS(originFirstObject.getString("plannedDateTime"));
            LocalDateTime plannedArrivalTime = UtilityFunctions.getDateTimeFromNS(destinationLastObject.getString("plannedDateTime"));
            String departurePlatform = originFirstObject.optString("plannedTrack", "");

            int plannedDurationInMinutes = trip.getInt("plannedDurationInMinutes");
            int transfersAmount = trip.getInt("transfers");

            Route myRoute = new Route(plannedDurationInMinutes, transfersAmount, departurePlatform, plannedDepartureTime, plannedArrivalTime);
            result.add(myRoute);
        }
        return result;
    }

    // TODO: make a connection between the models, page's and API. 
    private List<RouteV3> parseRoutesV3(String jsonString) {
        JSONArray tripsArray = new JSONObject(new JSONTokener(jsonString)).getJSONArray("trips");

        List<RouteV3> result = new ArrayList<>();
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

            // TODO: kosten uit de kosten api ophalen en deze hier toevoegen.
            Double cost = null;

            ArrayList<RouteTransfersV3> routeTransfers = new ArrayList<>();
            for (int j = 0; j < legsArray.length(); j++) {
                JSONObject leg = legsArray.getJSONObject(i);

                String departureLocation = leg.getJSONObject("origin").getString("name");
                String arrivalLocation = leg.getJSONObject("destination").getString("name");

                String departureLocationDetails = leg.getJSONObject("origin").getString("plannedTrack");
                String arrivalLocationDetails = leg.getJSONObject("destination").getString("plannedTrack");

                LocalDateTime departureTime = UtilityFunctions.getDateTimeFromNS(leg.getJSONObject("origin").getString("plannedDateTime"));
                LocalDateTime arrivalTime = UtilityFunctions.getDateTimeFromNS(leg.getJSONObject("destination").getString("plannedDateTime"));

                String transportType = leg.getJSONObject("product").getString("longCategoryName");
                String transportName = leg.getJSONObject("product").getString("displayName");
                String transportDirection = leg.getString("direction");

                RouteTransfersV3 routeTransfersV3 = new RouteTransfersV3(departureLocation, arrivalLocation, departureLocationDetails, arrivalLocationDetails, departureTime, arrivalTime, transportType, transportName, transportDirection);
                routeTransfers.add(routeTransfersV3);
            }

            // RouteV3 myRoute = new RouteV3(startLocation, endLocation, cost, routeTransfers);
            RouteV3 myRoute = new RouteV3(routeTransfers, ctxRecon, startLocation, endLocation, startDateTime, endDateTime, plannedDurationInMinutes, transfersAmount, cost);
            result.add(myRoute);
        }
        return result;
    }

}
