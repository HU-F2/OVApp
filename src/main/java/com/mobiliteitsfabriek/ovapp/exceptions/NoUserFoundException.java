package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class NoUserFoundException extends Exception {
    public NoUserFoundException() {
        super(TranslationHelper.get("error.no.user.found"));
    }
}
