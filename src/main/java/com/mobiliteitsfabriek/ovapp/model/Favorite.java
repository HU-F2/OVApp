package com.mobiliteitsfabriek.ovapp.model;

import java.util.Objects;

public class Favorite {
    private String startStation;
    private String endStation;

    public Favorite(String startStation, String endStation) {
        this.startStation = startStation;
        this.endStation = endStation;
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

    @Override
    public String toString() {
        return "FavoriteRoute [startStation=" + startStation + ", endStation=" + endStation + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite that = (Favorite) o;
        return startStation.equals(that.startStation) && endStation.equals(that.endStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startStation, endStation);
    }
}
