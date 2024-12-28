package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class NoStationFoundException extends Exception{
    public NoStationFoundException() {
        super(TranslationHelper.get("error.no.station.found"));
    }
}
