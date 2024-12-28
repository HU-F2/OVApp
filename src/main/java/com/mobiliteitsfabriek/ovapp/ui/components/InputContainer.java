package com.mobiliteitsfabriek.ovapp.ui.components;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InputContainer extends VBox {
    private Label invalidValidationLabel;

    public InputContainer(String labelText, Control field) {
        invalidValidationLabel = null;
        if (!UtilityFunctions.checkEmpty(labelText)) {
            createLabeledField(labelText, field);
        } else {
            addControlToContainer(field);
        }
    }

    public void addError(String invalidValidationMessage) {
        if (invalidValidationLabel == null) {
            invalidValidationLabel = createErrorLabel(invalidValidationMessage);
            this.getChildren().addLast(invalidValidationLabel);
        } else {
            invalidValidationLabel.setText(invalidValidationMessage);
        }
    }

    public void noError() {
        if (invalidValidationLabel != null) {
            this.getChildren().remove(invalidValidationLabel);
            invalidValidationLabel = null;
        }
    }

    private void createLabeledField(String labelText, Control field) {
        Label label = new Label(labelText);
        label.getStyleClass().add("input-container-label");
        this.getStyleClass().add("input-container");
        this.getChildren().addAll(label, field);
    }

    private void addControlToContainer(Control field) {
        this.getStyleClass().add("input-container");
        this.getChildren().addAll(field);
    }

    private Label createErrorLabel(String invalidValidationMessage) {
        Label invalidInputLabel = new Label(invalidValidationMessage);
        invalidInputLabel.getStyleClass().add("invalid-input-label");
        invalidInputLabel.setAccessibleHelp(invalidValidationMessage);
        return invalidInputLabel;
    }
}
