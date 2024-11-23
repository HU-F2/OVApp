package com.mobiliteitsfabriek.ovapp.ui.components;

import java.util.List;

import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.service.StationService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SearchFieldStation extends VBox{
    private StationService stationService;
    private ObservableList<String> stationsNames = FXCollections.observableArrayList();

    public SearchFieldStation(StationService service){
        this.stationService = service;
        ListView<String> listView = new ListView<>();
        listView.setItems(stationsNames);
        listView.setVisible(false);
        listView.setManaged(false);

        TextField startStationInput = new TextField("Vul uw begin station in.");

        startStationInput.textProperty().addListener((observable,oldValue,newValue)->{
            if(newValue.isBlank()){
                listView.setVisible(false);
                listView.setManaged(false);
                return;
            }
            List<Station> stations = stationService.getStationByName(newValue);
            stationsNames.setAll(stations.stream().map((station)->station.getName()).toList());
            listView.setVisible(true);
            listView.setManaged(true);
        });

        this.getChildren().addAll(startStationInput,listView);
    }
}
