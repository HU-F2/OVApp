package com.mobiliteitsfabriek.ovapp.ui.components;

import java.time.format.DateTimeFormatter;

import com.mobiliteitsfabriek.ovapp.model.Route;

import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class RouteElement extends VBox {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public RouteElement(Route route, boolean lastElement) {
        this.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE,
                lastElement ? BorderStrokeStyle.SOLID : BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
                CornerRadii.EMPTY, new BorderWidths(1), Insets.EMPTY)));

        // Time
        HBox timeContainer = new HBox();
        Text startTimeText = new Text(route.getStartDateTime().format(timeFormatter));
        Text arrowSign = new Text(" --> ");
        Text endTimeText = new Text(route.getEndDateTime().format(timeFormatter));
        timeContainer.getChildren().addAll(startTimeText, arrowSign, endTimeText);
        timeContainer.getStyleClass().add("time-container");

        // Duration
        HBox infoContainer = new HBox();
        Text durationText = new Text(route.getDuration() + " minuten | ");
        Text transfersText = new Text(route.getTransfers() + " overstappen | ");
        Text platformNumberText = new Text("platform " + route.getPlatformNumber());
        infoContainer.getChildren().addAll(durationText, transfersText, platformNumberText);
        infoContainer.getStyleClass().add("info-container");

        this.getChildren().addAll(timeContainer, infoContainer);
        this.getStyleClass().add("route");

        this.setOnMouseClicked((e) -> {
            this.requestFocus();
            handleGoToDetailedRoute(route);
        });

        this.setFocusTraversable(true);
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleGoToDetailedRoute(route);
                event.consume();
            }
        });
    }

    //TODO: implementeer de detailedRoute pagina
    public static void handleGoToDetailedRoute(Route route) {
        throw new UnsupportedOperationException();
    }
}
