package com.mobiliteitsfabriek.ovapp.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobiliteitsfabriek.ovapp.model.Favorite;

public class FavoriteService {

    private static final String FAVORITES_FILE_PATH = "src/main/resources/favorite.json";

    public static ArrayList<Favorite> loadFavorites() {
        ArrayList<Favorite> favorites = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FAVORITES_FILE_PATH))) {
            String content = reader.lines().reduce("", String::concat);
            if (content.isEmpty())
                content = "[]";

            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                favorites.add(new Favorite(jsonObject.getString("routeId"),jsonObject.getString("userId"),jsonObject.getString("startStation"), jsonObject.getString("endStation")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return favorites;
    }

    public static void saveFavorite(Favorite newFavorite) {
        ArrayList<Favorite> favorites = loadFavorites();
        favorites.add(newFavorite);
        writeFavoritesToFile(favorites);
    }

    private static void writeFavoritesToFile(List<Favorite> favorites) {
        JSONArray jsonArray = new JSONArray();

        for (Favorite fav : favorites) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("routeId", fav.getRouteId());
            jsonObject.put("userId", fav.getUserId());
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
