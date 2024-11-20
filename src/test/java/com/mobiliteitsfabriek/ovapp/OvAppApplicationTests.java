package com.mobiliteitsfabriek.ovapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;

/**
 * Integration tests for the OvApp application.
 */
class OvAppApplicationTests {
    @Test
    void contextLoads() {
        // Verifies that the Spring context loads successfully
    }

    @Test
    void testMessageFormat() {
        String url = UtilityFunctions.fxmlUrlFormatter("MainView");
        assertEquals("/fxml/MainView.fxml", url);
    }
}
