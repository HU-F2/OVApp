package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class RuntimeParseException extends RuntimeException {
    
    public RuntimeParseException(String parseClassName, Throwable cause) {
        super(TranslationHelper.get("error.api.parse", parseClassName), cause);
    }
}
