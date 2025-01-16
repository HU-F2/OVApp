package com.mobiliteitsfabriek.ovapp.exceptions;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;

public class NotLoggedInFavoritePermissionException extends Exception {
    public NotLoggedInFavoritePermissionException() {
        super(TranslationHelper.get("error.notLoggedIn.permission.favorite"));
    }
}
