package com.mobiliteitsfabriek.ovapp.ui.pages;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.enums.InputKey;
import com.mobiliteitsfabriek.ovapp.exceptions.InputException;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingFieldException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoStationFoundException;
import com.mobiliteitsfabriek.ovapp.exceptions.SameStationsSearchException;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.translation.Language;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.DateTimePicker;
import com.mobiliteitsfabriek.ovapp.ui.components.DepartureTimeToggleButton;
import com.mobiliteitsfabriek.ovapp.ui.components.InputContainer;
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
    private static InputContainer startStationInputContainer;
    private static InputContainer endStationInputContainer;

    private static SearchFieldStation startStationField;
    private static SearchFieldStation endStationField;

    private static DateTimePicker dateTimeComponent;
    private static DepartureTimeToggleButton departureToggleComponent;

    public static Scene getScene() {
        dateTimeComponent = new DateTimePicker(true);
        departureToggleComponent = new DepartureTimeToggleButton();

        startStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.start"));
        startStationField.getStyleClass().add("station-field");
        startStationInputContainer = new InputContainer(TranslationHelper.get("home.startStation.label"), startStationField);

        endStationField = new SearchFieldStation(StationService.getAllStationNames(), TranslationHelper.get("searchFieldStation.end"));
        endStationField.getStyleClass().add("station-field");
        endStationInputContainer = new InputContainer(TranslationHelper.get("home.endStation.label"), endStationField);

        Button submitBtn = new Button(TranslationHelper.get("app.common.search"));
        submitBtn.getStyleClass().add("submit-btn");
        submitBtn.setOnAction(event -> {
            handleSearchButton();
        });

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

        VBox startWithEndStation = new VBox(10);
        startWithEndStation.getStyleClass().add("station-box");
        startWithEndStation.getChildren().addAll(startStationInputContainer, endStationInputContainer);
        startWithEndStation.setAlignment(Pos.CENTER);

        StackPane textFieldsWithButton = new StackPane(startWithEndStation, swapBtn);
        swapBtn.setTranslateX(175);

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

        Button goToLoginButton = new Button(TranslationHelper.get("home.goTo.login.button"));
        goToLoginButton.getStyleClass().add("goTo-login-page-button");
        goToLoginButton.setOnAction(actionEvent -> goToLoginPage());

        HBox topBar = new HBox(goToLoginButton);
        topBar.getStyleClass().add("topBar");

        VBox mainContainer = new VBox(textFieldsWithButton, dateTimeComponent, departureToggleComponent.departureToggleButton(), submitBtn, toggleLanguageBtn);
        mainContainer.getStyleClass().add("container");

        VBox root = new VBox(topBar, mainContainer);
        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(HomePage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }

    private static void handleSearchButton() {
        resetErrorFields();
        try {
            RoutesPage.handleSearch(startStationField.getValidValueString(), endStationField.getValidValueString(), dateTimeComponent, departureToggleComponent.isArrival());
        } catch (MissingFieldException e) {
            setErrorFields(e);
        } catch (SameStationsSearchException e) {
            startStationInputContainer.addError(e.getMessage());
        } catch (NoStationFoundException e) {
            e.printStackTrace();
        }
    }

    private static void resetErrorFields() {
        startStationInputContainer.noError();
        endStationInputContainer.noError();
    }

    private static void setErrorFields(InputException exception) {
        if (exception.getInputKey().equals(InputKey.STARTSTATION)) {
            startStationInputContainer.addError(exception.getMessage());
        } else {
            endStationInputContainer.addError(exception.getMessage());
        }
    }

    private static void goToLoginPage() {
        OVAppUI.switchToScene(new LoginPage().getScene());
    }
}
