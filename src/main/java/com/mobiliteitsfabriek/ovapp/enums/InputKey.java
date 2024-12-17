package com.mobiliteitsfabriek.ovapp.enums;

public enum InputKey {
    USERNAME("username"),
    PASSWORD("password");

    private final String inputName;

    InputKey(String inputName) {
        this.inputName = inputName;
    }

    public String getFieldName() {
        return inputName;
    }

    @Override
    public String toString() {
        return this.inputName;
    }

    public static InputKey getInputKeyFromString(String inputName) {
        for (InputKey fieldKey : InputKey.values()) {
            if (fieldKey.getFieldName().equalsIgnoreCase(inputName)) {
                return fieldKey;
            }
        }
        return null;
    }
}
