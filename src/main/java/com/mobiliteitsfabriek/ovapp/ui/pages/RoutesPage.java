package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.enums.InputKey;
import com.mobiliteitsfabriek.ovapp.exceptions.DateInPastException;
import com.mobiliteitsfabriek.ovapp.exceptions.InputException;
import com.mobiliteitsfabriek.ovapp.exceptions.MatchingStationsException;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingFieldException;
import com.mobiliteitsfabriek.ovapp.exceptions.StationNotFoundException;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.general.ValidationFunctions;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.Search;
import com.mobiliteitsfabriek.ovapp.model.SearchManagement;
import com.mobiliteitsfabriek.ovapp.model.UserManagement;
import com.mobiliteitsfabriek.ovapp.service.RouteService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.DateTimePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.DepartureTimeToggleButton;
import com.mobiliteitsfabriek.ovapp.ui.components.FavoritePageButton;
import com.mobiliteitsfabriek.ovapp.ui.components.InputContainer;
import com.mobiliteitsfabriek.ovapp.ui.components.RouteElement;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class RoutesPage {

    public static Scene getScene(ArrayList<Route> routes, Search search) {
        if (UtilityFunctions.checkEmpty(search)) {
            throw new IllegalArgumentException();
        }
        Route firstRoute = routes.get(0);

        VBox root = new VBox();

        HBox headerContainer = new HBox();
        VBox navigationContainer = new VBox();
        // Backbutton
        Button backButton = new Button(TranslationHelper.get("app.common.back"));
        backButton.getStyleClass().add("submit-btn");
        backButton.setOnAction((event) -> handleBackButton(event));
        // Favorite button
        FavoritePageButton favoritePageBtn = new FavoritePageButton();
        favoritePageBtn.setWrapText(true);
        navigationContainer.getChildren().add(backButton);
        if(UserManagement.userLoggedIn()){
            navigationContainer.getChildren().add(favoritePageBtn);
        }
        navigationContainer.setSpacing(10);

        // Datetime picker
        DateTimePicker dateTimeContainer = new DateTimePicker(true);
        dateTimeContainer.getDatePicker().setValue(search.getSelectedDate().toLocalDate());
        dateTimeContainer.getTimeSpinner().getValueFactory().setValue(UtilityFunctions.formatTime(search.getSelectedDate()));

        Region departureSpacer = new Region();
        HBox.setHgrow(departureSpacer, Priority.ALWAYS);

        DepartureTimeToggleButton departureToggleComponent = new DepartureTimeToggleButton();

        // Locations
        VBox locationContainer = new VBox();
        SearchFieldStation startStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.start"), firstRoute.getStartLocation());
        startStationField.getStyleClass().add("station-field");
        SearchFieldStation endStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.end"), firstRoute.getEndLocation());
        endStationField.getStyleClass().add("station-field");
        InputContainer startContainer = new InputContainer(startStationField);
        InputContainer endContainer = new InputContainer(endStationField);
        locationContainer.getChildren().addAll(startContainer, endContainer);

        // Search again
        Button searchButton = new Button(TranslationHelper.get("app.common.search"));
        searchButton.getStyleClass().add("submit-btn");  // Apply larger button styling
        InputContainer submitContainer = new InputContainer(searchButton);
        searchButton.setOnAction(event -> {
            handleSearch(startStationField, endStationField, dateTimeContainer.getDateTimeRFC3339Format(), false, startContainer, endContainer, submitContainer);
        });

        locationContainer.setTranslateX(-40);
        // Search container
        HBox departureContainer = new HBox(dateTimeContainer, departureSpacer, departureToggleComponent.departureToggleButton());
        HBox searchContainer = new HBox(locationContainer, searchButton);
        VBox searchForm = new VBox(searchContainer,departureContainer);
        searchForm.setSpacing(15);

        HBox.setHgrow(departureContainer, Priority.ALWAYS);
        HBox.setHgrow(searchForm, Priority.ALWAYS);

        searchContainer.setAlignment(Pos.TOP_RIGHT);
        departureContainer.setAlignment(Pos.BOTTOM_RIGHT);

        headerContainer.getChildren().addAll(navigationContainer, searchForm);
        headerContainer.setSpacing(10);
        headerContainer.getStyleClass().add("header-container");

        root.getChildren().addAll(headerContainer);

        for (int i = 0; i < routes.size() - 1; i++) {
            Route route = routes.get(i);
            RouteElement routeElement = new RouteElement(route, i == routes.size() - 2, routes);
            root.getChildren().addAll(routeElement);
        }

        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH + 175, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(RoutesPage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }

    public static void handleSearch(SearchFieldStation startStationField, SearchFieldStation endStationField, String dateTimeRFC3339, boolean isToggleDeparture, InputContainer startContainer, InputContainer endContainer, InputContainer submitContainer) {
        try {
            startContainer.noError();
            endContainer.noError();
            submitContainer.noError();
            search(startStationField, endStationField, dateTimeRFC3339, isToggleDeparture);
        } catch (MissingFieldException | StationNotFoundException e) {
            setErrorFields(e, startContainer, endContainer);
        } catch (MatchingStationsException | DateInPastException e) {
            submitContainer.addError(e.getMessage());
        }
    }

    private static void setErrorFields(InputException exception, InputContainer startContainer, InputContainer endContainer) {
        if (exception.getInputKey().equals(InputKey.START_STATION)) {
            startContainer.addError(exception.getMessage());
        } else {
            endContainer.addError(exception.getMessage());
        }
    }

    private static void search(SearchFieldStation startStationField, SearchFieldStation endStationField, String dateTimeRFC3339, boolean isToggleDeparture) throws MissingFieldException, StationNotFoundException, MatchingStationsException, DateInPastException {
        String startName = startStationField.getEditor().textProperty().get().replace("’", "'");
        String endName = endStationField.getEditor().textProperty().get().replace("’", "'");

        Search search = ValidationFunctions.validateSearchRoute(startName, endName, UtilityFunctions.getLocalDateFromRFC3339String(dateTimeRFC3339), isToggleDeparture);

        SearchManagement.setCurrentSearch(search);
        ArrayList<Route> newRoutes = RouteService.getRoutes(search.getStartStation().getId(), search.getEndStation().getId(), dateTimeRFC3339, isToggleDeparture);
        Scene routesPage = RoutesPage.getScene(newRoutes, search);

        OVAppUI.switchToScene(routesPage);
    }

    public static void handleBackButton(ActionEvent event) {
        Scene homePage = HomePage.getScene();

        OVAppUI.switchToScene(homePage);
    }
}
