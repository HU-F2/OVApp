package com.mobiliteitsfabriek.ovapp.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class OVAppUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        final BorderPane root = loader.load();

        final Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets()
                .add(Objects.requireNonNull(getClass().getResource("/styles/styles.css"))
                        .toExternalForm());

        primaryStage.setTitle("OV App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
} 