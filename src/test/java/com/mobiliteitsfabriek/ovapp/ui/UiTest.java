package com.mobiliteitsfabriek.ovapp.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.RouteTransfersV3;
import com.mobiliteitsfabriek.ovapp.model.RouteV3;
import com.mobiliteitsfabriek.ovapp.ui.pages.RouteDetailPage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;

// 1. comment the @Disabled
// 2. Then run a test
// 3. uncomment the @Disabled
@Disabled("Only manually test functions from the 'UiTest' test class.")
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
            RouteDetailPage routeDetailPage = new RouteDetailPage(createTestData());
            Scene routeDetailPageScene = routeDetailPage.createRouteDetailScene();
            OVAppUI.switchToScene(routeDetailPageScene);
        });
    }

    private RouteV3 createTestData() {
        // Creëer een aantal routeTransfers
        RouteTransfersV3 routeTransfer1 = new RouteTransfersV3("Station Arnhem Centraal", "Station Amsterdam Zuid", "spoor 11", "perron 3", LocalDateTime.of(2024, 12, 1, 9, 16), LocalDateTime.of(2024, 12, 1, 9, 20), null, "NS Intercity", "richting A");
        RouteTransfersV3 routeTransfer2 = new RouteTransfersV3("Station Amsterdam Zuid", "Halte Station Zuid", "perron 3", "spoor 52", LocalDateTime.of(2024, 12, 1, 10, 20), LocalDateTime.of(2024, 12, 1, 10, 24), null, "NS Intercity", "richting A");
        RouteTransfersV3 stop3 = new RouteTransfersV3("Halte Station Zuid", "Halte Vijzelgracht", "spoor 52", "", LocalDateTime.of(2024, 12, 1, 10, 31), LocalDateTime.of(2024, 12, 1, 10, 36), null, "GVB Metro", "richting A");
        RouteTransfersV3 stop4 = new RouteTransfersV3("Halte Vijzelgracht", "Station Amsterdam Centraal", "", "spoor 8", LocalDateTime.of(2024, 12, 1, 10, 36), LocalDateTime.of(2024, 12, 1, 10, 41), null, "GVB Metro", "richting A");

        // Maak een route aan
        ArrayList<RouteTransfersV3> routeTransfers = new ArrayList<>(List.of(routeTransfer1, routeTransfer2, stop3, stop4));

        // RouteV3 route = new RouteV3(routeTransfers, totalDuration, travelDate, totalCost);
        RouteV3 route = new RouteV3(routeTransfers, null, "Station Arnhem Centraal", "Station Amsterdam Centraal", LocalDateTime.of(2024, 12, 1, 9, 16), LocalDateTime.of(2024, 12, 1, 9, 20), 20, 4, 15.0);

        // Toon de route details
        return route;
    }
}
