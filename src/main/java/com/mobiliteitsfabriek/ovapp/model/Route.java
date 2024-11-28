package com.mobiliteitsfabriek.ovapp.model;

public class Route {
    private String duration;
    private String startStation;
    private String endStation;
    private int transfers;
    private String status;
    private String perronNumber;

    public Route(String duration, int transfers, String status, String perronNumber, String startStation, String endStation) {
        this.duration = duration;
        this.transfers = transfers;
        this.status = status;
        this.perronNumber = perronNumber;
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getTransfers() {
        return transfers;
    }

    public void setTransfers(int transfers) {
        this.transfers = transfers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPerronNumber() {
        return perronNumber;
    }

    public void setPerronNumber(String perronNumber) {
        this.perronNumber = perronNumber;
    }


}
