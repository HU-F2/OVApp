package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class NoNullArgumentAllowed extends Error {
    
    public NoNullArgumentAllowed() {
        super(TranslationHelper.get("Null argument is not allowed."));
    }
}
