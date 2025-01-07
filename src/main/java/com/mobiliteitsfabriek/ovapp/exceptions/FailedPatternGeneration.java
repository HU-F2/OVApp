package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class FailedPatternGeneration extends Exception {
    public FailedPatternGeneration() {
        super(TranslationHelper.get("error.generate.pattern"));
    }
}