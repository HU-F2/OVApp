package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(int responseCode, String responseMessage) {
        super(TranslationHelper.get("error.apiRequestError", responseCode, responseMessage));
    }
}