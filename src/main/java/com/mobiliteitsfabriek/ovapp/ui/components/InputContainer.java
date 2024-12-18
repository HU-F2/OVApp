package com.mobiliteitsfabriek.ovapp.ui.components;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InputContainer extends VBox {
    private Label invalidValidationLabel;

    public InputContainer(String labelText, Control field) {
        invalidValidationLabel = null;
        createLabeledField(labelText, field);
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
        label.getStyleClass().add("login-label");
        this.setSpacing(5);
        this.getChildren().addAll(label, field);
    }

    private Label createErrorLabel(String invalidValidationMessage) {
        Label error = new Label(invalidValidationMessage);
        error.getStyleClass().add("error-prompt-heading");
        return error;
    }
}
