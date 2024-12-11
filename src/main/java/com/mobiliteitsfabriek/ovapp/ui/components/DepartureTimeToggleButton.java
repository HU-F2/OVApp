package com.mobiliteitsfabriek.ovapp.ui.components;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;

import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class DepartureTimeToggleButton {
    private ToggleButton selectedButton;

    public HBox departureToggleButton() {
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

    public boolean isToggleDeparture() {
        if (selectedButton == null) {
            return true;
        }
        String buttonText = selectedButton.getText();
        // FIXME: Hier moet een betere oplossing voor komen, want dit is erg foutgevoelig.
        if (UtilityFunctions.checkStringInArrayList(buttonText, "Vertrek", "Departure")) {
            return false;
        }
        return true;
    }

}
