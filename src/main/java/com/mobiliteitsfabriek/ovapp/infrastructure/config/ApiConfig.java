package com.mobiliteitsfabriek.ovapp.infrastructure.config;

import java.io.InputStream;
import java.util.Properties;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

import lombok.Data;

/**
 * Configuration class for API settings.
 * Loads and holds API-related configuration values from a properties file.
 */
@Data
public class ApiConfig {
    /** Base URL for the API endpoints */
    private String apiBaseUrl;

    /** Authentication key for API access */
    private String apiKey;

    /**
     * Loads API configuration from the config.properties file.
     * 
     * @return ApiConfig instance populated with values from the properties file
     * @throws RuntimeException if the configuration file cannot be loaded or read
     */
    public static ApiConfig load() {
        final ApiConfig config = new ApiConfig();
        try (InputStream input = ApiConfig.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            final Properties prop = new Properties();
            prop.load(input);

            config.setApiBaseUrl(prop.getProperty("api.baseUrl"));
            config.setApiKey(prop.getProperty("api.key"));

            return config;
        } catch (Exception e) {
            throw new RuntimeException(TranslationHelper.get("error.api.configuration"), e);
        }
    }
}