package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;

public class Route {
    private int duration;
    private int transfers;
    private String platformNumber;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Route(int duration, int transfers, String platformNumber, LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        this.duration = duration;
        this.transfers = transfers;
        this.platformNumber = platformNumber;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "Route [duration=" + duration + ", transfers=" + transfers + ", platformNumber=" + platformNumber
                + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + "]";
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

}
