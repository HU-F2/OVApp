package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Route {
    // TODO: get a single trip using ctxRecon
    private String ctxRecon;

    private String startLocation;
    private String endLocation;

    private Double cost;
    private ArrayList<RouteTransfers> routeTransfers;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int plannedDurationInMinutes;
    private String departurePlatformNumber;
    private int transfersAmount;

    public Route(ArrayList<RouteTransfers> routeTransfers, String ctxRecon, String startLocation, String endLocation, String departurePlatformNumber, LocalDateTime startDateTime, LocalDateTime endDateTime, int plannedDurationInMinutes, int transfersAmount, Double cost) {
        this.ctxRecon = ctxRecon;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.cost = cost;
        this.routeTransfers = routeTransfers;

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.plannedDurationInMinutes = plannedDurationInMinutes;
        this.transfersAmount = transfersAmount;
        this.departurePlatformNumber = departurePlatformNumber;
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

    public ArrayList<RouteTransfers> getRouteTransfers() {
        return routeTransfers;
    }

    public String getDeparturePlatformNumber() {
        return departurePlatformNumber;
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
