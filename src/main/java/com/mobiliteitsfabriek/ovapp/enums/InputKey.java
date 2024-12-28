package com.mobiliteitsfabriek.ovapp.enums;

public enum InputKey {
    USERNAME("username"),
    PASSWORD("password"),
    STARTSTATION("vertrek station"),
    ENDSTATION("eindbestemming");

    private final String inputName;

    InputKey(String inputName) {
        this.inputName = inputName;
    }

    @Override
    public String toString() {
        return this.inputName;
    }

    // Getters and Setters
    public static InputKey getInputKeyFromString(String inputName) {
        for (InputKey fieldKey : InputKey.values()) {
            if (fieldKey.getFieldName().equalsIgnoreCase(inputName)) {
                return fieldKey;
            }
        }
        return null;
    }

    public String getFieldName() {
        return inputName;
    }
}
