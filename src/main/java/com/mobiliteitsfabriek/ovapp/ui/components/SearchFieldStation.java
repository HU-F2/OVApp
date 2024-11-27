package com.mobiliteitsfabriek.ovapp.ui.components;

import java.util.List;

import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.service.StationService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;

public class SearchFieldStation extends ComboBox<String>{
    private StationService stationService;
    private ObservableList<String> stationsNames = FXCollections.observableArrayList();

    public SearchFieldStation(StationService service){
        this.stationService = service;

        List<Station> stations = stationService.getStationByName("");
        stationsNames.setAll(stations.stream().map((station)->station.getName()).toList());

        this.setItems(stationsNames);
        this.setEditable(true);
        
        this.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Do not clear the selection
            if (newValue != null) {
                this.getEditor().setText(newValue);
            }
        });

        this.setOnKeyReleased(event -> {
            String typedCharacter = event.getText();

            // Check if the typed character is a letter
            if (!typedCharacter.matches("[a-zA-Z]") && event.getCode() != KeyCode.BACK_SPACE) {
                return;
            }
            String input = this.getEditor().getText().toLowerCase();
            List<Station> names = stationService.getStationByName(input);
            stationsNames.setAll(names.stream().map((station)->station.getName()).toList());

            if (this.getItems().isEmpty()) {
                this.getEditor().setText(input);  // Keep current input
                this.getEditor().positionCaret(input.length());
                this.hide();
                return;
            }
            this.show();
        });
    }
}
