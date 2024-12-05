package com.mobiliteitsfabriek.ovapp.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.Station;

public class StationService {
    private static ArrayList<Station> stations;

    static {
        StationService.stations = geStationsFromFile(GlobalConfig.FILE_PATH_STATIONS);
    }

    private static ArrayList<Station> geStationsFromFile(String filePath) {
        ArrayList<Station> stations = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JSONArray stationsArray = new JSONArray(new JSONTokener(reader));

            for (int i = 0; i < stationsArray.length(); i++) {
                JSONObject jsonObject = stationsArray.getJSONObject(i);

                // Json values to java classes
                JSONObject idObject = jsonObject.getJSONObject("id");
                String id = idObject.getString("code");

                JSONObject namesObject = jsonObject.getJSONObject("names");
                String name = namesObject.getString("long");
                String country = jsonObject.getString("country");

                stations.add(new Station(id, name, country));
            }
            return stations;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }

    public List<Station> getStationByName(String name) {
        Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        List<Station> result = stations.stream().filter((station -> pattern.matcher(station.getName()).find())).sorted((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName())).toList();
        return result;
    }

    public List<String> getAllStationNames() {
        return stations.stream()
                .map(Station::getName)
                .sorted(String::compareToIgnoreCase)
                .toList();
    }

    public Station getStation(String name) {
        if (UtilityFunctions.checkEmpty(name)) {
            return null;
        }
        Optional<Station> stationValidation = stations.stream()
                .filter(station -> station.getName().equalsIgnoreCase(name))
                .findFirst();
        return stationValidation.orElse(null);
    }

}
