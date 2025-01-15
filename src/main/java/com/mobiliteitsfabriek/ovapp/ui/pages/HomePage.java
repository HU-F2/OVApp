package com.mobiliteitsfabriek.ovapp.ui.pages;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.UserManagement;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.DateTimePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.DepartureTimeToggleButton;
import com.mobiliteitsfabriek.ovapp.ui.components.FavoritePageButton;
import com.mobiliteitsfabriek.ovapp.ui.components.InputContainer;
import com.mobiliteitsfabriek.ovapp.ui.components.LanguagePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.SearchFieldStation;
import com.mobiliteitsfabriek.ovapp.ui.components.SwapButton;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class HomePage {
    private static VBox mainContainer;
    private static SearchFieldStation startStationField;
    private static SearchFieldStation endStationField;

    public static Scene getScene() {
        // Top bar
        FavoritePageButton favoritesPageBtn = new FavoritePageButton();

        String authText = UserManagement.userLoggedIn() ? TranslationHelper.get("home.logout") : TranslationHelper.get("home.goTo.login.button");
        Button authButton = new Button(authText);
        authButton.getStyleClass().add("goTo-login-page-button");
        authButton.setOnAction(actionEvent -> {
            if (UserManagement.userLoggedIn()) {
                logout();
            } else {
                goToLoginPage();
            }
        });
        LanguagePicker languagePicker = new LanguagePicker();

        HBox topBar = new HBox(20, languagePicker, authButton);
        topBar.setAlignment(Pos.CENTER);
        if (UserManagement.userLoggedIn()) {
            topBar.getChildren().addFirst(favoritesPageBtn);
        }
        topBar.getStyleClass().add("topBar");

        // Search fields
        startStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.start"));
        startStationField.getStyleClass().add("station-field");
        InputContainer startFieldContainer = new InputContainer(startStationField);

        endStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.end"));
        endStationField.getStyleClass().add("station-field");
        InputContainer endFieldContainer = new InputContainer(endStationField);

        SwapButton swapBtn = new SwapButton(() -> onSwap());
        swapBtn.setAccessibleText(TranslationHelper.get("home.swap.accessibleText"));
        swapBtn.setTranslateX(175);

        VBox startWithEndStation = new VBox(10);
        startWithEndStation.getStyleClass().add("station-box");
        startWithEndStation.getChildren().addAll(startFieldContainer, endFieldContainer);

        StackPane textFieldsWithButton = new StackPane(startWithEndStation, swapBtn);

        DateTimePicker dateTimeComponent = new DateTimePicker(true);
        DepartureTimeToggleButton departureToggleComponent = new DepartureTimeToggleButton();

        Button submitBtn = new Button(TranslationHelper.get("app.common.search"));
        submitBtn.getStyleClass().add("goTo-login-page-button");
        InputContainer submitBtnContainer = new InputContainer(submitBtn);

        submitBtn.setOnAction(event -> RoutesPage.handleSearch(startStationField, endStationField, dateTimeComponent.getDateTimeRFC3339Format(), departureToggleComponent.isArrival(), startFieldContainer, endFieldContainer, submitBtnContainer));

        // Favorites
        mainContainer = new VBox(textFieldsWithButton, dateTimeComponent, departureToggleComponent.departureToggleButton(), submitBtnContainer);
        mainContainer.getStyleClass().add("container");

        VBox root = new VBox(topBar, mainContainer);

        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(HomePage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }

    private static void goToLoginPage() {
        OVAppUI.switchToScene(new LoginPage().getScene());
    }

    private static void logout() {
        UserManagement.logout();
        OVAppUI.switchToScene(HomePage.getScene());
    }

    

    private static void onSwap() {
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
    }
}
