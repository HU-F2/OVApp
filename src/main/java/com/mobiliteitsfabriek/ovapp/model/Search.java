package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;

public class Search {
    private Station startStation;
    private Station endStation;

    private String startName;
    private String endName;

    private boolean isArrival;
    private LocalDateTime selectedDate;

    public Search(Station startStation, Station endStation, boolean isArrival, LocalDateTime selectedDate) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.startName = startStation.getName();
        this.endName = endStation.getName();
        this.isArrival = isArrival;
        this.selectedDate = selectedDate;
    }

    public Search() {
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public boolean isArrival() {
        return isArrival;
    }

    public void setArrival(boolean isArrival) {
        this.isArrival = isArrival;
    }

    public LocalDateTime getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDateTime selectedDate) {
        this.selectedDate = selectedDate;
    }
}
