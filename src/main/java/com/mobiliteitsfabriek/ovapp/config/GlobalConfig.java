package com.mobiliteitsfabriek.ovapp.config;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.mobiliteitsfabriek.ovapp.translation.Language;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class GlobalConfig {
    public static final int SCENE_WIDTH = 500; //eerst 1200
    public static final int SCENE_HEIGHT = 800; //eerst 800

    // UtilityFunctions
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String TIME_FORMAT = "HH:mm";
    public static final Locale NETHERLANDS_LOCAL = Locale.forLanguageTag("nl-NL");

    // API
    public static final DateTimeFormatter NS_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
    public static final String API_KEY = "57e1df724be741f6bc8f926355646bd5";

    // 1000 = 1 second
    public static final int SHOW_TEST_PAGE_DURATION = (1000 * 100);

    // Data File Paths
    public static final String FILE_PATH_STATIONS = "src/main/resources/stations.json";

    // console output
    public static final Boolean NO_TRANSLATION_KEY_ERROR_CONSOLE = true;

    // Validation
    public static final int BCRYPT_STRENGTH = 10;
    public static final Boolean CHECK_PASSWORD_CREATE_PATTERN = true;
    public static final String PASSWORD_CREATE_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    // functionalities
    public static final Boolean GUEST_LOGIN_BUTTON = true;

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
