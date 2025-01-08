package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.util.ArrayList;

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
    private final ArrayList<Route> previousRoutes;

    public RouteDetailPage(Route route, ArrayList<Route> routes) {
        this.controller = new RouteDetailController(route);
        this.previousRoutes = routes;
    }

    public Scene createRouteDetailScene() {
        Route route = controller.getRoute();

        // Header
        HBox header = createHeader(route);

        // Javafx list group for routeTransfers
        VBox listGroup = createListGroup(route);

        // Back button
        Button backButton = new Button(TranslationHelper.get("app.common.back"));
        backButton.setOnAction((actionEvent) -> {
            controller.handleBackButton(actionEvent, previousRoutes);
        });
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
        Label title = new Label(TranslationHelper.get("detail.title"));
        title.getStyleClass().add("title");
        title.setFocusTraversable(GlobalConfig.isUsingScreenreader);

        Label travelInfo = new Label(TranslationHelper.get("detail.travelInfo", route.getStartLocation(), route.getEndLocation()));
        travelInfo.setAccessibleText(TranslationHelper.get("detail.travelInfo.accessibleText", route.getStartLocation(), route.getEndLocation()));

        travelInfo.getStyleClass().add("info");
        travelInfo.setFocusTraversable(GlobalConfig.isUsingScreenreader);

        VBox headerContent = new VBox(title, travelInfo);
        if (!UtilityFunctions.checkEmpty(route.getCost())) {
            Label priceLabel = new Label(TranslationHelper.get("detail.price", UtilityFunctions.formatValueAsCurrency(route.getCost().getFirstClassPriceInCents() / 100.0), UtilityFunctions.formatValueAsCurrency(route.getCost().getSecondClassPriceInCents() / 100.0)));
            priceLabel.getStyleClass().add("info");
            headerContent.getChildren().add(priceLabel);
        }

        headerContent.setSpacing(5);

        HBox header = new HBox(headerContent);
        header.getStyleClass().add("header");
        return header;
    }

    private VBox createListGroup(Route route) {
        VBox listGroup = new VBox();

        for (RouteTransfers routeTransfer : route.getRouteTransfers()) {
            HBox listItem = new HBox();
            listItem.setFocusTraversable(GlobalConfig.isUsingScreenreader);
            listItem.setAccessibleRole(AccessibleRole.TEXT);
            listItem.setAccessibleText(TranslationHelper.get("detail.transfer", routeTransfer.getTransportType()));

            VBox stopDetails = new VBox();
            Label location = new Label(TranslationHelper.get("detail.transfer.location", routeTransfer.getDepartureLocationAndDetails(), routeTransfer.getArrivalLocationAndDetails()));
            location.setFocusTraversable(GlobalConfig.isUsingScreenreader);
            location.setAccessibleText(TranslationHelper.get("detail.transfer.location.accessibleText", routeTransfer.getDepartureLocation(), routeTransfer.getDepartureLocationDetails(), routeTransfer.getArrivalLocation(), routeTransfer.getArrivalLocationDetails()));
            Label time = new Label(TranslationHelper.get("detail.transfer.time", UtilityFunctions.formatTime(routeTransfer.getPlannedDepartureDateTime()), UtilityFunctions.formatTime(routeTransfer.getPlannedArrivalDateTime()), routeTransfer.getPlannedDurationMinutes()));
            time.setFocusTraversable(GlobalConfig.isUsingScreenreader);
            time.setAccessibleText(TranslationHelper.get("detail.transfer.time.accessibleText", UtilityFunctions.formatTime(routeTransfer.getPlannedDepartureDateTime()), UtilityFunctions.formatTime(routeTransfer.getPlannedArrivalDateTime()), routeTransfer.getPlannedDurationMinutes()));
            Label details = new Label(TranslationHelper.get("detail.transfer.details", routeTransfer.getCombinedTransport()));
            details.setFocusTraversable(GlobalConfig.isUsingScreenreader);
            details.setAccessibleText(TranslationHelper.get("detail.transfer.details.accessibleText", routeTransfer.getTransportType()));

            stopDetails.getChildren().addAll(location, time, details);
            stopDetails.setSpacing(5);

            listItem.getChildren().add(stopDetails);
            listItem.getStyleClass().add("routeTransfer");
            listGroup.getChildren().add(listItem);
        }

        return listGroup;
    }
}
