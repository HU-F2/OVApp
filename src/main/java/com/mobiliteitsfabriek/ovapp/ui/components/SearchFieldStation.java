package com.mobiliteitsfabriek.ovapp.ui.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.service.StationService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;

public class SearchFieldStation extends ComboBox<String>{
    private StationService stationService;
    public ComboBox<String> startStation;
    private ObservableList<String> stationsNames = FXCollections.observableArrayList();

    public SearchFieldStation(StationService service, List<String> stationNames, String stationType){
        this.stationService = service;

        // ComboBox<String> startStation = new ComboBox<>();
        this.setPromptText("Vul uw " + stationType + " station in.");
        this.getItems().addAll(stationNames);
        this.setEditable(true);
        this.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                this.hide();
                return;
            }

            ObservableList<String> filteredItems = FXCollections.observableArrayList();
            for (String stationName : stationNames) {
                if (stationName.toLowerCase().contains(newValue.toLowerCase())) {
                    filteredItems.add(stationName);
                }
            }
            this.setItems(filteredItems);
            this.show();

        });
    }
}

