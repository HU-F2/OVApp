package com.mobiliteitsfabriek.ovapp.general.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import com.mobiliteitsfabriek.ovapp.exceptions.RuntimeConfigException;
import com.mobiliteitsfabriek.ovapp.translation.Language;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class GlobalConfig {
    private static final String APPLICATION_CONFIGURATION_FILE = "application.properties";
    private static final Properties properties;

    // console output
    public static final Boolean NO_TRANSLATION_KEY_ERROR_CONSOLE = false;

    // Schermconfiguratie
    public static final double SCENE_WIDTH = 1200;
    public static final double SCENE_HEIGHT = 800;

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

    static {
        properties = new Properties();
    }

    // ------- for loading the configuration file properties ---------
    // currently not used.

    public static void loadConfigurationFile() {
        try (InputStream inputStream = GlobalConfig.class.getResourceAsStream(APPLICATION_CONFIGURATION_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeConfigException(APPLICATION_CONFIGURATION_FILE, e);
        }
    }

    public static HashMap<String, Object> getConfiguration() {
        HashMap<String, Object> map = new HashMap<>();

        for (String key : properties.stringPropertyNames()) {
            map.put(key, properties.getProperty(key));
        }

        return map;
    }

    public static String getConfigurationValue(String key) {
        return properties.getProperty(key);
    }
}