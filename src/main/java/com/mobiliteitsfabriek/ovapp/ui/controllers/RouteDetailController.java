package com.mobiliteitsfabriek.ovapp.ui.controllers;

import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.RouteV3;

import javafx.event.ActionEvent;

public class RouteDetailController {
    private Route route;
    private RouteV3 routeV3;

    public RouteDetailController(Route route) {
        if (route == null) {
            throw new IllegalArgumentException("Route can't be null");
        }
        this.route = route;
    }

    public RouteDetailController(RouteV3 route) {
        if (route == null) {
            throw new IllegalArgumentException("Route can't be null");
        }
        this.routeV3 = route;
    }

    public Route getRoute() {
        return this.route;
    }

    public RouteV3 getRouteV3() {
        return this.routeV3;
    }

    public void handleBackButton(ActionEvent actionEvent) {
        // TODO: implement back button handling
        throw new UnsupportedOperationException();
    }
}
