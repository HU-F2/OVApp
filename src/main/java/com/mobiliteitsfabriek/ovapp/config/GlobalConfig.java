package com.mobiliteitsfabriek.ovapp.config;

import java.util.Locale;

public class GlobalConfig {
    public static final int SCENE_WIDTH = 800; //eerst 1200
    public static final int SCENE_HEIGHT = 600; //eerst 800

    // UtilityFunctions
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String TIME_FORMAT = "HH:mm";
    public static final Locale NETHERLANDS_LOCAL = Locale.forLanguageTag("nl-NL");
    
    // 1000 = 1 second
    public static final int SHOW_TEST_PAGE_DURATION = (1000 * 100);
}
