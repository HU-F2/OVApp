package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.RouteV3;
import com.mobiliteitsfabriek.ovapp.model.Stop;
import com.mobiliteitsfabriek.ovapp.model.StopV3;
import com.mobiliteitsfabriek.ovapp.ui.controllers.RouteDetailController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RouteDetailPage {

    private final RouteDetailController controller;

    public RouteDetailPage(Route route) {
        this.controller = new RouteDetailController(route);
    }

    public RouteDetailPage(RouteV3 route) {
        this.controller = new RouteDetailController(route);
    }

    public Scene createRouteDetailSceneV2() {
        Route route = controller.getRoute();

        VBox mainContainer = new VBox(10);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: #ffffff;");

        // Titel
        Label titleLabel = new Label("Route Details");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setStyle("-fx-font-weight: bold;");
        mainContainer.getChildren().add(titleLabel);

        // Tijdlijn Container
        VBox timelineContainer = new VBox(20);
        timelineContainer.setPadding(new Insets(10, 20, 10, 20));
        timelineContainer.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 10;");
        mainContainer.getChildren().add(timelineContainer);

        // Stations en tijden
        for (int i = 0; i < route.getStops().size(); i++) {
            Stop stop = route.getStops().get(i);

            HBox stopContainer = new HBox(10);
            stopContainer.setAlignment(Pos.TOP_LEFT);

            // Tijd
            Label timeLabel = new Label(stop.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            timeLabel.setFont(Font.font("Arial", 16));
            stopContainer.getChildren().add(timeLabel);

            // Tussenruimte of lijn
            if (i > 0) {
                Line line = new Line(0, 0, 2, 50); // Lijn voor connectie
                line.setStroke(Color.GRAY);
                stopContainer.getChildren().add(line);
            } else {
                Circle circle = new Circle(5, Color.DARKBLUE);
                stopContainer.getChildren().add(circle);
            }

            // Station Details
            VBox stationDetails = new VBox(5);
            Label stationName = new Label(stop.getStationName());
            stationName.setFont(Font.font("Arial", 14));
            Label platformInfo = new Label("Spoor " + stop.getPlatform());
            platformInfo.setFont(Font.font("Arial", 12));
            platformInfo.setTextFill(Color.GRAY);
            stationDetails.getChildren().addAll(stationName, platformInfo);

            stopContainer.getChildren().add(stationDetails);

            timelineContainer.getChildren().add(stopContainer);
        }

        // Terugknop
        Label backButton = new Label("← Terug");
        backButton.setFont(Font.font("Arial", 16));
        backButton.setTextFill(Color.BLUE);
        backButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) mainContainer.getScene().getWindow();
            stage.setScene(new Scene(new Label("Terug naar routes pagina"))); // Voorbeeld: Terugkeren.
        });
        mainContainer.getChildren().add(backButton);

        return new Scene(mainContainer, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
    }

    public Scene createRouteDetailSceneV3() {
        RouteV3 route = controller.getRouteV3();

        // Header
        HBox header = createHeader(route);

        // List group for stops and transfers
        VBox listGroup = createListGroup(route);

        // Back button
        Button backButton = new Button("Terug");
        backButton.setOnAction(controller::handleBackButton);
        backButton.setPrefSize(120, 40);

        // Layout
        VBox layout = new VBox(10, header, listGroup, backButton);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f8f9fa;");

        return new Scene(layout, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
    }

    public Scene createRouteDetailSceneV4() {
        RouteV3 route = controller.getRouteV3();

        // Header
        HBox header = createHeader(route);

        // List group for stops and transfers
        VBox listGroup = createListGroupV2(route);

        // Back button
        Button backButton = new Button("Terug");
        backButton.setOnAction(controller::handleBackButton);
        backButton.setPrefSize(120, 40);

        VBox layoutData = new VBox(0, header, listGroup);

        // Layout
        VBox layout = new VBox(10, layoutData, backButton);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f8f9fa;");

        return new Scene(layout, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
    }

    private HBox createHeader(RouteV3 route) {
        Label title = new Label("Route Details");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label travelInfo = new Label(MessageFormat.format("{0} → {1} | Kosten: {2}", route.getStartLocation(), route.getEndLocation(), UtilityFunctions.formatValueAsCurrency(route.getCost())));
        travelInfo.setStyle("-fx-font-size: 16px;");

        VBox headerContent = new VBox(title, travelInfo);
        headerContent.setSpacing(5);

        HBox header = new HBox(headerContent);
        header.setPadding(new Insets(10));
        header.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        return header;
    }

    private VBox createListGroup(RouteV3 route) {
        VBox listGroup = new VBox();
        listGroup.setSpacing(10);

        for (StopV3 stop : route.getStops()) {
            HBox listItem = new HBox();

            VBox stopDetails = new VBox();
            Label location = new Label(MessageFormat.format("{0} → {1}", stop.getDepartureLocationAndDetails(), stop.getArrivalLocationAndDetails()));
            Label time = new Label(MessageFormat.format("Vertrek: {0} | Aankomst: {1} | {2} minuten", UtilityFunctions.formatTime(stop.getDepartureTime()), UtilityFunctions.formatTime(stop.getArrivalTime()), stop.getDurationMinutes()));
            Label details = new Label(MessageFormat.format("{0}", stop.getCombinedTransport()));

            stopDetails.getChildren().addAll(location, time, details);
            stopDetails.setSpacing(5);

            listItem.getChildren().add(stopDetails);
            listItem.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-padding: 10;");
            listGroup.getChildren().add(listItem);
        }

        return listGroup;
    }

    private VBox createListGroupV2(RouteV3 route) {
        VBox listGroup = new VBox();

        for (StopV3 stop : route.getStops()) {
            HBox listItem = new HBox();

            VBox stopDetails = new VBox();
            Label location = new Label(MessageFormat.format("{0} → {1}", stop.getDepartureLocationAndDetails(), stop.getArrivalLocationAndDetails()));
            Label time = new Label(MessageFormat.format("Vertrek: {0} | Aankomst: {1} | {2} minuten", UtilityFunctions.formatTime(stop.getDepartureTime()), UtilityFunctions.formatTime(stop.getArrivalTime()), stop.getDurationMinutes()));
            Label details = new Label(MessageFormat.format("{0}", stop.getCombinedTransport()));

            stopDetails.getChildren().addAll(location, time, details);
            stopDetails.setSpacing(5);

            listItem.getChildren().add(stopDetails);
            listItem.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-padding: 10;");
            listGroup.getChildren().add(listItem);
        }

        return listGroup;
    }
}
