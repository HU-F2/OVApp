package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class InvalidUsernameException extends Exception {

    public InvalidUsernameException() {
        super(TranslationHelper.get("validation.username.pattern"));
    }
}
