package com.mobiliteitsfabriek.ovapp.ui.pages;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;
import com.mobiliteitsfabriek.ovapp.ui.enums.SearchFieldStationTypes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class HomePage {
    public static Scene getScene() {
        VBox root = new VBox();
        root.getStyleClass().add("container");

        SearchFieldStation startStationField = new SearchFieldStation(StationService.getAllStationNames(), SearchFieldStationTypes.DEPARTURE_DESTINATION);
        SearchFieldStation endStationField = new SearchFieldStation(StationService.getAllStationNames(), SearchFieldStationTypes.FINAL_DESTINATION);
        Button submitBtn = new Button("Zoek");
        Button swapBtn = new Button("<->");

        startStationField.getStyleClass().add("station-field");
        endStationField.getStyleClass().add("station-field");
        submitBtn.getStyleClass().add("submit-btn");
        swapBtn.getStyleClass().add("submit-btn");

        swapBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            String startValue = startStationField.getValue();
            String endValue = endStationField.getValue();

            startStationField.safeSetValue(endValue);
            endStationField.safeSetValue(startValue);

            startStationField.hide();
            endStationField.hide();
        });

        submitBtn.setOnAction(event -> {
            RoutesPage.handleSearch(startStationField, endStationField);
        });

        root.getChildren().addAll(startStationField, endStationField, swapBtn, submitBtn);
        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(HomePage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }
}
