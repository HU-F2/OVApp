package com.mobiliteitsfabriek.ovapp.ui.components;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;

import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InputContainer extends VBox {
    private Label invalidValidationLabel;

    public InputContainer(String labelText, Control field) {
        invalidValidationLabel = null;
        createLabeledField(labelText, field);
    }

    public InputContainer(Control field){
        this(null,field);
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
        if(UtilityFunctions.checkEmpty(labelText)){
            this.getChildren().add(field);
            this.setAlignment(Pos.CENTER);
            return;
        }
        Label label = new Label(labelText);
        label.getStyleClass().add("login-label");
        this.getStyleClass().add("input-container");
        this.getChildren().addAll(label, field);
    }

    private Label createErrorLabel(String invalidValidationMessage) {
        Label invalidInputLabel = new Label(invalidValidationMessage);
        invalidInputLabel.getStyleClass().add("invalid-input-label");
        invalidInputLabel.setAccessibleHelp(invalidValidationMessage);
        return invalidInputLabel;
    }
}
