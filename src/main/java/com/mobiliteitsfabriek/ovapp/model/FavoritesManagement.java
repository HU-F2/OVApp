package com.mobiliteitsfabriek.ovapp.model;

import java.util.ArrayList;
import java.util.Optional;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.ExistingFavoriteException;
import com.mobiliteitsfabriek.ovapp.exceptions.InvalidRouteException;
import com.mobiliteitsfabriek.ovapp.exceptions.MatchingStationsException;
import com.mobiliteitsfabriek.ovapp.general.ValidationFunctions;
import com.mobiliteitsfabriek.ovapp.service.FavoriteService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class FavoritesManagement {

    public static void addFavorite(String startStation, String endStation, String routeId) throws InvalidRouteException, MatchingStationsException, ExistingFavoriteException {
        Favorite newFavorite = ValidationFunctions.validateFavoriteRoute(startStation, endStation, routeId);

        if (GlobalConfig.DEBUG_FAVORITE) {
            System.out.println(TranslationHelper.get("debug.addFavorite", startStation, endStation));
        }
        FavoriteService.saveFavorite(newFavorite);
    }

    public static void deleteFavorite(String routeId){
        ArrayList<Favorite> favorites = FavoriteService.loadFavorites();
        User user = UserManagement.getLoggedInUser();
        favorites.removeIf(favorite -> favorite.getRouteId().equals(routeId) && favorite.getUserId().equals(user.getId()));

        if(GlobalConfig.DEBUG_FAVORITE){
            System.out.println(TranslationHelper.get("debug.deleteFavorite", routeId));
        }

        FavoriteService.writeFavoritesToFile(favorites);
    }

    public static Favorite getFavoriteIfUnique(String startValue, String endValue, String routeId){
        ArrayList<Favorite> favorites = FavoriteService.loadFavorites();
        User user = UserManagement.getLoggedInUser();
        Optional<Favorite> newFavorite = favorites.stream().filter((fav)->fav.getRouteId().equals(routeId) && fav.getUserId().equals(user.getId())).findFirst();
        if(newFavorite.isPresent()){
            return null;
        }
        return new Favorite(routeId, user.getId(), startValue, endValue);
    }

    public static boolean favoriteExists(String routeId){
        ArrayList<Favorite> favorites = FavoriteService.loadFavorites();
        User user = UserManagement.getLoggedInUser();
        return favorites.stream().filter((fav)->fav.getRouteId().equals(routeId) && fav.getUserId().equals(user.getId())).findFirst().isPresent();
    }
}