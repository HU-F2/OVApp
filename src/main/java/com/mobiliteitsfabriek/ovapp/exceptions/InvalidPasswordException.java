package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException() {
        super(TranslationHelper.get("validation.password.pattern"));
    }
}
