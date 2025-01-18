package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class IncorrectLoginCredentialsException extends Exception {
    public IncorrectLoginCredentialsException() {
        super(TranslationHelper.get("validation.login.credentials.incorrect"));
    }
}
