package com.mobiliteitsfabriek.ovapp.ui.controllers;

import com.mobiliteitsfabriek.ovapp.infrastructure.config.ApiConfig;
import com.mobiliteitsfabriek.ovapp.service.TransitService;
import com.mobiliteitsfabriek.ovapp.service.TransitServiceImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import lombok.RequiredArgsConstructor;

/**
 * Main controller for the OV App application.
 * Handles primary navigation and user interface interactions.
 */
@RequiredArgsConstructor
public class MainController {

    /** Service responsible for handling transit-related operations and API calls */
    private TransitService transitService;

    /** Main content container for the application's dynamic content */
    @FXML
    private StackPane contentArea;

    /** Label displaying status messages and user feedback */
    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        final ApiConfig config = ApiConfig.load();
        this.transitService = new TransitServiceImpl(config.getApiBaseUrl(), config.getApiKey());
    }

    /**
     * Opens the settings dialog.
     */
    @FXML
    private void handleSettings() {
        updateStatus("Opening settings...");
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    @FXML
    private void handleAbout() {
        updateStatus("OV App - Version 1.0");
    }

    @FXML
    private void handleJourneyPlanner() {
        updateStatus("Opening journey planner...");
    }

    @FXML
    private void handleFavorites() {
        updateStatus("Opening favorites...");
    }

    @FXML
    private void handleRecentTrips() {
        updateStatus("Opening recent trips...");
    }

    /**
     * Updates the status message in the UI.
     *
     * @param message The message to display
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    /**
     * Searches for stations based on the provided query.
     *
     * @param query The search query string
     */
    private void searchStations(String query) {
        transitService.searchStations(query)
                .thenAccept(stations -> {
                    Platform.runLater(() -> {
                        // Update your UI components here
                    });
                })
                .exceptionally(throwable -> {
                    Platform.runLater(() -> {
                        updateStatus("Error: " + throwable.getMessage());
                    });
                    return null;
                });
    }
}