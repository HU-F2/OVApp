package com.mobiliteitsfabriek.ovapp.ui.controllers;

import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.service.RouteService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
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

    public void handleBackButton(ActionEvent actionEvent) {
        Station startStation = StationService.getStation(getRoute().getStartLocation());
        Station endStation = StationService.getStation(getRoute().getEndLocation());

        if (UtilityFunctions.checkEmpty(startStation) || UtilityFunctions.checkEmpty(startStation)) {
            throw new IllegalArgumentException("Cannot go to route overview page, departure destination or final destination cannot be found.");
        }

        ArrayList<Route> newRoutes = RouteService.getRoutes(startStation.getId(), endStation.getId());
        Scene routesPage = RoutesPage.getScene(newRoutes);
        OVAppUI.switchToScene(routesPage);
    }
}
