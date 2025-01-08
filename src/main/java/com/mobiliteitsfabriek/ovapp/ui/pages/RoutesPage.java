package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.time.LocalDate;
import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.enums.InputKey;
import com.mobiliteitsfabriek.ovapp.exceptions.DateInPastException;
import com.mobiliteitsfabriek.ovapp.exceptions.MatchingStationsException;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingFieldException;
import com.mobiliteitsfabriek.ovapp.exceptions.StationNotFoundException;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.general.ValidationFunctions;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.Search;
import com.mobiliteitsfabriek.ovapp.service.RouteService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.DateTimePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.InputContainer;
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
    public static Scene getScene(ArrayList<Route> routes, LocalDate defaultDate, String defaultTime) {
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
        InputContainer startContainer = new InputContainer(startStationField);
        InputContainer endContainer = new InputContainer(endStationField);
        locationContainer.getChildren().addAll(startContainer, endContainer);
        centerContainer.getChildren().addAll(dateTimeContainer, locationContainer);
        centerContainer.setAlignment(Pos.CENTER);

        DateTimePicker dateTimePicker = new DateTimePicker(true);
        // Search again
        Button searchButton = new Button(TranslationHelper.get("app.common.search"));
        searchButton.getStyleClass().add("submit-btn");
        InputContainer submitContainer = new InputContainer(searchButton);
        searchButton.setOnAction(event -> {
            handleSearch(startStationField, endStationField, dateTimePicker, false,startContainer,endContainer,submitContainer);
        });
        headerContainer.getChildren().addAll(backButton, centerContainer, submitContainer);
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

    public static void handleSearch(SearchFieldStation startStationField, SearchFieldStation endStationField, DateTimePicker dateTimePicker, boolean isToggleDeparture,InputContainer startContainer, InputContainer endContainer, InputContainer submitContainer) {
        try {
            startContainer.noError();
            endContainer.noError();
            submitContainer.noError();
            search(startStationField, endStationField, dateTimePicker, isToggleDeparture);
        } catch (MissingFieldException | StationNotFoundException e) {
            if(e.getInputKey().equals(InputKey.START_STATION)){
                startContainer.addError(e.getMessage());
            }else if(e.getInputKey().equals(InputKey.END_STATION)){
                endContainer.addError(e.getMessage());
            }
        }catch(MatchingStationsException|DateInPastException e){
            submitContainer.addError(e.getMessage());
        }
    }

    private static void search(SearchFieldStation startStationField, SearchFieldStation endStationField, DateTimePicker dateTimePicker, boolean isToggleDeparture) throws MissingFieldException, StationNotFoundException, MatchingStationsException, DateInPastException{
        String startName = startStationField.getEditor().textProperty().get().replace("’", "'");
        String endName = endStationField.getEditor().textProperty().get().replace("’", "'");
        
        Search search = ValidationFunctions.validateSearchRoute(startName, endName,UtilityFunctions.getLocalDateFromRFC3339String(dateTimePicker.getDateTimeRFC3339Format()),isToggleDeparture);

        GlobalConfig.setCurrentSearch(search);

        LocalDate selectedDate = dateTimePicker.getDatePicker().getValue();
        String selectedTime = dateTimePicker.getTimeSpinner().getValue();
        ArrayList<Route> newRoutes = RouteService.getRoutes(search.getStartStation().getId(), search.getEndStation().getId(), dateTimePicker.getDateTimeRFC3339Format(), isToggleDeparture);
        Scene routesPage = RoutesPage.getScene(newRoutes, selectedDate, selectedTime);

        OVAppUI.switchToScene(routesPage);
    }

    public static void handleBackButton(ActionEvent event) {
        Scene homePage = HomePage.getScene();

        OVAppUI.switchToScene(homePage);
    }
}
