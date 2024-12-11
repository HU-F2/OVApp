package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class MissingKeyException extends RuntimeException {
    public MissingKeyException(String key,String location) {
        super(TranslationHelper.get("error.missingKeyException",key,location));
    }

    public MissingKeyException(String key){
        this(key,"JSON");
    }
}