package com.mobiliteitsfabriek.ovapp.ui.components;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class DepartureTimeToggleButton {
    private ToggleButton selectedButton;

    public HBox departureToggleButton() {
        ToggleGroup toggleGroup = new ToggleGroup();

        ToggleButton vertrekButton = new ToggleButton(TranslationHelper.get("departureTimeToggle.departure"));
        ToggleButton aankomstButton = new ToggleButton(TranslationHelper.get("departureTimeToggle.arrival"));

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

    public boolean isArrival() {
        // FIXME: de selectedButton wordt niet geintaliseerd samen met de DepartureTimeToggleButton. Hij kan dan dus null zijn bij deze functie.
        // FIXME: dit is een simpele fix, maar zou verbeterd kunnen worden.
        if (selectedButton == null) {
            return false;
        }
        String buttonText = selectedButton.getText();
        String arrival = TranslationHelper.get("departureTimeToggle.arrival");
        return buttonText.equals(arrival);
    }

}
