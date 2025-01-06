package com.mobiliteitsfabriek.ovapp.ui.pages;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.service.InvalidStationHandler;
import com.mobiliteitsfabriek.ovapp.service.StationHandler;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.service.ValidStationHandler;
import com.mobiliteitsfabriek.ovapp.ui.components.DateTimePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.DepartureTimeToggleButton;
import com.mobiliteitsfabriek.ovapp.ui.components.LanguagePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;
import com.mobiliteitsfabriek.ovapp.ui.components.SwapButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage {
    private static VBox root;

    public static Scene getScene() {
        DateTimePicker dateTimeComponent = new DateTimePicker(true);
        DepartureTimeToggleButton departureToggleComponent = new DepartureTimeToggleButton();

        SearchFieldStation startStationField = new SearchFieldStation(StationService.getAllStationNames(),
                TranslationHelper.get("searchFieldStation.start"));
        SearchFieldStation endStationField = new SearchFieldStation(StationService.getAllStationNames(),
                TranslationHelper.get("searchFieldStation.end"));
        Button submitBtn = new Button(TranslationHelper.get("app.common.search"));

        Button favoriteBtn = new Button("☆");
        favoriteBtn.getStyleClass().add("favorite-btn");

        Button favoritesPageBtn = new Button("Favorites");
        favoritesPageBtn.getStyleClass().add("submit-btn");

        favoriteBtn.setOnAction(event -> {
            String startValue = startStationField.getValue();
            String endValue = endStationField.getValue();

            StationHandler stationHandler;

            if (startValue != null && !startValue.isEmpty() && endValue != null && !endValue.isEmpty()) {
                stationHandler = new ValidStationHandler();
            } else {
                stationHandler = new InvalidStationHandler();
            }

            stationHandler.handle(startValue, endValue);
        });

        favoritesPageBtn.setOnAction(event -> {
            if (root != null && root.getScene() != null) {
                Stage primaryStage = (Stage) root.getScene().getWindow();
                FavoritePage favoritePage = new FavoritePage();
                Scene favoritesScene = new Scene(favoritePage, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
                primaryStage.setScene(favoritesScene);
            } else {
                System.err.println("Error: Root is not initialized or scene is null.");
            }
        });

        startStationField.getStyleClass().add("station-field");
        endStationField.getStyleClass().add("station-field");
        submitBtn.getStyleClass().add("submit-btn");

        submitBtn.setOnAction(event -> {
            RoutesPage.handleSearch(startStationField, endStationField, dateTimeComponent,
                    departureToggleComponent.isArrival());
        });

        SwapButton swapBtn = new SwapButton(() -> {
            String startValue = startStationField.getValue();
            String endValue = endStationField.getValue();

            if (endValue != null) {
                endValue = endValue.replace("'", "’");
                startStationField.getSelectionModel().select(endValue);
            }

            if (startValue != null) {
                startValue = startValue.replace("'", "’");
                endStationField.getSelectionModel().select(startValue);
            }

            startStationField.setValue(endValue);
            endStationField.setValue(startValue);

            startStationField.hide();
            endStationField.hide();
        });
        swapBtn.setAccessibleText(TranslationHelper.get("home.swap.accessibleText"));

        VBox startWithEndStation = new VBox(10);
        startWithEndStation.getStyleClass().add("station-box");
        startWithEndStation.getChildren().addAll(startStationField, endStationField);
        startWithEndStation.setAlignment(Pos.CENTER);

        StackPane textFieldsWithButton = new StackPane(startWithEndStation, swapBtn);
        swapBtn.setTranslateX(175);

        LanguagePicker languagePicker = new LanguagePicker();
        HBox header = new HBox(languagePicker);
        header.setAlignment(Pos.TOP_RIGHT);
        header.setPadding(new Insets(0, 0, 50, 0));

        VBox mainContainer = new VBox(textFieldsWithButton, dateTimeComponent,
                departureToggleComponent.departureToggleButton(), submitBtn, favoriteBtn, favoritesPageBtn);
        mainContainer.getStyleClass().add("container");

        root = new VBox(header, mainContainer);
        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(HomePage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }
}
