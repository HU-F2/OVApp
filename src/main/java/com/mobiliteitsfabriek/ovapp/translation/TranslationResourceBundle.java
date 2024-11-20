package com.mobiliteitsfabriek.ovapp.translation;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class TranslationResourceBundle extends ResourceBundle {

    @Override
    protected Object handleGetObject(String key) {
        // Gebruik TranslationHelper om de vertaling op te halen
        return TranslationHelper.get(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        // Haal alle sleutels op via TranslationHelper
        return TranslationHelper.getAllKeys();
    }

    // De TranslationHelper class handelt het gebrek van een vertaling af in plaats van de FXMLLoader. Met deze functie wordt de fout van de FXMLLoader voorkomen.
    // Fout van de FXMLLoader: javafx.fxml.LoadException: Resource "..." not found.
    @Override
    public boolean containsKey(String key) {
        return true;
    }
}