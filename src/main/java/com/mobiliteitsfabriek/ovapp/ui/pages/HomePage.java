package com.mobiliteitsfabriek.ovapp.ui.pages;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.translation.Language;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.DateTimePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.DepartureTimeToggleButton;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class HomePage {
    public static Scene getScene() {
        DateTimePicker dateTimeComponent = new DateTimePicker(true);
        DepartureTimeToggleButton departureToggleComponent = new DepartureTimeToggleButton();

        VBox root = new VBox();
        root.getStyleClass().add("container");

        SearchFieldStation startStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.start"));
        SearchFieldStation endStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.end"));
        Button submitBtn = new Button(TranslationHelper.get("app.common.search"));
        Button swapBtn = new Button("<->");

        startStationField.getStyleClass().add("station-field");
        endStationField.getStyleClass().add("station-field");
        submitBtn.getStyleClass().add("submit-btn");
        swapBtn.getStyleClass().add("submit-btn");

        swapBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            String startValue = startStationField.getValue().replace("'", "’");
            String endValue = endStationField.getValue().replace("'", "’");

            startStationField.setValue(endValue);
            endStationField.setValue(startValue);

            if (endValue != null) {
                startStationField.getSelectionModel().select(endValue);
            }

            if (startValue != null) {
                endStationField.getSelectionModel().select(startValue);
            }

            startStationField.hide();
            endStationField.hide();
        });

        submitBtn.setOnAction(event -> {
            RoutesPage.handleSearch(startStationField, endStationField, dateTimeComponent, departureToggleComponent.isArrival());
        });

        Button toggleLanguageBtn = new Button(TranslationHelper.get("toggleLanguage"));
        toggleLanguageBtn.getStyleClass().add("submit-btn");
        VBox.setMargin(toggleLanguageBtn, new Insets(50,0,0,0));
        toggleLanguageBtn.setOnAction((event)->{
            System.out.println(GlobalConfig.currentLanguage);
            if(GlobalConfig.currentLanguage == Language.DUTCH){
                GlobalConfig.setLanguage(Language.ENGLISH);
                OVAppUI.switchToScene(HomePage.getScene());
            }else{
                GlobalConfig.setLanguage(Language.DUTCH);
                OVAppUI.switchToScene(HomePage.getScene());
            }
        });

        root.getChildren().addAll(startStationField, endStationField, dateTimeComponent, departureToggleComponent.departureToggleButton(), swapBtn, submitBtn,toggleLanguageBtn);
        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(HomePage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }
}
