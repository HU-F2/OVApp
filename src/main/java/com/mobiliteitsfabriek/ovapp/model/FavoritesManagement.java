package com.mobiliteitsfabriek.ovapp.model;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.ExistingFavoriteException;
import com.mobiliteitsfabriek.ovapp.exceptions.InvalidRouteException;
import com.mobiliteitsfabriek.ovapp.exceptions.MatchingStationsException;
import com.mobiliteitsfabriek.ovapp.general.ValidationFunctions;
import com.mobiliteitsfabriek.ovapp.service.FavoriteService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class FavoritesManagement {

    public static void addFavorite(String startStation, String endStation) throws InvalidRouteException, MatchingStationsException, ExistingFavoriteException {
        Favorite newFavorite = ValidationFunctions.validateFavoriteRoute(startStation, endStation);

        if (GlobalConfig.DEBUG_FAVORITE) {
            System.out.println(TranslationHelper.get("debug.addFavorite", startStation, endStation));
        }
        FavoriteService.saveFavorite(newFavorite);
    }
}