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

public class SearchFieldStation extends VBox{
    private StationService stationService;
    public ComboBox<String> startStation;
    private ObservableList<String> stationsNames = FXCollections.observableArrayList();

    public SearchFieldStation(StationService service, List<String> stationNames, String stationType){
        this.stationService = service;

        ComboBox<String> startStation = new ComboBox<>();
        startStation.setPromptText("Vul uw " + stationType + " station in.");
        startStation.getItems().addAll(stationNames);
        startStation.setEditable(true);
        startStation.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                startStation.hide();
                return;
            }

            ObservableList<String> filteredItems = FXCollections.observableArrayList();
            for (String stationName : stationNames) {
                if (stationName.toLowerCase().contains(newValue.toLowerCase())) {
                    filteredItems.add(stationName);
                }
            }
            startStation.setItems(filteredItems);
            startStation.show();
        });

        this.getChildren().addAll(startStation);
    }

    public String getSelectedValue() {
        if (startStation.getValue() != null){
            return startStation.getValue();
        }
        return "";
    }
}

