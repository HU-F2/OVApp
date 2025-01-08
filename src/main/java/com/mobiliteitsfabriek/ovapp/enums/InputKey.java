package com.mobiliteitsfabriek.ovapp.enums;

public enum InputKey {
    USERNAME("username", "inputKey.username"),
    PASSWORD("password", "inputKey.password"),
    FAVORITE("favorite", "inputKey.favorite"),
    START_STATION("startStation", "inputKey.startStation"),
    END_STATION("endStation", "inputKey.endStation"),
    TRAVEL_DATE("travelDate", "inputKey.travelDate"),
    SEARCH_ROUTE("searchRoute", "inputKey.searchRoute");

    private final String inputName;
    private final String inputTranslation;

    InputKey(String inputName, String inputTranslation) {
        this.inputName = inputName;
        this.inputTranslation = inputTranslation;
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

    public String getInputTranslation() {
        return inputTranslation;
    }

    public String getFieldName() {
        return inputName;
    }
}
