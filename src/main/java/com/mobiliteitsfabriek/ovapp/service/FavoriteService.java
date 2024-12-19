package com.mobiliteitsfabriek.ovapp.service;

import com.mobiliteitsfabriek.ovapp.model.Favorite;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteService {

    private static final String FAVORITES_FILE_PATH = "src/main/resources/favorite.json"; 

    public static List<Favorite> loadFavorites() {
        List<Favorite> favorites = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FAVORITES_FILE_PATH))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            if (content.length() == 0 || content.toString().trim().startsWith("[")) {
                content.append("[]");
            }

            JSONArray jsonArray = new JSONArray(content.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String startStation = jsonObject.getString("startStation");
                String endStation = jsonObject.getString("endStation");
                favorites.add(new Favorite(startStation, endStation));
            }

        } catch (IOException e) {
            e.printStackTrace(); 
        }

        return favorites;
    }

    public static void saveFavorite(String startStation, String endStation) {
        List<Favorite> favorites = loadFavorites();

        Favorite newFavorite = new Favorite(startStation, endStation);

        if (!favorites.contains(newFavorite)) {
            favorites.add(newFavorite);
            saveFavoritesToFile(favorites);
        }
    }

    private static void saveFavoritesToFile(List<Favorite> favorites) {
        JSONArray jsonArray = new JSONArray();

        for (Favorite favorite : favorites) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startStation", favorite.getStartStation());
            jsonObject.put("endStation", favorite.getEndStation());
            jsonArray.put(jsonObject);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAVORITES_FILE_PATH))) {
            writer.write(jsonArray.toString(4));
            System.out.println("Favorites saved to file at " + FAVORITES_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
