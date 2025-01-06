package com.mobiliteitsfabriek.ovapp.service;

public class ValidStationHandler implements StationHandler {

    @Override
    public void handle(String startStation, String endStation) {
        System.out.println("Valid stations - Adding to favorites: " + startStation + " to " + endStation);
        FavoriteService.saveFavorite(startStation, endStation);
    }
}