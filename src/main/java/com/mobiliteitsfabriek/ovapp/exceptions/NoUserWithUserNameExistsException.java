package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class NoUserWithUserNameExistsException extends Exception {

    public NoUserWithUserNameExistsException() {
        super(TranslationHelper.get("validation.username.not.exists"));
    }
}
