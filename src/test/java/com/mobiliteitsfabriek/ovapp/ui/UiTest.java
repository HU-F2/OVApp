package com.mobiliteitsfabriek.ovapp.ui;

import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.RouteV3;
import com.mobiliteitsfabriek.ovapp.model.Stop;
import com.mobiliteitsfabriek.ovapp.model.StopV3;
import com.mobiliteitsfabriek.ovapp.service.RouteService;
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
    void testRouteDetailPage() {
        UiTest.startJavaFXThread(() -> {
            RouteService routeService = new RouteService();

            String startStationId = "ASD";
            String stopStationId = "AMF";
            List<Route> routes = routeService.getRoutes(startStationId, stopStationId);
            assumeFalse(routes == null || routes.isEmpty() || routes.get(0) == null);

            RouteDetailPage routeDetailPage = new RouteDetailPage(routes.get(0));
            Scene routeDetailPageScene = routeDetailPage.createRouteDetailSceneV2();
            OVAppUI.switchToScene(routeDetailPageScene);
        });
    }

    @Test
    void testRouteDetailPageWithCustomTestData() {
        UiTest.startJavaFXThread(() -> {
            RouteDetailPage routeDetailPage = new RouteDetailPage(createTestData());
            Scene routeDetailPageScene = routeDetailPage.createRouteDetailSceneV2();
            OVAppUI.switchToScene(routeDetailPageScene);
        });
    }

    @Test
    void testRouteDetailPageV3WithCustomTestData() {
        UiTest.startJavaFXThread(() -> {
            RouteDetailPage routeDetailPage = new RouteDetailPage(createTestDataV3());
            Scene routeDetailPageScene = routeDetailPage.createRouteDetailSceneV3();
            OVAppUI.switchToScene(routeDetailPageScene);
        });
    }

    @Test
    void testRouteDetailPageV4WithCustomTestData() {
        UiTest.startJavaFXThread(() -> {
            RouteDetailPage routeDetailPage = new RouteDetailPage(createTestDataV3());
            Scene routeDetailPageScene = routeDetailPage.createRouteDetailSceneV4();
            OVAppUI.switchToScene(routeDetailPageScene);
        });
    }

    private Route createTestData() {
        // Creëer een aantal stops
        Stop stop1 = new Stop("Station Arnhem Centraal", "11", LocalDateTime.of(2024, 12, 1, 9, 16), "NS Intercity", "Schiphol Airport");
        Stop stop2 = new Stop("Station Amsterdam Zuid", "3", LocalDateTime.of(2024, 12, 1, 10, 20), "NS Intercity", "Schiphol Airport");
        Stop stop3 = new Stop("Halte Station Zuid", "Metro 52", LocalDateTime.of(2024, 12, 1, 10, 31), "GVB Metro", "Noord");
        Stop stop4 = new Stop("Halte Vijzelgracht", "8", LocalDateTime.of(2024, 12, 1, 10, 36), "GVB Metro", "Noord");

        // Maak een route aan
        ArrayList<Stop> stops = new ArrayList<>(List.of(stop1, stop2, stop3, stop4));
        int totalDuration = 80;
        LocalDateTime travelDate = LocalDateTime.of(2024, 12, 1, 9, 16);
        double totalCost = 12.50;

        Route route = new Route(stops, totalDuration, travelDate, totalCost);

        // Toon de route details
        return route;
    }

    private RouteV3 createTestDataV3() {
        // Creëer een aantal stops
        StopV3 stop1 = new StopV3("Station Arnhem Centraal", "Station Amsterdam Zuid", "spoor 11", "perron 3", LocalDateTime.of(2024, 12, 1, 9, 16), LocalDateTime.of(2024, 12, 1, 9, 20), null, "NS Intercity", null);
        StopV3 stop2 = new StopV3("Station Amsterdam Zuid", "Halte Station Zuid", "perron 3", "spoor 52", LocalDateTime.of(2024, 12, 1, 10, 20), LocalDateTime.of(2024, 12, 1, 10, 24), null, "NS Intercity", null);
        StopV3 stop3 = new StopV3("Halte Station Zuid", "Halte Vijzelgracht", "spoor 52", "", LocalDateTime.of(2024, 12, 1, 10, 31), LocalDateTime.of(2024, 12, 1, 10, 36), null, "GVB Metro", null);
        StopV3 stop4 = new StopV3("Halte Vijzelgracht", "Station Amsterdam Centraal", "", "spoor 8", LocalDateTime.of(2024, 12, 1, 10, 36), LocalDateTime.of(2024, 12, 1, 10, 41), null, "GVB Metro", null);

        // Maak een route aan
        ArrayList<StopV3> stops = new ArrayList<>(List.of(stop1, stop2, stop3, stop4));

        // RouteV3 route = new RouteV3(stops, totalDuration, travelDate, totalCost);
        RouteV3 route = new RouteV3("Station Arnhem Centraal", "Station Amsterdam Centraal", 15, stops);

        // Toon de route details
        return route;
    }
}
