package com.mobiliteitsfabriek.ovapp.model;

import com.mobiliteitsfabriek.ovapp.enums.UserType;
import com.mobiliteitsfabriek.ovapp.exceptions.ExistingUserException;
import com.mobiliteitsfabriek.ovapp.exceptions.IncorrectPasswordException;
import com.mobiliteitsfabriek.ovapp.exceptions.InvalidPasswordException;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingFieldException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserFoundException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserWithUserNameExistsException;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.general.ValidationFunctions;

public class UserManagement {
    private static User loggedInUser;

    public static void createUser(String username, String password) throws ExistingUserException, MissingFieldException, InvalidPasswordException {
        // Validate the gegevens bij het aanmaken van een account
        ValidationFunctions.validateAccountCreation(username, password);

        // Maak een beveiligd wachtwoord.
        String encryptedPassword = UtilityFunctions.encodePassword(password);

        User user = new User(UtilityFunctions.generateID(), username, encryptedPassword);
        // TODO: add user to database or local json database
    }

    public static boolean loginUser(String username, String password) throws MissingFieldException, NoUserWithUserNameExistsException, NoUserFoundException, IncorrectPasswordException {
        User user = ValidationFunctions.validateLogin(username, password);

        if (!UtilityFunctions.checkEmpty(user)) {
            setLoggedInUser(user);
            return true;
        }
        return false;
    }

    public static boolean hasPermission(UserType userType) {
        if (!userLoggedIn()) {
            return false;
        }

        return getLoggedInUser().getUserType().equals(userType);
    }

    public static boolean userLoggedIn() {
        return !UtilityFunctions.checkEmpty(UserManagement.loggedInUser);
    }

    // GETTERS AND SETTERS
    public static void logout() {
        UserManagement.setLoggedInUser(null);
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        UserManagement.loggedInUser = loggedInUser;
    }
}
