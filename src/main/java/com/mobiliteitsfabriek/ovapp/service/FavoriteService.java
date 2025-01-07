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
            String content = reader.lines().reduce("", String::concat);
            if (content.isEmpty())
                content = "[]";

            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                favorites.add(new Favorite(jsonObject.getString("startStation"), jsonObject.getString("endStation")));
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
            writeFavoritesToFile(favorites);
        } else {
            System.out.println("This route is already added as a favorite.");
        }
    }

    private static void writeFavoritesToFile(List<Favorite> favorites) {
        JSONArray jsonArray = new JSONArray();

        for (Favorite fav : favorites) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("startStation", fav.getStartStation());
            jsonObject.put("endStation", fav.getEndStation());
            jsonArray.put(jsonObject);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAVORITES_FILE_PATH))) {
            writer.write(jsonArray.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
