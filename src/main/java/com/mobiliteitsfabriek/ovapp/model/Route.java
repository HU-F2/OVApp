package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;

public class Route {
    private String platformNumber;
    private int transfers;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int duration;

    public Route(int duration, int transfers, String platformNumber, LocalDateTime startDateTime, LocalDateTime endDateTime) {
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
}
