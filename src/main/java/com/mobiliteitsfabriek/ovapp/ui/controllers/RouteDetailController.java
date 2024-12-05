package com.mobiliteitsfabriek.ovapp.ui.controllers;

import com.mobiliteitsfabriek.ovapp.model.RouteV3;

import javafx.event.ActionEvent;

public class RouteDetailController {
    private RouteV3 routeV3;

    public RouteDetailController(RouteV3 route) {
        if (route == null) {
            throw new IllegalArgumentException("Route can't be null");
        }
        this.routeV3 = route;
    }

    public RouteV3 getRouteV3() {
        return this.routeV3;
    }

    public void handleBackButton(ActionEvent actionEvent) {
        // TODO: implement back button handling
        throw new UnsupportedOperationException();
    }
}
