package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Route {
    private String platformNumber;
    private int transfers;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int duration;

    private ArrayList<Stop> stops;
    private int totalDuration;
    private LocalDateTime travelDate;
    private double totalCost;

    public Route(int duration, int transfers, String platformNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.duration = duration;
        this.transfers = transfers;
        this.platformNumber = platformNumber;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Route(ArrayList<Stop> stops, int totalDuration, LocalDateTime travelDate, double totalCost) {
        this.stops = stops;
        this.totalDuration = totalDuration;
        this.travelDate = travelDate;
        this.totalCost = totalCost;
    }

    // GETTERS AND SETTERS
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTransfers() {
        return transfers;
    }

    public void setTransfers(int transfers) {
        this.transfers = transfers;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String perronNumber) {
        this.platformNumber = perronNumber;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }
}
