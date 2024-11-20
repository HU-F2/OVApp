package com.mobiliteitsfabriek.ovapp.translation;

public enum Language {
    DUTCH("nl", "Nederlands");

    private final String code;
    private final String displayName;

    Language(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Language getLanguageFromCode(String code) {
        for (Language lang : Language.values()) {
            if (lang.getCode().equalsIgnoreCase(code)) {
                return lang;
            }
        }
        return null;
    }
}