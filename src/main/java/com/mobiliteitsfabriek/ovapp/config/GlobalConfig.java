package com.mobiliteitsfabriek.ovapp.config;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GlobalConfig {
    public static final int SCENE_WIDTH = 800; //eerst 1200
    public static final int SCENE_HEIGHT = 600; //eerst 800

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
}
