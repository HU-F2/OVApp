package com.mobiliteitsfabriek.ovapp.ui.components;

import java.util.List;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class SearchFieldStation extends ComboBox<String> {
    public ComboBox<String> startStation;

    public SearchFieldStation(List<String> stationNames, String stationType, String defaultValue) {
        this.setPromptText(TranslationHelper.get("searchFieldStation.prompt",stationType));

        this.setEditable(true);
        this.getItems().addAll(stationNames);
        if (defaultValue != null) {
            this.getEditor().textProperty().set(defaultValue);
        }

        this.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                this.hide();
                return;
            }

            if(this.getValue() != null && !newValue.equals(this.getValue())){
                this.setValue(null);
                this.getEditor().setText(newValue);
                this.getEditor().selectPositionCaret(newValue.length());
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

    public SearchFieldStation(List<String> stationNames, String stationType) {
        this(stationNames, stationType, null);
    }
}
