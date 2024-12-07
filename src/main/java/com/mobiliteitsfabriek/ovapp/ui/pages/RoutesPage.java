package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.service.RouteService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.RouteElement;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;
import com.mobiliteitsfabriek.ovapp.ui.enums.SearchFieldStationTypes;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RoutesPage {
    public static Scene getScene(ArrayList<Route> routes) {
        Route firstRoute = routes.get(0);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        VBox root = new VBox();

        HBox headerContainer = new HBox();
        // Backbutton
        Button backButton = new Button("<--");
        backButton.getStyleClass().add("submit-btn");
        backButton.setOnAction((event) -> handleBackButton(event));
        // Center
        HBox centerContainer = new HBox();

        // Route date and time fields
        VBox dateTimeContainer = new VBox();
        Button timePicker = new Button("13:00"); // TODO: replace with future time picker element
        Button datePicker = new Button(firstRoute.getStartDateTime().format(dateFormatter)); // TODO: replace with future date picker element
        dateTimeContainer.getChildren().addAll(timePicker, datePicker);

        // Locations
        VBox locationContainer = new VBox();
        SearchFieldStation startStationField = new SearchFieldStation(StationService.getAllStationNames(), SearchFieldStationTypes.DEPARTURE_DESTINATION, firstRoute.getStartLocation());
        SearchFieldStation endStationField = new SearchFieldStation(StationService.getAllStationNames(), SearchFieldStationTypes.FINAL_DESTINATION, firstRoute.getEndLocation());
        locationContainer.getChildren().addAll(startStationField, endStationField);
        centerContainer.getChildren().addAll(dateTimeContainer, locationContainer);
        centerContainer.setAlignment(Pos.CENTER);

        // Search again
        Button searchButton = new Button("Zoek");
        searchButton.getStyleClass().add("submit-btn");
        searchButton.setOnAction(event -> {
            handleSearch(startStationField, endStationField);
        });
        headerContainer.getChildren().addAll(backButton, centerContainer, searchButton);
        HBox.setHgrow(centerContainer, Priority.ALWAYS);
        headerContainer.getStyleClass().add("header-container");

        root.getChildren().addAll(headerContainer);

        for (int i = 0; i < routes.size() - 1; i++) {
            Route route = routes.get(i);
            RouteElement routeElement = new RouteElement(route, i == routes.size() - 2);
            root.getChildren().addAll(routeElement);
        }

        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(RoutesPage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }

    public static void handleSearch(SearchFieldStation startStationField, SearchFieldStation endStationField) {
        String startName = startStationField.getEditor().textProperty().get();
        Station startStation = StationService.getStation(startName);
        if (startStation == null) {
            // TODO: Add error message
            return;
        }
        String endName = endStationField.getEditor().textProperty().get();
        Station endStation = StationService.getStation(endName);
        if (endStation == null) {
            // TODO: Add error message
            return;
        }

        ArrayList<Route> newRoutes = RouteService.getRoutes(startStation.getId(), endStation.getId());
        Scene routesPage = RoutesPage.getScene(newRoutes);
        OVAppUI.switchToScene(routesPage);
    }

    public static void handleBackButton(ActionEvent event) {
        Scene homePage = HomePage.getScene();

        OVAppUI.switchToScene(homePage);
    }
}
