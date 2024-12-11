package com.mobiliteitsfabriek.ovapp.ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class DepartureTimeToggleButton {    
    private ToggleButton selectedButton;
    
    public DepartureTimeToggleButton(){

    }

    public HBox departureToggleButton(){
        ToggleGroup toggleGroup = new ToggleGroup();

        ToggleButton vertrekButton = new ToggleButton("Vertrek");
        ToggleButton aankomstButton = new ToggleButton("Aankomst");

        vertrekButton.setToggleGroup(toggleGroup);
        aankomstButton.setToggleGroup(toggleGroup);

        vertrekButton.setSelected(true);
        selectedButton = vertrekButton;
        HBox toggleContainer = new HBox(0, vertrekButton, aankomstButton);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedButton = (ToggleButton) newValue;
            }
        });
        toggleContainer.setAlignment(Pos.CENTER);

        return toggleContainer;
    }

    public boolean isToggleDeparture(){
        String buttonText = selectedButton.getText();
        if (buttonText.equals("Vertrek") || buttonText.equals("Departure")){
            return false;
        } else if (buttonText.equals("Aankomst") || buttonText.equals("Arrival")) {
            return true;
        } else {
            return true;
        }
    }

}
