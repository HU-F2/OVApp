package com.mobiliteitsfabriek.ovapp.ui.pages;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.service.FavoriteService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.ui.components.DateTimePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.DepartureTimeToggleButton;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage {
    public static Scene getScene() {
        DateTimePicker dateTimeComponent = new DateTimePicker(true);
        DepartureTimeToggleButton departureToggleComponent = new DepartureTimeToggleButton();

        VBox root = new VBox();
        root.getStyleClass().add("container");

        SearchFieldStation startStationField = new SearchFieldStation(StationService.getAllStationNames(), "begin");
        SearchFieldStation endStationField = new SearchFieldStation(StationService.getAllStationNames(), "eind");
        Button submitBtn = new Button("Zoek");
        Button swapBtn = new Button("<->");

        Button favoriteBtn = new Button("☆");
        favoriteBtn.getStyleClass().add("favorite-btn");

        System.out.println("Favorite button created: " + (favoriteBtn != null));

        Button favoritesPageBtn = new Button("Favorites");
        favoritesPageBtn.getStyleClass().add("submit-btn");

        favoriteBtn.setOnAction(event -> {
            String startValue = startStationField.getValue();
            String endValue = endStationField.getValue();

            if (startValue != null && !startValue.isEmpty() && endValue != null && !endValue.isEmpty()) {
                FavoriteService.saveFavorite(startValue, endValue);
                System.out.println("Favorited route: " + startValue + " -> " + endValue);
            } else {
                System.out.println("Start and end stations must be selected.");
            }
        });
        System.out.println("Button created and event handler attached!");

        startStationField.getStyleClass().add("station-field");
        endStationField.getStyleClass().add("station-field");
        submitBtn.getStyleClass().add("submit-btn");
        swapBtn.getStyleClass().add("submit-btn");

        swapBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String startValue = startStationField.getValue().replace("'", "’");
            String endValue = endStationField.getValue().replace("'", "’");

            startStationField.setValue(endValue);
            endStationField.setValue(startValue);

            if (endValue != null) {
                startStationField.getSelectionModel().select(endValue);
            }

            if (startValue != null) {
                endStationField.getSelectionModel().select(startValue);
            }

            startStationField.hide();
            endStationField.hide();
        });

        submitBtn.setOnAction(event -> {
            RoutesPage.handleSearch(startStationField, endStationField, dateTimeComponent,
                    departureToggleComponent.isToggleDeparture());
        });

        favoritesPageBtn.setOnAction(event -> {
            System.out.println("Navigating to Favorites Page");
        
            FavoritePage favoritePage = new FavoritePage();
        
            Scene favoritesScene = new Scene(favoritePage, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        
            Stage primaryStage = (Stage) root.getScene().getWindow();
            primaryStage.setScene(favoritesScene);
        });
        

        root.getChildren().addAll(startStationField, endStationField, dateTimeComponent,
                departureToggleComponent.departureToggleButton(), swapBtn, submitBtn, favoriteBtn, favoritesPageBtn);
        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(HomePage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }
}
