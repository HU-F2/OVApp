package com.mobiliteitsfabriek.ovapp.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

import com.mobiliteitsfabriek.ovapp.service.SeedingService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;

public class OVAppUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StationService stationService = new StationService();

        VBox root = new VBox();
        SearchFieldStation startStationField = new SearchFieldStation(stationService);
        SearchFieldStation endStationField = new SearchFieldStation(stationService);

        Button submitBtn = new Button("Zoek");

        root.getChildren().addAll(startStationField,endStationField,submitBtn);
        final Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets()
                .add(Objects.requireNonNull(getClass().getResource("/styles/styles.css"))
                        .toExternalForm());

        primaryStage.setTitle("OV App");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load data
        SeedingService service = new SeedingService();
        service.getAllStations();
    }
} 