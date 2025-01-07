package com.mobiliteitsfabriek.ovapp.ui.components;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.translation.Language;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.pages.HomePage;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

public class LanguagePicker extends ComboBox<Language> {

    public LanguagePicker() {
        ObservableList<Language> options = FXCollections.observableArrayList(Language.values());
        this.setItems(options);
        this.setValue(GlobalConfig.currentLanguage);

        // On value selected
        this.valueProperty().addListener((ObservableValue<? extends Language> observable, Language oldValue, Language newValue) -> {
            if (newValue != null) {
                GlobalConfig.setLanguage(newValue);
                OVAppUI.switchToScene(HomePage.getScene());
            }
        });

        // List with translations
        this.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Language languageItem, boolean empty) {
                super.updateItem(languageItem, empty);
                if (empty || languageItem == null) {
                    setText(null);
                } else {
                    setText(TranslationHelper.get(languageItem.getTranslationKey())); // Customize the display
                }
            }
        });

        // Selected value translations
        this.setConverter(new StringConverter<>() {
            @Override
            public String toString(Language languageItem) {
                return languageItem == null ? null : TranslationHelper.get(languageItem.getTranslationKey());
            }

            @Override
            public Language fromString(String string) {
                return Language.getLanguageFromTranslationKey(string);
            }
        });
    }

}
