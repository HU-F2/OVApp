package com.mobiliteitsfabriek.ovapp.config;

import com.mobiliteitsfabriek.ovapp.translation.Language;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class GlobalConfig {
    // console output
    public static final Boolean NO_TRANSLATION_KEY_ERROR_CONSOLE = false;

    // Standaard taal van de applicatie.
    // deze taal wordt gebruikt als de vertaling niet bestaat in de applicatie taal.
    public static final Language DEFAULT_LANGUAGE = Language.DUTCH;

    // Huidige taal (kan tijdens runtime veranderen)
    public static Language currentLanguage = DEFAULT_LANGUAGE;

    // Methode om de huidige taal te wijzigen
    public static void setLanguage(Language language) {
        currentLanguage = language;
        TranslationHelper.setLanguage(language);
    }
}