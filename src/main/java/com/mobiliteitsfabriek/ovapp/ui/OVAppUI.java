package com.mobiliteitsfabriek.ovapp.ui;

import java.io.IOException;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.general.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.translation.TranslationResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OVAppUI extends Application {

    private static Scene scene;

    public static void goToPage(String fxml) throws IOException {
        OVAppUI.scene.setRoot(loadFXML(fxml));
    }

    // Laad het FXML-bestand in met de TranslationHelper class om vertalingen op te halen
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OVAppUI.class.getResource(UtilityFunctions.fxmlUrlFormatter(fxml)));
        fxmlLoader.setResources(new TranslationResourceBundle());

        return fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws IOException {
        OVAppUI.scene = new Scene(loadFXML("MainView"), GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        OVAppUI.scene.getStylesheets().add(OVAppUI.class.getResource("/styles/styles.css").toExternalForm());
        
        stage.setTitle(TranslationHelper.get("app.window.title"));
        stage.setScene(OVAppUI.scene);
        stage.show();
    }
}