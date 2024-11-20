package com.mobiliteitsfabriek.ovapp.ui.components;

import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StationCard extends VBox {
    public StationCard(Station station) {
        getStyleClass().add("station-card");

        final Label nameLabel = new Label(station.getName());
        nameLabel.getStyleClass().add("station-name");

        final HBox accessibilityInfo = new HBox(5);
        if (station.hasElevator()) {
            accessibilityInfo.getChildren().add(new Label("🛗"));
        }
        if (station.hasAccessiblePlatform()) {
            accessibilityInfo.getChildren().add(new Label("♿"));
        }

        final Button detailsButton = new Button(TranslationHelper.get("stationcard.button.details"));
        detailsButton.setOnAction(e -> showDetails(station));

        getChildren().addAll(nameLabel, accessibilityInfo, detailsButton);
    }

    private void showDetails(Station station) {
        // Show station details in a new window or dialog
    }
} 