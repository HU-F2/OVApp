package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class IncorrectPasswordException extends Exception {

    public IncorrectPasswordException() {
        super(TranslationHelper.get("validation.password.incorrect"));
    }
}