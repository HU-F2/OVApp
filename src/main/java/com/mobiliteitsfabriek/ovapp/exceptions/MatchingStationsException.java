package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.enums.InputKey;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class MatchingStationsException extends InputException {
    public MatchingStationsException(InputKey inputKey) {
        super(TranslationHelper.get("validation.matchingStations"), inputKey);
    }
}
