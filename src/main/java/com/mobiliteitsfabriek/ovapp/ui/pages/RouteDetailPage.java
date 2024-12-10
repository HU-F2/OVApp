package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.text.MessageFormat;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.RouteTransfers;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.controllers.RouteDetailController;

import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RouteDetailPage {

    private final RouteDetailController controller;

    public RouteDetailPage(Route route) {
        this.controller = new RouteDetailController(route);
    }

    public Scene createRouteDetailScene() {
        Route route = controller.getRoute();

        // Header
        HBox header = createHeader(route);
        
        // Javafx list group for routeTransfers
        VBox listGroup = createListGroup(route);

        // Back button
        Button backButton = new Button(TranslationHelper.get("app.common.back"));
        backButton.setOnAction(controller::handleBackButton);
        backButton.setPrefSize(120, 40);

        VBox layoutData = new VBox(0, header, listGroup);

        // Layout
        VBox layout = new VBox(10, layoutData, backButton);
        layout.getStyleClass().add("detailedRoute-container");

        Scene scene = new Scene(layout, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(RoutesPage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }

    private HBox createHeader(Route route) {
        Label title = new Label(TranslationHelper.get("app.detail.title"));
        title.getStyleClass().add("title");
        title.setFocusTraversable(true);

        Label travelInfo;
        if (UtilityFunctions.checkEmpty(route.getCost())) {
            travelInfo = new Label(MessageFormat.format("{0} → {1}", route.getStartLocation(), route.getEndLocation()));
            travelInfo.setAccessibleText(MessageFormat.format(TranslationHelper.get("app.detail.travelInfo.tts"), route.getStartLocation(), route.getEndLocation()));
        } else {
            travelInfo = new Label(MessageFormat.format(TranslationHelper.get("app.detail.travelInfoPrice"), route.getStartLocation(),route.getEndLocation()));
            travelInfo.setAccessibleText(MessageFormat.format(TranslationHelper.get("app.detail.travelInfoPrice.tts"), route.getStartLocation(),route.getEndLocation()));
        }
        travelInfo.getStyleClass().add("info");
        travelInfo.setFocusTraversable(true);

        VBox headerContent = new VBox(title, travelInfo);
        headerContent.setSpacing(5);

        HBox header = new HBox(headerContent);
        header.getStyleClass().add("header");
        return header;
    }

    private VBox createListGroup(Route route) {
        VBox listGroup = new VBox();

        for (RouteTransfers routeTransfer : route.getRouteTransfers()) {
            HBox listItem = new HBox();
            listItem.setFocusTraversable(true);
            listItem.setAccessibleRole(AccessibleRole.TEXT);
            listItem.setAccessibleText(MessageFormat.format(TranslationHelper.get("app.detail.transfer"), routeTransfer.getTransportType()));

            VBox stopDetails = new VBox();
            Label location = new Label(MessageFormat.format("{0} → {1}", routeTransfer.getDepartureLocationAndDetails(), routeTransfer.getArrivalLocationAndDetails()));
            location.setFocusTraversable(true);
            location.setAccessibleText(MessageFormat.format(TranslationHelper.get("app.detail.transfer.location.tts"), routeTransfer.getDepartureLocation(), routeTransfer.getDepartureLocationDetails() ,routeTransfer.getArrivalLocation(),routeTransfer.getArrivalLocationDetails()));
            Label time = new Label(MessageFormat.format(TranslationHelper.get("app.detail.transfer.time"), UtilityFunctions.formatTime(routeTransfer.getPlannedDepartureDateTime()), UtilityFunctions.formatTime(routeTransfer.getPlannedArrivalDateTime()), routeTransfer.getPlannedDurationMinutes()));
            time.setFocusTraversable(true);
            time.setAccessibleText(MessageFormat.format(TranslationHelper.get("app.detail.transfer.time.tts"), UtilityFunctions.formatTime(routeTransfer.getPlannedDepartureDateTime()), UtilityFunctions.formatTime(routeTransfer.getPlannedArrivalDateTime()), routeTransfer.getPlannedDurationMinutes()));
            Label details = new Label(MessageFormat.format("{0}", routeTransfer.getCombinedTransport()));
            details.setFocusTraversable(true);
            details.setAccessibleText(MessageFormat.format(TranslationHelper.get("app.detail.transfer.details.tts"), routeTransfer.getTransportType()));

            stopDetails.getChildren().addAll(location, time, details);
            stopDetails.setSpacing(5);

            listItem.getChildren().add(stopDetails);
            listItem.getStyleClass().add("routeTransfer");
            listGroup.getChildren().add(listItem);
        }

        return listGroup;
    }
}
