package com.mobiliteitsfabriek.ovapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mobiliteitsfabriek.ovapp.model.Route;

public class RouteService {

    public List<Route> getRoutes(String startStationId, String endStationId) {
        String baseURL = "https://gateway.apiportal.ns.nl/reisinformatie-api/api/v3/trips";
        String urlString = baseURL + "?fromStation=" + startStationId + "&toStation=" + endStationId;

        try {
            URI uri = new URI(urlString);
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "57e1df724be741f6bc8f926355646bd5");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return parseRoutes(response.toString());

            } else {
                System.out.println("GET request failed.");
            }

        } catch (IOException | URISyntaxException e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    private List<Route> parseRoutes(String jsonString) {
        JSONArray tripsArray = new JSONObject(new JSONTokener(jsonString)).getJSONArray("trips");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

        List<Route> routeList = new ArrayList<>();
        for (int i = 0; i < tripsArray.length(); i++) {
            JSONObject trip = tripsArray.getJSONObject(i);
            JSONArray legsArray = trip.getJSONArray("legs");
            JSONObject originFirstObject = legsArray.getJSONObject(0).getJSONObject("origin");
            JSONObject destinationLastObject = legsArray.getJSONObject(legsArray.length() - 1).getJSONObject("destination");
            LocalDateTime plannedDepartureTime = LocalDateTime.parse(originFirstObject.getString("plannedDateTime"), formatter);
            LocalDateTime plannedArrivalTime = LocalDateTime.parse(destinationLastObject.getString("plannedDateTime"), formatter);
            String departurePlatform = originFirstObject.optString("plannedTrack", "");
            int plannedDurationInMinutes = trip.getInt("plannedDurationInMinutes");
            int transfersAmount = trip.getInt("transfers");

            String startStationName = originFirstObject.getString("name");
            String endStationName = destinationLastObject.getString("name");

            Route myRoute = new Route(startStationName,endStationName,plannedDurationInMinutes, transfersAmount, departurePlatform, plannedDepartureTime, plannedArrivalTime);
            routeList.add(myRoute);
        }
        return routeList;
    }

}
