package com.mobiliteitsfabriek.ovapp.service;

public class InvalidStationHandler implements StationHandler {

    @Override
    public void handle(String startStation, String endStation) {
        System.out.println("Invalid stations. Cannot add empty stations to favorites.");
    }
}
