package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class SameStationsSearchException extends Exception {
    public SameStationsSearchException() {
        super(TranslationHelper.get("validation.stations.same"));
    }
}
