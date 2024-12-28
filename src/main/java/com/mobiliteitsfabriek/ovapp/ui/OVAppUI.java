package com.mobiliteitsfabriek.ovapp.ui;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.service.SeedingService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.pages.HomePage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OVAppUI extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        final Scene scene = HomePage.getScene();

        primaryStage.setTitle(TranslationHelper.get("app.window.title"));
        primaryStage.setScene(scene);
        primaryStage.show();

        SeedingService service = new SeedingService();
        service.getAllStations();
    }

    public static void switchToScene(Scene scene) {
        // FIXME: setIconified true and then false is the only way i got the screenreader to start reading again
        //       after scene switch, if you find a better way please fix. 
        if (GlobalConfig.isUsingScreenreader) {
            stage.setIconified(true);
        }
        stage.setScene(scene);
        if (GlobalConfig.isUsingScreenreader) {
            stage.setIconified(false);
        }
    }
}