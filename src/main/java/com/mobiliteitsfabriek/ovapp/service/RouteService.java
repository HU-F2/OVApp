package com.mobiliteitsfabriek.ovapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.Station;

public class RouteService {
    
    private List<Route> routes = new ArrayList<>();

    public RouteService(){
        try (FileReader reader = new FileReader("src/main/resources/route.json")){
            JSONObject rootObject = new JSONObject(new JSONTokener(reader));
            JSONArray routesArray = rootObject.getJSONArray("trips");
            for(int i = 0; i < routesArray.length(); i++){
                JSONObject jsonObject = routesArray.getJSONObject(i);
                JSONArray legsArray = jsonObject.getJSONArray("legs");
                for (int j = 0; j < legsArray.length(); j++){
                    JSONObject legObject = legsArray.getJSONObject(j);
                    JSONObject startStationObject = legObject.getJSONObject("origin");
                    String startStationName = startStationObject.getString("name");
                    String departureTime = startStationObject.getString("plannedDateTime");
                    System.out.println(startStationName);
                    System.out.println(departureTime);
                }
                // JSONObject namesObject = jsonObject.getJSONObject("names");
                // String name = namesObject.getString("long");
                // String country = jsonObject.getString("country");

                // routes.add(new Route(id, name, country));
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
