package com.mobiliteitsfabriek.ovapp.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.service.RouteService;
import com.mobiliteitsfabriek.ovapp.service.SeedingService;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;
import com.mobiliteitsfabriek.ovapp.ui.pages.HomePage;

public class OVAppUI extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        final Scene scene = HomePage.getScene();
        scene.getStylesheets()
                .add(Objects.requireNonNull(getClass().getResource("/styles/styles.css"))
                        .toExternalForm());

        primaryStage.setTitle("OV App");
        primaryStage.setScene(scene);
        primaryStage.show();

        SeedingService service = new SeedingService();
        service.getAllStations();
    }

    public static void switchToScene(Scene scene){
        stage.setScene(scene);
    }


} 