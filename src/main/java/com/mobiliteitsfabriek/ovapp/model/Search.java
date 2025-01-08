package com.mobiliteitsfabriek.ovapp.model;

import java.time.LocalDateTime;

public class Search {
    private Station startStation;
    private Station endStation;

    private boolean isArrival;
    private LocalDateTime selectedDate;

    public Search(Station startStation, Station endStation, boolean isArrival, LocalDateTime selectedDate) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.isArrival = isArrival;
        this.selectedDate = selectedDate;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public String getStartName() {
        return startStation.getName();
    }

    public String getEndName() {
        return endStation.getName();
    }

    public boolean isArrival() {
        return isArrival;
    }

    public LocalDateTime getSelectedDate() {
        return selectedDate;
    }
}
