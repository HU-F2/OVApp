package com.mobiliteitsfabriek.ovapp.ui.pages;

import com.mobiliteitsfabriek.ovapp.service.RouteService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class HomePage {
    public static Scene getScene() {
        StationService stationService = new StationService();
        RouteService routeService = new RouteService();

        VBox root = new VBox();

        SearchFieldStation startStationField = new SearchFieldStation(stationService, stationService.getAllStationNames(), "begin");
        SearchFieldStation endStationField = new SearchFieldStation(stationService, stationService.getAllStationNames(), "eind");
        Button submitBtn = new Button("Zoek");

        submitBtn.setOnAction(event -> {
            RoutesPage.handleSearch(startStationField, endStationField, stationService, routeService);
        });

        root.getChildren().addAll(startStationField, endStationField, submitBtn);
        Scene scene = new Scene(root, 1200, 800);
        return scene;
    }
}
