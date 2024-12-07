package com.mobiliteitsfabriek.ovapp.ui.components;

import java.text.MessageFormat;
import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.ui.enums.SearchFieldStationTypes;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class SearchFieldStation extends ComboBox<String> {
    public ComboBox<String> startStation;
    private final ArrayList<String> stationNames;
    private final ObservableList<String> filteredItems;

    public SearchFieldStation(ArrayList<String> stationNames, SearchFieldStationTypes stationType, String defaultValue) {
        this.stationNames = stationNames;
        this.filteredItems = FXCollections.observableArrayList(stationNames);

        if (stationType == SearchFieldStationTypes.DEPARTURE_DESTINATION) {
            this.setPromptText("Vul uw start station in.");
        } else {
            this.setPromptText("Vul uw eind station in.");
        }

        this.setItems(filteredItems);
        if (defaultValue != null && filteredItems.contains(defaultValue)) {
            Platform.runLater(() -> this.safeSetValue(defaultValue));
        }

        this.setEditable(true);
        this.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (UtilityFunctions.checkEmpty(newValue)) {
                    resetFilter();
                } else {
                    applyFilter(newValue);
                }
            });
        });

        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && stationNames.contains(newValue)) {
                safeSetValue(newValue);
            }
        });
    }

    public SearchFieldStation(ArrayList<String> stationNames, SearchFieldStationTypes stationType) {
        this(stationNames, stationType, null);
    }

    public void safeSetValue(String value) {
        if (value != null && this.getItems().contains(value)) {
            Platform.runLater(() -> {
                this.setValue(value);
                this.getEditor().setText(value);
                applyFilter(value);
            });
        } else {
            System.out.println(MessageFormat.format("Error: Value '{0}' is not in the station list.", value));
        }
    }

    private void resetFilter() {
        Platform.runLater(() -> {
            filteredItems.setAll(stationNames);
            this.hide();
        });
    }

    private void applyFilter(String query) {
        Platform.runLater(() -> {
            filteredItems.setAll(stationNames.stream()
                    .filter(station -> station.toLowerCase().contains(query.toLowerCase()))
                    .toList());
            this.show();
        });
    }

}
