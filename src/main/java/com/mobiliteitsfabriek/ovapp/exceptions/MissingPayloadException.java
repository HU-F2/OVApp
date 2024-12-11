package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class MissingPayloadException extends RuntimeException {
    
    public MissingPayloadException() {
        super(TranslationHelper.get("error.api.missingPayload"));
    }
}