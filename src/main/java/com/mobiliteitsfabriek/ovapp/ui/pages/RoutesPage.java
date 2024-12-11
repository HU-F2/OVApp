package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.time.LocalDate;
import java.util.List;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.service.RouteService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.DateTimePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.DepartureTimeToggleButton;
import com.mobiliteitsfabriek.ovapp.ui.components.RouteElement;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RoutesPage {
    public static Scene getScene(List<Route> routes, LocalDate defaultDate, String defaultTime) {
        StationService stationService = new StationService();
        RouteService routeService = new RouteService();

        Route firstRoute = routes.get(0);
        
        VBox root = new VBox();

        HBox headerContainer = new HBox();
        // Backbutton
        Button backButton = new Button(TranslationHelper.get("app.common.back"));
        backButton.getStyleClass().add("submit-btn");
        backButton.setOnAction((event) -> handleBackButton(event));
        // Center
        HBox centerContainer = new HBox();
        // Datetime picker
        DateTimePicker dateTimeContainer = new DateTimePicker();
        dateTimeContainer.getDatePicker().setValue(defaultDate);
        dateTimeContainer.getTimeSpinner().getValueFactory().setValue(defaultTime);

        // Locations
        VBox locationContainer = new VBox();
        SearchFieldStation startStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.start"), firstRoute.getStartLocation());
        SearchFieldStation endStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.end"), firstRoute.getEndLocation());
        locationContainer.getChildren().addAll(startStationField, endStationField);
        centerContainer.getChildren().addAll(dateTimeContainer, locationContainer);
        centerContainer.setAlignment(Pos.CENTER);

        DateTimePicker dateTimePicker = new DateTimePicker(true);
        DepartureTimeToggleButton departureTimeToggleButton = new DepartureTimeToggleButton();
        // Search again
        Button searchButton = new Button(TranslationHelper.get("app.common.search"));
        searchButton.getStyleClass().add("submit-btn");
        searchButton.setOnAction(event -> {
            handleSearch(startStationField, endStationField, stationService, routeService, dateTimePicker, departureTimeToggleButton);
        });
        headerContainer.getChildren().addAll(backButton, centerContainer, searchButton);
        HBox.setHgrow(centerContainer, Priority.ALWAYS);
        headerContainer.getStyleClass().add("header-container");

        root.getChildren().addAll(headerContainer);

        for (int i = 0; i < routes.size() - 1; i++) {
            Route route = routes.get(i);
            RouteElement routeElement = new RouteElement(route, i == routes.size() - 2,routes);
            root.getChildren().addAll(routeElement);
        }

        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(RoutesPage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }

    public static void handleSearch(SearchFieldStation startStationField, SearchFieldStation endStationField,StationService stationService, RouteService routeService, DateTimePicker dateTimePicker, DepartureTimeToggleButton departureTimeToggleButton){
        String startName = startStationField.getEditor().textProperty().get().replace("’","'");
        Station startStation = StationService.getStation(startName);
        if (startStation == null) {
            // TODO: Add error message
            return;
        }
        String endName = endStationField.getEditor().textProperty().get().replace("’","'");;
        Station endStation = StationService.getStation(endName);
        if (endStation == null) {
            // TODO: Add error message
            return;
        }

        if(startName.equalsIgnoreCase(endName)){
            // TODO: Add error message
            return;
        }

        LocalDate selectedDate = dateTimePicker.getDatePicker().getValue();
        String selectedTime = dateTimePicker.getTimeSpinner().getValue();
        List<Route> newRoutes = RouteService.getRoutes(startStation.getId(), endStation.getId(), dateTimePicker.getDateTimeRFC3339Format(), departureTimeToggleButton.isArrival());
        Scene routesPage = RoutesPage.getScene(newRoutes, selectedDate, selectedTime);
        
        OVAppUI.switchToScene(routesPage);
    }

    public static void handleBackButton(ActionEvent event) {
        Scene homePage = HomePage.getScene();

        OVAppUI.switchToScene(homePage);
    }
}
