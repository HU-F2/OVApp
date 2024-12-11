package com.mobiliteitsfabriek.ovapp.translation;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingTranslationException;

public class TranslationHelper {
    private static ResourceBundle bundle;
    private static ResourceBundle defaultBundle;

    // Stel de taal in en laad de ResourceBundle (bestand met vertalingen van een bepaalde taal)
    public static void setLanguage(Language language) {
        Locale locale = Locale.forLanguageTag(language.getCode());
        bundle = ResourceBundle.getBundle("translations.translation", locale);

        Locale defaultLocale = Locale.forLanguageTag(GlobalConfig.DEFAULT_LANGUAGE.getCode());
        defaultBundle = ResourceBundle.getBundle("translations.translation", defaultLocale);
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            try {
                return defaultBundle.getString(key);
            } catch (MissingResourceException ex) {
                printMissingResourceException(ex);
                return key;
            }
        }
    }

    public static String get(String key, Object... args) {
        String translation = null;
        try {
            translation = bundle.getString(key);
        } catch (MissingResourceException e) {
            try {
                translation = defaultBundle.getString(key);
            } catch (MissingResourceException ex) {
                printMissingResourceException(ex);
                return key;
            }
        }

        return MessageFormat.format(translation, args);
    }

    private static void printMissingResourceException(MissingResourceException ex) {
        if (GlobalConfig.NO_TRANSLATION_KEY_ERROR_CONSOLE) {
            System.out.println(new MissingTranslationException(ex).getMessage());
        }
    }

    public static Enumeration<String> getKeys() {
        return bundle.getKeys();
    }

    public static Enumeration<String> getAllKeys() {
        Set<String> keysSet = new LinkedHashSet<>();

        if (defaultBundle != null) {
            keysSet.addAll(Collections.list(defaultBundle.getKeys()));
        }

        if (bundle != null) {
            keysSet.addAll(Collections.list(bundle.getKeys()));
        }

        return Collections.enumeration(keysSet);
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static ResourceBundle getDefaultBundle() {
        return defaultBundle;
    }

    // Standaard taal instellen bij de eerste keer laden
    static {
        setLanguage(GlobalConfig.DEFAULT_LANGUAGE);
    }
}
