package com.mobiliteitsfabriek.ovapp.ui.components;

import java.util.List;

import com.mobiliteitsfabriek.ovapp.service.StationService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class SearchFieldStation extends ComboBox<String> {
    public ComboBox<String> startStation;

    public SearchFieldStation(StationService service, List<String> stationNames, String stationType) {
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
