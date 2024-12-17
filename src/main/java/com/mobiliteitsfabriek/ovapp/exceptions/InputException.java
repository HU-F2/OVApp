package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.enums.InputKey;

public abstract class InputException extends Exception {
    private final InputKey inputKey;

    public InputException(String message, InputKey inputKey) {
        super(message);
        this.inputKey = inputKey;
    }

    public InputKey getFieldKey() {
        return inputKey;
    }
}
