package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.time.LocalDate;
import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingFieldException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoStationFoundException;
import com.mobiliteitsfabriek.ovapp.exceptions.SameStationsSearchException;
import com.mobiliteitsfabriek.ovapp.general.ValidationFunctions;
import com.mobiliteitsfabriek.ovapp.model.Pair;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.service.RouteService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.DateTimePicker;
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
    public static Scene getScene(String startStation, String endStation, ArrayList<Route> routes, LocalDate defaultDate, String defaultTime) {
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
        SearchFieldStation startStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.start"), startStation);
        SearchFieldStation endStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.end"), endStation);
        locationContainer.getChildren().addAll(startStationField, endStationField);
        centerContainer.getChildren().addAll(dateTimeContainer, locationContainer);
        centerContainer.setAlignment(Pos.CENTER);

        DateTimePicker dateTimePicker = new DateTimePicker(true);
        // Search again
        Button searchButton = new Button(TranslationHelper.get("app.common.search"));
        searchButton.getStyleClass().add("submit-btn");
        searchButton.setOnAction(event -> {
            try {
                handleSearch(startStationField.getValidValueString(), endStationField.getValidValueString(), dateTimePicker, false);
            } catch (MissingFieldException | SameStationsSearchException | NoStationFoundException e) {
                System.out.println(e.getMessage());
            }
        });
        headerContainer.getChildren().addAll(backButton, centerContainer, searchButton);
        HBox.setHgrow(centerContainer, Priority.ALWAYS);
        headerContainer.getStyleClass().add("header-container");

        root.getChildren().addAll(headerContainer);

        for (int i = 0; i < routes.size() - 1; i++) {
            Route route = routes.get(i);
            RouteElement routeElement = new RouteElement(route, i == routes.size() - 2, routes);
            root.getChildren().addAll(routeElement);
        }

        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(RoutesPage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }

    public static void handleSearch(String startName, String endName, DateTimePicker dateTimePicker, boolean isToggleDeparture) throws MissingFieldException, SameStationsSearchException, NoStationFoundException {
        Pair<Station> stations = ValidationFunctions.validateSearch(startName, endName);

        String startStationId = stations.getFirstObject().getId();
        String endStationId = stations.getFirstObject().getId();

        LocalDate selectedDate = dateTimePicker.getDatePicker().getValue();
        String selectedTime = dateTimePicker.getTimeSpinner().getValue();

        ArrayList<Route> newRoutes = RouteService.getRoutes(startStationId, endStationId, dateTimePicker.getDateTimeRFC3339Format(), isToggleDeparture);
        Scene routesPage = RoutesPage.getScene(startName, endName, newRoutes, selectedDate, selectedTime);
        OVAppUI.switchToScene(routesPage);
    }

    public static void handleBackButton(ActionEvent event) {
        Scene homePage = HomePage.getScene();

        OVAppUI.switchToScene(homePage);
    }
}
