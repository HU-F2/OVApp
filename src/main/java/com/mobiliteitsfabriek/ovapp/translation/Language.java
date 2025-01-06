package com.mobiliteitsfabriek.ovapp.translation;

public enum Language {
    DUTCH("nl", "Nederlands", "language.nl"),
    ENGLISH("en", "Engels", "language.en"),
    GERMAN("de", "Duits", "language.de");

    private final String code;
    private final String displayName;
    private final String translationKey;

    Language(String code, String displayName, String translationKey) {
        this.code = code;
        this.displayName = displayName;
        this.translationKey = translationKey;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Language getLanguageFromTranslationKey(String translationKey) {
        for (Language lang : Language.values()) {
            if (lang.getTranslationKey().equalsIgnoreCase(translationKey)) {
                return lang;
            }
        }
        return null;
    }
}