package com.mobiliteitsfabriek.ovapp.ui.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.pages.RoutesPage;

import javafx.event.ActionEvent;
import javafx.scene.Scene;

public class RouteDetailController {
    private Route route;

    public RouteDetailController(Route route) {
        if (route == null) {
            throw new IllegalArgumentException("Route can't be null");
        }
        this.route = route;
    }

    public Route getRoute() {
        return this.route;
    }

    public void handleBackButton(ActionEvent actionEvent, ArrayList<Route> routes) {
        LocalDateTime date = LocalDateTime.now();
        String time = UtilityFunctions.formatTime(date);

        Scene routesPage = RoutesPage.getScene(routes, date.toLocalDate(), time);
        OVAppUI.switchToScene(routesPage);
    }
}
