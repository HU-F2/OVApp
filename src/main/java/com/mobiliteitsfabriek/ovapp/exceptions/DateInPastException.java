package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.enums.InputKey;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class DateInPastException extends InputException {
    public DateInPastException(InputKey inputKey) {
        super(TranslationHelper.get("validation.dateInPast"), inputKey);
    }
}