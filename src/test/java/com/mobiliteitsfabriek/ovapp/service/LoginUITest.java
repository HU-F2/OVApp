package com.mobiliteitsfabriek.ovapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.ui.pages.LoginPage;

import javafx.stage.Stage;

public class LoginUITest extends ApplicationTest {

    @Override
    public void start(Stage stage) {
        LoginPage loginPage = new LoginPage();
        stage.setScene(loginPage.getScene());
        stage.show();
    }

    @Test
    void testValidLogin(FxRobot robot) {
        ServiceTest.executeUserWorkflow(() -> {
            robot.clickOn("#usernameField").write(GlobalConfig.TEST_USERNAME);
            robot.clickOn("#passwordField").write(GlobalConfig.TEST_PASSWORD);
            robot.clickOn("#submitButton");
    
            // Assert that the login was successful
            assertTrue(robot.lookup("#homePageMainContainer").tryQuery().isPresent(), "Home page should be loaded after a successful login.");
        });       
    }

    // @Test
    // void testValidLogin() {
    //     ServiceTest.executeUserWorkflow(() -> {
    //         // Voer een geldige gebruikersnaam en wachtwoord in
    //         clickOn("#usernameField").write(GlobalConfig.TEST_USERNAME);
    //         clickOn("#passwordField").write(GlobalConfig.TEST_PASSWORD);

    //         // Klik op de login-knop
    //         clickOn("#submitButton");

    //         // Controleer of de Home-pagina is geladen
    //         assertTrue(lookup("#homePageMainContainer").tryQuery().isPresent(), "Home page should be loaded after a successful login.");
    //     });
    // }

    // @Test
    // void testInvalidPassword() {
    //     // Voer een geldige gebruikersnaam en een ongeldig wachtwoord in
    //     clickOn("#usernameField").write("TestUserBas1");
    //     clickOn("#passwordField").write("WrongPassword");

    //     // Klik op de login-knop
    //     clickOn("#submitButton");

    //     // Controleer of een foutmelding wordt weergegeven
    //     assertTrue(lookup(".invalid-input-label").tryQuery().isPresent(), "An error message should be displayed for invalid login.");
    //     assertEquals("Incorrect password", lookup(".invalid-input-label").queryLabeled().getText());
    // }

    // @Test
    // void testEmptyFields() {
    //     // Laat beide velden leeg en klik op de login-knop
    //     clickOn("#submitButton");

    //     // Controleer of foutmeldingen voor beide velden worden weergegeven
    //     assertTrue(lookup(".invalid-input-label").tryQuery().isPresent(), "Error messages should be displayed for empty fields.");
    // }
}
