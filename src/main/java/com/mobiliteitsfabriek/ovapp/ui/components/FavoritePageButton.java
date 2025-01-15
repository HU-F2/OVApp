package com.mobiliteitsfabriek.ovapp.ui.components;

import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.pages.FavoritePage;

import javafx.scene.control.Button;

public class FavoritePageButton extends Button{
    public FavoritePageButton(){
        this.setText(TranslationHelper.get("favorites"));
        this.getStyleClass().add("goTo-login-page-button");
        this.setOnAction(event -> {
            OVAppUI.switchToScene(FavoritePage.getScene());
        });
    }
}
