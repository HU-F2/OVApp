package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class RuntimeConfigException extends RuntimeException {
    
    public RuntimeConfigException(String configurationFileUrl, Throwable cause) {
        super(TranslationHelper.get("error.config.notFound", configurationFileUrl), cause);
    }
}
