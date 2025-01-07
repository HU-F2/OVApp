package com.mobiliteitsfabriek.ovapp.ui.components;

import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.pages.RouteDetailPage;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.AccessibleRole;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class RouteElement extends HBox {
    public RouteElement(Route route, boolean lastElement, ArrayList<Route> routes) {
        this.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                lastElement ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

        // Time
        Label timeLabel = new Label(TranslationHelper.get("route.time", UtilityFunctions.formatTime(route.getStartDateTime()), UtilityFunctions.formatTime(route.getEndDateTime())));
        timeLabel.setAccessibleText(TranslationHelper.get("route.time.accessibleText",UtilityFunctions.formatTime(route.getStartDateTime()), UtilityFunctions.formatTime(route.getEndDateTime())));
        timeLabel.getStyleClass().add("time-container");
        timeLabel.setFocusTraversable(GlobalConfig.isUsingScreenreader);
        timeLabel.focusedProperty().addListener(this::handleFocusChange);//Workaround for focus-within
        
        // Duration
        Label infoLabel = new Label(TranslationHelper.get("route.info", route.getPlannedDurationInMinutes(), route.getTransfersAmount(), route.getDeparturePlatformNumber()));
        infoLabel.setAccessibleText(TranslationHelper.get("route.info.accessibleText",route.getPlannedDurationInMinutes(), route.getTransfersAmount(), route.getDeparturePlatformNumber()));
        infoLabel.getStyleClass().add("info-container");
        infoLabel.setFocusTraversable(GlobalConfig.isUsingScreenreader);
        infoLabel.focusedProperty().addListener(this::handleFocusChange);//Workaround for focus-within
        
        VBox container = new VBox(timeLabel, infoLabel);

        Label priceLabel = new Label("€" + String.valueOf(route.getCost().getFirstClassPriceInCents()/100.0f));
        HBox.setHgrow(container, Priority.ALWAYS);

        this.getChildren().addAll(container, priceLabel);
        this.getStyleClass().add("route");
        
        this.setOnMouseClicked((e) -> {
            this.requestFocus();
            handleGoToDetailedRoute(route, routes);
        });

        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleGoToDetailedRoute(route, routes);
                event.consume();
            }
        });

        this.setAccessibleRole(AccessibleRole.TEXT);
        this.setAccessibleText(TranslationHelper.get("route.accessibleText"));
        this.setFocusTraversable(true);
        this.focusedProperty().addListener(this::handleFocusChange);
    }

    public static void handleGoToDetailedRoute(Route route, ArrayList<Route> routes) {
        RouteDetailPage routeDetailPage = new RouteDetailPage(route, routes);
        OVAppUI.switchToScene(routeDetailPage.createRouteDetailScene());
    }

    private void handleFocusChange(@SuppressWarnings("unused") ObservableValue<? extends Boolean> observable, @SuppressWarnings("unused") Boolean oldValue, Boolean newValue) {
        if (newValue) {
            this.getStyleClass().add("selected");
        } else {
            this.getStyleClass().remove("selected");
        }
    }
}
