package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class ExistingFavoriteException extends Exception {

    public ExistingFavoriteException() {
        super(TranslationHelper.get("validation.favoriteRoute.exists"));
    }
}
