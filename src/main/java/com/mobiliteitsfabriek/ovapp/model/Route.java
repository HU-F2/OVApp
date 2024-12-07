package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;

public class Route {
    private String startStation;
    private String endStation;
    private String platformNumber;
    private int transfers;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int duration;

    public Route(String startStation, String endStation, int duration, int transfers, String platformNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.duration = duration;
        this.transfers = transfers;
        this.platformNumber = platformNumber;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    // GETTERS AND SETTERS
    public int getDuration() {
        return duration;
    }

    public int getTransfers() {
        return transfers;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getEndStation() {
        return endStation;
    }

}
