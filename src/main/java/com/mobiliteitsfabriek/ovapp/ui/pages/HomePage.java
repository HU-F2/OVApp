package com.mobiliteitsfabriek.ovapp.ui.pages;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.translation.Language;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.DateTimePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.DepartureTimeToggleButton;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;
import com.mobiliteitsfabriek.ovapp.ui.components.SwapButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HomePage {
    public static Scene getScene() {
        DateTimePicker dateTimeComponent = new DateTimePicker(true);
        DepartureTimeToggleButton departureToggleComponent = new DepartureTimeToggleButton();

        SearchFieldStation startStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.start"));
        SearchFieldStation endStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.end"));
        Button submitBtn = new Button(TranslationHelper.get("app.common.search"));

        startStationField.getStyleClass().add("station-field");
        endStationField.getStyleClass().add("station-field");
        submitBtn.getStyleClass().add("submit-btn");

        SwapButton swapBtn = new SwapButton(() -> {
            String startValue = startStationField.getValue();
            String endValue = endStationField.getValue();

            if (endValue != null) {
                endValue = endValue.replace("'", "’");
                startStationField.getSelectionModel().select(endValue);
            }

            if (startValue != null) {
                startValue = startValue.replace("'", "’");
                endStationField.getSelectionModel().select(startValue);
            }

            startStationField.setValue(endValue);
            endStationField.setValue(startValue);

            startStationField.hide();
            endStationField.hide();
        });
        swapBtn.setAccessibleText(TranslationHelper.get("home.swap.accessibleText"));

        submitBtn.setOnAction(event -> {
            RoutesPage.handleSearch(startStationField, endStationField, dateTimeComponent, departureToggleComponent.isArrival());
        });

        Button toggleLanguageBtn = new Button(TranslationHelper.get("toggleLanguage"));
        toggleLanguageBtn.getStyleClass().add("submit-btn");
        VBox.setMargin(toggleLanguageBtn, new Insets(50, 0, 0, 0));
        toggleLanguageBtn.setOnAction((event) -> {
            if (GlobalConfig.currentLanguage == Language.DUTCH) {
                GlobalConfig.setLanguage(Language.ENGLISH);
                OVAppUI.switchToScene(HomePage.getScene());
            } else {
                GlobalConfig.setLanguage(Language.DUTCH);
                OVAppUI.switchToScene(HomePage.getScene());
            }
        });

        VBox startWithEndStation = new VBox(10);
        startWithEndStation.getStyleClass().add("station-box");
        startWithEndStation.getChildren().addAll(startStationField, endStationField);
        startWithEndStation.setAlignment(Pos.CENTER);

        StackPane textFieldsWithButton = new StackPane(startWithEndStation, swapBtn);
        swapBtn.setTranslateX(175);

        Button goToLoginButton = new Button(TranslationHelper.get("home.goTo.login.button"));
        goToLoginButton.getStyleClass().add("goTo-login-page-button");
        goToLoginButton.setId("loginButton");
        goToLoginButton.setOnAction(actionEvent -> goToLoginPage());

        HBox topBar = new HBox(goToLoginButton);
        topBar.getStyleClass().add("topBar");

        VBox mainContainer = new VBox(textFieldsWithButton, dateTimeComponent, departureToggleComponent.departureToggleButton(), submitBtn, toggleLanguageBtn);
        mainContainer.getStyleClass().add("container");
        mainContainer.setId("homePageMainContainer");

        VBox root = new VBox(topBar, mainContainer);
        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(HomePage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }

    private static void goToLoginPage() {
        OVAppUI.switchToScene(new LoginPage().getScene());
    }
}
