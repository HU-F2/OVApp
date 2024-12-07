package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;

public class Route {
    private String startStation;
    private String endStation;
    private int duration;
    private int transfers;
    private String platformNumber;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Route(String startStation, String endStation, int duration, int transfers, String platformNumber,
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.duration = duration;
        this.transfers = transfers;
        this.platformNumber = platformNumber;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

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

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

}
