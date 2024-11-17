package com.mobiliteitsfabriek.ovapp.ui.controllers;

import com.mobiliteitsfabriek.ovapp.service.TransitService;
import com.mobiliteitsfabriek.ovapp.ui.components.StationCard;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class StationsController {
    /** The service responsible for handling transit-related operations and data retrieval */
    private final TransitService transitService;

    /** Container for displaying station cards in a flowing layout */
    @FXML
    private FlowPane stationsContainer;

    /** Text input field for searching/filtering stations */
    @FXML
    private TextField searchField;

    public StationsController(TransitService transitService) {
        this.transitService = transitService;
    }

    @FXML
    public void initialize() {
        loadStations();
        setupSearch();
    }

    private void loadStations() {
        // Load stations from service and create station cards
        transitService.searchStations("").thenAccept(stations ->
                stations.forEach(station -> {
                    final StationCard card = new StationCard(station);
                    stationsContainer.getChildren().add(card);
                })
        );
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter stations based on search text
            filterStations(newValue);
        });
    }

    private void filterStations(String searchText) {
        stationsContainer.getChildren().clear();
        transitService.searchStations(searchText)
                .thenAccept(stations -> stations.stream()
                        .filter(station -> station.getName().toLowerCase().contains(searchText.toLowerCase()))
                        .forEach(station -> stationsContainer.getChildren().add(new StationCard(station))));
    }
} 