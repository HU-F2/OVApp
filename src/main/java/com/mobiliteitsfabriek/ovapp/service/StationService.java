package com.mobiliteitsfabriek.ovapp.service;

import com.mobiliteitsfabriek.ovapp.model.Station;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class StationService {

    private List<Station> stations = new ArrayList<>();

    public StationService(){
        try (FileReader reader = new FileReader("src/main/resources/stations.json")){
            JSONArray stationsArray = new JSONArray(new JSONTokener(reader));
            for(int i = 0; i < stationsArray.length(); i++){
                JSONObject jsonObject = stationsArray.getJSONObject(i);

                // Json values to java classes
                JSONObject idObject = jsonObject.getJSONObject("id");
                String id = idObject.getString("code");

                JSONObject namesObject = jsonObject.getJSONObject("names");
                String name = namesObject.getString("long");
                String country = jsonObject.getString("country");

                stations.add(new Station(id, name, country));
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public List<Station> getStationByName(String name){
        Pattern pattern = Pattern.compile(name,Pattern.CASE_INSENSITIVE);
        List<Station> result = stations.stream().filter((station ->pattern.matcher(station.getName()).find())).sorted((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName())).toList();
        return result;
    }

    public List<String> getAllStationNames() {
        return stations.stream()
                .map(Station::getName)
                .sorted(String::compareToIgnoreCase)
                .toList();
    }

    public Station getStation(String name){
        Optional<Station> stationValidation = stations.stream().filter((station)->station.getName().equals(name)).findFirst();
        if(stationValidation.isPresent()){
            return stationValidation.get();
        }
        return null;
    }

}
