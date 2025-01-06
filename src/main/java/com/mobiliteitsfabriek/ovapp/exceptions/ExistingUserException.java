package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class ExistingUserException extends Exception {

    public ExistingUserException(String username) {
        super(TranslationHelper.get("validation.username.exists", username));
    }
}
