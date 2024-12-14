package com.mobiliteitsfabriek.ovapp.ui.components;

import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.pages.RouteDetailPage;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RouteElement extends VBox {
    public RouteElement(Route route, boolean lastElement,ArrayList<Route> routes) {
        this.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                lastElement ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

        // Time
        Label timeLabel = new Label(TranslationHelper.get("route.time",UtilityFunctions.formatTime(route.getStartDateTime()),UtilityFunctions.formatTime(route.getEndDateTime())));
        timeLabel.getStyleClass().add("time-container");

        // Duration
        Label infoLabel = new Label(TranslationHelper.get("route.info",route.getPlannedDurationInMinutes(),route.getTransfersAmount(),route.getDeparturePlatformNumber()));
        infoLabel.getStyleClass().add("info-container");

        this.getChildren().addAll(timeLabel, infoLabel);
        this.getStyleClass().add("route");

        this.setOnMouseClicked((e) -> {
            this.requestFocus();
            handleGoToDetailedRoute(route, routes);
        });

        this.setFocusTraversable(true);
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleGoToDetailedRoute(route, routes);
                event.consume();
            }
        });
    }

    public static void handleGoToDetailedRoute(Route route, ArrayList<Route> routes) {
        RouteDetailPage routeDetailPage = new RouteDetailPage(route, routes);
        OVAppUI.switchToScene(routeDetailPage.createRouteDetailScene());
    }
}
