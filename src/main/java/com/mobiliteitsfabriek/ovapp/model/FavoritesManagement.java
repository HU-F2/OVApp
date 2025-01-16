package com.mobiliteitsfabriek.ovapp.model;

import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.ExistingFavoriteException;
import com.mobiliteitsfabriek.ovapp.exceptions.InvalidRouteException;
import com.mobiliteitsfabriek.ovapp.exceptions.MatchingStationsException;
import com.mobiliteitsfabriek.ovapp.exceptions.NotLoggedInFavoritePermissionException;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.general.ValidationFunctions;
import com.mobiliteitsfabriek.ovapp.service.FavoriteService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class FavoritesManagement {

    public static void addFavorite(String startStation, String endStation, String routeId) throws InvalidRouteException, MatchingStationsException, ExistingFavoriteException, NotLoggedInFavoritePermissionException {
        Favorite newFavorite = ValidationFunctions.validateFavoriteRoute(startStation, endStation, routeId);

        if (GlobalConfig.DEBUG_FAVORITE) {
            System.out.println(TranslationHelper.get("debug.addFavorite", startStation, endStation));
        }
        FavoriteService.saveFavorite(newFavorite);
    }

    public static void deleteFavorite(String routeId) throws NotLoggedInFavoritePermissionException {
        FavoriteService.deleteFavorite(routeId);
    }

    public static Favorite getFavoriteIfUnique(String startValue, String endValue, String routeId) throws NotLoggedInFavoritePermissionException {
        if (!UserManagement.userLoggedIn()) {
            throw new NotLoggedInFavoritePermissionException();
        }

        ArrayList<Favorite> favorites = FavoriteService.loadFavorites();
        User user = UserManagement.getLoggedInUser();

        boolean exists = favorites.stream().anyMatch(fav -> fav.getRouteId().equals(routeId) && fav.getUserId().equals(user.getId()));
        if (exists) {
            return null;
        }

        return new Favorite(UtilityFunctions.generateID(), routeId, user.getId(), startValue, endValue);
    }

    public static boolean favoriteExists(String routeId) throws NotLoggedInFavoritePermissionException {
        if (!UserManagement.userLoggedIn()) {
            throw new NotLoggedInFavoritePermissionException();
        }
        ArrayList<Favorite> favorites = FavoriteService.loadFavorites();
        User user = UserManagement.getLoggedInUser();
        return favorites.stream().filter((fav) -> fav.getRouteId().equals(routeId) && fav.getUserId().equals(user.getId())).findFirst().isPresent();
    }
}