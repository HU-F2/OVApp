package com.mobiliteitsfabriek.ovapp.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;

import javafx.stage.Stage;

public class LoginUITest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        OVAppUI ovAppUI = new OVAppUI();
        ovAppUI.start(stage);
    }

    @Test
    void testValidLogin() {
        ServiceTest.executeUserWorkflow(() -> {
            // Ga naar de login pagina
            clickOn("#loginButton");

            // Voer een geldige gebruikersnaam en wachtwoord in
            clickOn("#usernameField").write(GlobalConfig.TEST_USERNAME);
            clickOn("#passwordField").write(GlobalConfig.TEST_PASSWORD);

            // Klik op de login-knop
            clickOn("#submitButton");

            // Controleer of de Home-pagina is geladen
            assertTrue(lookup("#homePageMainContainer").tryQuery().isPresent(), "Home page should be loaded after a successful login.");
        });
    }

    @Test
    void testInvalidPassword() {
        // Ga naar de login pagina
        clickOn("#loginButton");

        // Voer een geldige gebruikersnaam en een ongeldig wachtwoord in
        clickOn("#usernameField").write(GlobalConfig.TEST_USERNAME);
        clickOn("#passwordField").write("WrongPassword");

        // Klik op de login-knop
        clickOn("#submitButton");

        // Controleer of een foutmelding wordt weergegeven
        assertTrue(lookup(".invalid-input-label").tryQuery().isPresent(), "An error message should be displayed for invalid login.");
    }

    @Test
    void testEmptyFields() {
        // Ga naar de login pagina
        clickOn("#loginButton");

        // Laat beide velden leeg en klik op de login-knop
        clickOn("#submitButton");

        // Controleer of foutmeldingen voor beide velden worden weergegeven
        assertTrue(lookup(".invalid-input-label").tryQuery().isPresent(), "Error messages should be displayed for empty fields.");
    }
}
