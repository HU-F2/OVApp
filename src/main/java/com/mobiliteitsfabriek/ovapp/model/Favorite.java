package com.mobiliteitsfabriek.ovapp.model;

import java.util.Objects;

public class Favorite {
    // routeId is called ctxRecon in NSApi
    private final String routeId;
    private final String userId;
    private final String startStation;
    private final String endStation;

    public Favorite(String routeId, String userId, String startStation, String endStation) {
        this.routeId = routeId;
        this.userId = userId;
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public String getRouteId(){
        return routeId;
    }

    public String getUserId(){
        return userId;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    @Override
    public String toString() {
        return String.format("FavoriteRoute [startStation=%s, endStation=%s]", startStation, endStation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Favorite that = (Favorite) o;
        return startStation.equals(that.startStation) && endStation.equals(that.endStation) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startStation, endStation);
    }
}
