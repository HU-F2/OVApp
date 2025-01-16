package com.mobiliteitsfabriek.ovapp.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.NotLoggedInFavoritePermissionException;
import com.mobiliteitsfabriek.ovapp.model.Favorite;
import com.mobiliteitsfabriek.ovapp.model.User;
import com.mobiliteitsfabriek.ovapp.model.UserManagement;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

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
                favorites.add(new Favorite(jsonObject.getString("Id"), jsonObject.getString("routeId"), jsonObject.getString("userId"), jsonObject.getString("startStation"), jsonObject.getString("endStation")));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return favorites;
    }

    public static ArrayList<Favorite> loadFavoriteByUser() {
        ArrayList<Favorite> allFavorites = loadFavorites();
        User loggedInUser = UserManagement.getLoggedInUser();
        return allFavorites.stream().filter((favorite) -> favorite.getUserId().equals(loggedInUser.getId())).collect(Collectors.toCollection(ArrayList::new));
    }

    public static void saveFavorite(Favorite newFavorite) {
        ArrayList<Favorite> favorites = loadFavorites();
        favorites.add(newFavorite);
        writeFavoritesToFile(favorites);
    }

    public static void deleteFavorite(String routeId) throws NotLoggedInFavoritePermissionException {
        if (!UserManagement.userLoggedIn()) {
            throw new NotLoggedInFavoritePermissionException();
        }
        ArrayList<Favorite> favorites = FavoriteService.loadFavorites();
        User user = UserManagement.getLoggedInUser();
        favorites.removeIf(favorite -> favorite.getRouteId().equals(routeId) && favorite.getUserId().equals(user.getId()));

        if (GlobalConfig.DEBUG_FAVORITE) {
            System.out.println(TranslationHelper.get("debug.deleteFavorite", routeId));
        }

        FavoriteService.writeFavoritesToFile(favorites);
    }

    static void deleteFavoritesOfUser(String userID) {
        ArrayList<Favorite> favorites = loadFavorites();
        favorites.removeIf((favorite) -> favorite.getUserId().equals(userID));
        writeFavoritesToFile(favorites);
    }

    public static void writeFavoritesToFile(List<Favorite> favorites) {
        JSONArray jsonArray = new JSONArray();

        for (Favorite fav : favorites) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Id", fav.getId());
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
