package com.mobiliteitsfabriek.ovapp.service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.Route;

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

            LocalDateTime plannedDepartureTime = LocalDateTime.parse(originFirstObject.getString("plannedDateTime"), GlobalConfig.NS_DATE_TIME_FORMATTER);
            LocalDateTime plannedArrivalTime = LocalDateTime.parse(destinationLastObject.getString("plannedDateTime"), GlobalConfig.NS_DATE_TIME_FORMATTER);
            String departurePlatform = originFirstObject.optString("plannedTrack", "");

            int plannedDurationInMinutes = trip.getInt("plannedDurationInMinutes");
            int transfersAmount = trip.getInt("transfers");

            Route myRoute = new Route(plannedDurationInMinutes, transfersAmount, departurePlatform, plannedDepartureTime, plannedArrivalTime);
            result.add(myRoute);
        }
        return result;
    }

}
