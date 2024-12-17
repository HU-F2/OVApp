package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.enums.InputKey;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class MissingFieldException extends InputException {

    public MissingFieldException(InputKey inputKey) {
        super(TranslationHelper.get("validation.missingField", inputKey), inputKey);
    }
}
