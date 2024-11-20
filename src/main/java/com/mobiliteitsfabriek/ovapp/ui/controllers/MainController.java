package com.mobiliteitsfabriek.ovapp.ui.controllers;

import com.mobiliteitsfabriek.ovapp.infrastructure.config.ApiConfig;
import com.mobiliteitsfabriek.ovapp.service.TransitService;
import com.mobiliteitsfabriek.ovapp.service.TransitServiceImpl;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

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
        updateStatus(TranslationHelper.get("status.settings"));
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    @FXML
    private void handleAbout() {
        updateStatus(TranslationHelper.get("status.about"));
    }

    @FXML
    private void handleJourneyPlanner() {
        updateStatus(TranslationHelper.get("status.journey.planner"));
    }

    @FXML
    private void handleFavorites() {
        updateStatus(TranslationHelper.get("status.favorites"));
    }

    @FXML
    private void handleRecentTrips() {
        updateStatus(TranslationHelper.get("status.recent.trips"));
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
                        updateStatus(TranslationHelper.get("status.error", throwable.getMessage()));
                    });
                    return null;
                });
    }
}