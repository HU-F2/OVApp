package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.enums.InputKey;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class StationNotFoundException extends InputException {
    
    public StationNotFoundException(InputKey inputKey){
        super(TranslationHelper.get("validation.stationNotFound"), inputKey);
    }
    
}
