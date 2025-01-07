package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.enums.InputKey;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class InvalidRouteException extends InputException {
    public InvalidRouteException(InputKey inputKey){
        super(TranslationHelper.get("validation.invalidRoute"), inputKey);
    }
}
