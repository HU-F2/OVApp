package com.mobiliteitsfabriek.ovapp.ui.controllers;

import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.SearchManagement;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.pages.HomePage;
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
        if(routes == null){
            OVAppUI.switchToScene(HomePage.getScene());
            return;
        }
        Scene routesPage = RoutesPage.getScene(routes, SearchManagement.getCurrentSearch());
        OVAppUI.switchToScene(routesPage);
    }

}
