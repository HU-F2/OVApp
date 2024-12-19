package com.mobiliteitsfabriek.ovapp.general;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.enums.InputKey;
import com.mobiliteitsfabriek.ovapp.exceptions.ExistingUserException;
import com.mobiliteitsfabriek.ovapp.exceptions.IncorrectPasswordException;
import com.mobiliteitsfabriek.ovapp.exceptions.InvalidPasswordException;
import com.mobiliteitsfabriek.ovapp.exceptions.InvalidUsernameException;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingFieldException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserFoundException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserWithUserNameExistsException;
import com.mobiliteitsfabriek.ovapp.model.User;
import com.mobiliteitsfabriek.ovapp.service.UserService;

public class ValidationFunctions {

    public static boolean checkUsernameMatchesPattern(String username) {
        return username.matches(GlobalConfig.USERNAME_CREATE_PATTERN);
    }

    public static boolean checkPasswordMatchesPattern(String password) {
        return password.matches(GlobalConfig.PASSWORD_CREATE_PATTERN);
    }

    public static User validateLogin(String username, String password) throws MissingFieldException, NoUserWithUserNameExistsException, NoUserFoundException, IncorrectPasswordException {
        if (UtilityFunctions.checkEmpty(username)) {
            throw new MissingFieldException(InputKey.USERNAME);
        }

        if (UtilityFunctions.checkEmpty(password)) {
            throw new MissingFieldException(InputKey.PASSWORD);
        }

        if (!UserService.doesUserExist(username)) {
            throw new NoUserWithUserNameExistsException(username);
        }

        User user = UserService.getUserByUsername(username);

        if (UtilityFunctions.checkEmpty(user)) {
            throw new NoUserFoundException();
        }

        if (!user.checkCorrectPassword(password)) {
            throw new IncorrectPasswordException();
        }

        return user;
    }

    public static void validateAccountCreation(String username, String password) throws ExistingUserException, MissingFieldException, InvalidPasswordException, InvalidUsernameException {
        if (UtilityFunctions.checkEmpty(username)) {
            throw new MissingFieldException(InputKey.USERNAME);
        }

        if (UtilityFunctions.checkEmpty(password)) {
            throw new MissingFieldException(InputKey.PASSWORD);
        }

        if (UserService.doesUserExist(username)) {
            throw new ExistingUserException(username);
        }

        if (GlobalConfig.CHECK_USERNAME_CREATE_PATTERN) {
            if (!ValidationFunctions.checkUsernameMatchesPattern(username)) {
                throw new InvalidUsernameException();
            }
        }        

        if (GlobalConfig.CHECK_PASSWORD_CREATE_PATTERN) {
            if (!ValidationFunctions.checkPasswordMatchesPattern(password)) {
                throw new InvalidPasswordException();
            }
        }
    }

    // Password validations
    public static boolean checkCorrectPassword(String password, String encodedPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(GlobalConfig.BCRYPT_STRENGTH, new SecureRandom());
        return bCryptPasswordEncoder.matches(password, encodedPassword);
    }
}
