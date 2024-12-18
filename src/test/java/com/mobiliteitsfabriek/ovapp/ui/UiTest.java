package com.mobiliteitsfabriek.ovapp.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.RouteTransfers;
import com.mobiliteitsfabriek.ovapp.ui.pages.LoginPage;
import com.mobiliteitsfabriek.ovapp.ui.pages.RouteDetailPage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;

// 1. comment the @Disabled
// 2. Then run a test
// 3. uncomment the @Disabled
// @Disabled("Only manually test functions from the 'UiTest' test class.")
public class UiTest {

    public static synchronized void startJavaFXThread(Runnable runnable) {
        // Start JavaFX-runtime in een nieuwe thread
        new Thread(() -> Application.launch(OVAppUI.class)).start();

        // Zorg ervoor dat de UI-runtime tijd heeft om te starten
        try {
            Thread.sleep(2000); // Wacht kort om JavaFX volledig op te starten
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            try {
                runnable.run(); // Voer de meegegeven code uit
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Houd de test draaiend om de JavaFX-code te laten uitvoeren
        // Sluit de app na GlobalConfig.SHOW_TEST_PAGE_DURATION aantal milliseconde.
        try {
            Thread.sleep(GlobalConfig.SHOW_TEST_PAGE_DURATION); // Wacht totdat de test voltooid is
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRouteDetailPageV4WithCustomTestData() {
        UiTest.startJavaFXThread(() -> {
            RouteDetailPage routeDetailPage = new RouteDetailPage(createTestData(), new ArrayList<>());
            Scene routeDetailPageScene = routeDetailPage.createRouteDetailScene();
            OVAppUI.switchToScene(routeDetailPageScene);
        });
    }

    @Test
    void testLoginPage() {
        UiTest.startJavaFXThread(() -> {
            LoginPage loginPage = new LoginPage();
            OVAppUI.switchToScene(loginPage.getScene());
        });
    }

    private Route createTestData() {
        // Creëer een aantal routeTransfers
        RouteTransfers routeTransfer1 = new RouteTransfers("Station Arnhem Centraal", "Station Amsterdam Zuid", "spoor 11", "perron 3", LocalDateTime.of(2024, 12, 1, 9, 16), LocalDateTime.of(2024, 12, 1, 9, 20), null, "NS Intercity", "richting A");
        RouteTransfers routeTransfer2 = new RouteTransfers("Station Amsterdam Zuid", "Halte Station Zuid", "perron 3", "spoor 52", LocalDateTime.of(2024, 12, 1, 10, 20), LocalDateTime.of(2024, 12, 1, 10, 24), null, "NS Intercity", "richting A");
        RouteTransfers stop3 = new RouteTransfers("Halte Station Zuid", "Halte Vijzelgracht", "spoor 52", "", LocalDateTime.of(2024, 12, 1, 10, 31), LocalDateTime.of(2024, 12, 1, 10, 36), null, "GVB Metro", "richting A");
        RouteTransfers stop4 = new RouteTransfers("Halte Vijzelgracht", "Station Amsterdam Centraal", "", "spoor 8", LocalDateTime.of(2024, 12, 1, 10, 36), LocalDateTime.of(2024, 12, 1, 10, 41), null, "GVB Metro", "richting A");

        // Maak een route aan
        ArrayList<RouteTransfers> routeTransfers = new ArrayList<>(List.of(routeTransfer1, routeTransfer2, stop3, stop4));

        Route route = new Route(routeTransfers, null, "Station Arnhem Centraal", "Station Amsterdam Centraal", "spoor 11", LocalDateTime.of(2024, 12, 1, 9, 16), LocalDateTime.of(2024, 12, 1, 9, 20), 20, 4, 15.0);

        // Toon de route details
        return route;
    }
}
