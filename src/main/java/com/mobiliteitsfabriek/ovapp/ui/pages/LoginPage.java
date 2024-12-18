package com.mobiliteitsfabriek.ovapp.ui.pages;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.enums.InputKey;
import com.mobiliteitsfabriek.ovapp.exceptions.IncorrectPasswordException;
import com.mobiliteitsfabriek.ovapp.exceptions.InputException;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingFieldException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserFoundException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserWithUserNameExistsException;
import com.mobiliteitsfabriek.ovapp.model.UserManagement;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.InputContainer;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginPage {
    private final Scene scene;

    private final VBox layoutOuter;
    private final VBox layout;
    private final InputContainer usernameInputContainer;
    private final InputContainer passwordInputContainer;

    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Button submitButton;
    private Button guestLoginButton;

    public LoginPage() {
        layoutOuter = new VBox(10);
        layoutOuter.getStyleClass().add("login-layoutOuter");
        layout = new VBox(10);
        layout.getStyleClass().add("login-form");

        // Title
        Label title = new Label(TranslationHelper.get("login.title"));
        title.getStyleClass().add("login-title");

        // Username Field
        usernameField = new TextField();
        usernameField.setPromptText(TranslationHelper.get("login.username.inputPrompt"));
        usernameField.getStyleClass().add("login-input");
        usernameInputContainer = new InputContainer(TranslationHelper.get("login.username.label"), usernameField);

        // Password Field
        passwordField = new PasswordField();
        passwordField.setPromptText(TranslationHelper.get("login.password.inputPrompt"));
        passwordField.getStyleClass().add("login-input");
        passwordInputContainer = new InputContainer(TranslationHelper.get("login.password.label"), passwordField);

        // guest login Button
        guestLoginButton = null;
        if (GlobalConfig.GUEST_LOGIN_BUTTON) {
            guestLoginButton = new Button(TranslationHelper.get("login.guestLogin.button"));
            guestLoginButton.getStyleClass().add("login-guestLogin");
            guestLoginButton.setOnAction(actionEvent -> handleLoginGuest());
        }

        // Submit Button
        submitButton = new Button(TranslationHelper.get("login.submit.button"));
        submitButton.getStyleClass().add("login-submit");
        submitButton.setOnAction(actionEvent -> handleLogin());

        if (GlobalConfig.GUEST_LOGIN_BUTTON) {
            layout.getChildren().addAll(title, usernameInputContainer, passwordInputContainer, submitButton, guestLoginButton);
        } else {
            layout.getChildren().addAll(title, usernameInputContainer, passwordInputContainer, submitButton);
        }
        layoutOuter.getChildren().add(layout);

        scene = new Scene(layoutOuter, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(RoutesPage.class.getResource("/styles/styles.css").toExternalForm());
    }

    public Scene getScene() {
        return scene;
    }

    private void handleLogin() {
        boolean isValid = false;
        resetErrorFields();
        try {
            isValid = UserManagement.loginUser(usernameField.getText(), passwordField.getText());
        } catch (MissingFieldException e) {
            setErrorFields(e);
        } catch (NoUserWithUserNameExistsException e) {
            usernameInputContainer.addError(e.getMessage());
        } catch (IncorrectPasswordException e) {
            passwordInputContainer.addError(e.getMessage());
        } catch (NoUserFoundException e) {
            e.printStackTrace();
        }

        if (isValid) {
            System.out.println("Login successful");
            OVAppUI.switchToScene(HomePage.getScene());
        }
    }

    private void handleLoginGuest() {
        OVAppUI.switchToScene(HomePage.getScene());
    }

    private void resetErrorFields() {
        usernameInputContainer.noError();
        passwordInputContainer.noError();
    }

    private void setErrorFields(InputException exception) {
        if (exception.getInputKey().equals(InputKey.USERNAME)) {
            usernameInputContainer.addError(exception.getMessage());
        } else {
            passwordInputContainer.addError(exception.getMessage());
        }
    }
}
