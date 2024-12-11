package com.mobiliteitsfabriek.ovapp.exceptions;

import java.util.MissingResourceException;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class MissingTranslationException extends Exception {

    public MissingTranslationException(MissingResourceException missingResourceException) {
        super(TranslationHelper.get("warning.MissingTranslationException", missingResourceException.getKey()));
    }
}
