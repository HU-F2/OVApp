package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.util.List;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.service.RouteService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class HomePage {
    public static Scene getScene() {
        StationService stationService = new StationService();
        RouteService routeService = new RouteService();

        VBox root = new VBox();

        SearchFieldStation startStationField = new SearchFieldStation(stationService, stationService.getAllStationNames(), "begin");
        SearchFieldStation endStationField = new SearchFieldStation(stationService, stationService.getAllStationNames(), "eind");
        Button submitBtn = new Button("Zoek");

        submitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String startName = startStationField.getValue();
            Station startStation = stationService.getStation(startName);
            if (startStation == null) {
                // TODO: Add error message
                // Maybe by adding it to the SearchFieldStation
                return;
            }
            String endName = endStationField.getValue();
            Station endStation = stationService.getStation(endName);
            if (endStation == null) {
                // TODO: Add error message
                return;
            }

            List<Route> routes = routeService.getRoutes(startStation.getId(), endStation.getId());
            Scene routesPage = RoutesPage.getScene(routes);
            OVAppUI.switchToScene(routesPage);
        });

        root.getChildren().addAll(startStationField, endStationField, submitBtn);
        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        return scene;
    }
}
