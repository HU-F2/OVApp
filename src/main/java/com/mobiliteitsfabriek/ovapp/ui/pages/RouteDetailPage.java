package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.text.MessageFormat;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.RouteTransfersV3;
import com.mobiliteitsfabriek.ovapp.model.RouteV3;
import com.mobiliteitsfabriek.ovapp.ui.controllers.RouteDetailController;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RouteDetailPage {

    private final RouteDetailController controller;

    public RouteDetailPage(RouteV3 route) {
        this.controller = new RouteDetailController(route);
    }

    // TODO: put all styling into a css file. 
    // TODO: add translations
    // TODO: add screanreader suport
    public Scene createRouteDetailScene() {
        RouteV3 route = controller.getRouteV3();

        // Header
        HBox header = createHeader(route);

        // List group for routeTransfers and transfers
        VBox listGroup = createListGroup(route);

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

        for (RouteTransfersV3 routeTransfer : route.getRouteTransfers()) {
            HBox listItem = new HBox();

            VBox stopDetails = new VBox();
            Label location = new Label(MessageFormat.format("{0} → {1}", routeTransfer.getDepartureLocationAndDetails(), routeTransfer.getArrivalLocationAndDetails()));
            Label time = new Label(MessageFormat.format("Vertrek: {0} | Aankomst: {1} | {2} minuten", UtilityFunctions.formatTime(routeTransfer.getPlannedDepartureDateTime()), UtilityFunctions.formatTime(routeTransfer.getPlannedArrivalDateTime()), routeTransfer.getPlannedDurationMinutes()));
            Label details = new Label(MessageFormat.format("{0}", routeTransfer.getCombinedTransport()));

            stopDetails.getChildren().addAll(location, time, details);
            stopDetails.setSpacing(5);

            listItem.getChildren().add(stopDetails);
            listItem.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-padding: 10;");
            listGroup.getChildren().add(listItem);
        }

        return listGroup;
    }
}
