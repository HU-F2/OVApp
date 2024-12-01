package com.mobiliteitsfabriek.ovapp.model;

import java.util.ArrayList;

public class RouteV3 {
    private String startLocation;
    private String endLocation;

    private double cost;
    private ArrayList<StopV3> stops;
    
    public RouteV3(String startLocation, String endLocation, double cost, ArrayList<StopV3> stops) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.cost = cost;
        this.stops = stops;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public double getCost() {
        return cost;
    }

    public ArrayList<StopV3> getStops() {
        return stops;
    }
}
