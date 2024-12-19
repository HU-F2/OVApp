package com.mobiliteitsfabriek.ovapp.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.ExistingUserException;
import com.mobiliteitsfabriek.ovapp.exceptions.IncorrectPasswordException;
import com.mobiliteitsfabriek.ovapp.exceptions.InvalidPasswordException;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingFieldException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserFoundException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserWithUserNameExistsException;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.UserManagement;

public class ServiceTest {

    private static void executeUserWorkflow(Runnable testLogic) {
        String username = null;
        String password = null;
        try {
            username = UtilityFunctions.generateUsername(GlobalConfig.TEST_USERNAME_PREFIX);
            password = UtilityFunctions.generatePassword();

        } catch (Exception e) {
            fail(e.getMessage());
            return;
        }
        try {
            UserManagement.createUser(username, password);
            assertTrue(UserService.doesUserExist(username), "User should exist after creation");

            // Voer de specifieke testlogica uit
            testLogic.run();

        } catch (Exception e) {
            fail(e.getMessage());
        } finally { // Always try to delete the user regardless of the result
            try {
                if (UserService.doesUserExist(username)) {
                    UserService.deleteUser(username);
                }
                assertFalse(UserService.doesUserExist(username), "User should not exist after deletion");
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }
    }

    private static void executeUserWorkflow(String username, String password, Runnable testLogic) {
        try {
            UserManagement.createUser(username, password);
            assertTrue(UserService.doesUserExist(username), "User should exist after creation");

            // Voer de specifieke testlogica uit
            testLogic.run();

        } catch (Exception e) {
            fail(e.getMessage());
        } finally { // Always try to delete the user regardless of the result
            try {
                if (UserService.doesUserExist(username)) {
                    UserService.deleteUser(username);
                }
                assertFalse(UserService.doesUserExist(username), "User should not exist after deletion");
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }
    }

    private static String[] generateCredentials() {
        String username = null;
        String password = null;
        try {
            username = UtilityFunctions.generateUsername(GlobalConfig.TEST_USERNAME_PREFIX);
            password = UtilityFunctions.generatePassword();

        } catch (Exception e) {
            fail(e.getMessage());
        }
        String ar[] = new String[2];
        ar[0] = username;
        ar[1] = password;
        return ar;
    }

    @Test
    void testUserCheckExistsCreateDelete() throws MissingFieldException, ExistingUserException, InvalidPasswordException {
        ServiceTest.executeUserWorkflow(() -> {
        });
    }

    @Test
    void testLogin() throws MissingFieldException, ExistingUserException, InvalidPasswordException {
        String ar[] = ServiceTest.generateCredentials();
        String username = ar[0];
        String password = ar[1];
        ServiceTest.executeUserWorkflow(username, password, () -> {
            try {
                assertTrue(UserManagement.loginUser(username, password), "User should be able to log in with correct credentials");
            } catch (MissingFieldException | NoUserWithUserNameExistsException | NoUserFoundException | IncorrectPasswordException e) {
                fail(e.getMessage());
            }
        });
    }
}
