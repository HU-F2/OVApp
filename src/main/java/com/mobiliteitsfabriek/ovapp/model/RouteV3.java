package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RouteV3 {
    // TODO: get a single trip using ctxRecon
    private String ctxRecon;

    private String startLocation;
    private String endLocation;

    private Double cost;
    private ArrayList<RouteTransfersV3> routeTransfers;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int plannedDurationInMinutes;
    //FIXME: both startlocation and endLocation can have a platform number
    private String platformNumber;
    private int transfersAmount;

    public RouteV3(ArrayList<RouteTransfersV3> routeTransfers, String ctxRecon, String startLocation, String endLocation, LocalDateTime startDateTime, LocalDateTime endDateTime, int plannedDurationInMinutes, int transfersAmount, Double cost) {
        this.ctxRecon = ctxRecon;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.cost = cost;
        this.routeTransfers = routeTransfers;

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.plannedDurationInMinutes = plannedDurationInMinutes;
        this.transfersAmount = transfersAmount;
        this.platformNumber = null;
    }

    // GETTERS AND SETTERS
    public String getCtxRecon() {
        return ctxRecon;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public int getPlannedDurationInMinutes() {
        return plannedDurationInMinutes;
    }

    public Double getCost() {
        return cost;
    }

    public ArrayList<RouteTransfersV3> getRouteTransfers() {
        return routeTransfers;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public int getTransfersAmount() {
        return transfersAmount;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
