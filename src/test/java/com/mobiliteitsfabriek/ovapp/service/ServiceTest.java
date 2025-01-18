package com.mobiliteitsfabriek.ovapp.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Random;

import org.junit.jupiter.api.Test;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.ExistingUserException;
import com.mobiliteitsfabriek.ovapp.exceptions.IncorrectPasswordException;
import com.mobiliteitsfabriek.ovapp.exceptions.InvalidPasswordException;
import com.mobiliteitsfabriek.ovapp.exceptions.MissingFieldException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserFoundException;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserWithUserNameExistsException;
import com.mobiliteitsfabriek.ovapp.model.UserManagement;

public class ServiceTest {

    private static void executeUserWorkflow(Runnable testLogic) {
        String username = GlobalConfig.TEST_USERNAME;
        String password = GlobalConfig.TEST_PASSWORD;
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

    @Test
    void testUserCheckExistsCreateDelete() throws MissingFieldException, ExistingUserException, InvalidPasswordException {
        ServiceTest.executeUserWorkflow(() -> {
        });
    }

    @Test
    void testLoginCorrect() throws MissingFieldException, ExistingUserException, InvalidPasswordException {
        ServiceTest.executeUserWorkflow(() -> {
            try {
                String username = GlobalConfig.TEST_USERNAME;
                String password = GlobalConfig.TEST_PASSWORD;
                assertTrue(UserManagement.loginUser(username, password), "User should be able to log in with correct credentials");
            } catch (MissingFieldException | NoUserWithUserNameExistsException | NoUserFoundException | IncorrectPasswordException e) {
                fail(e.getMessage());
            } catch (Exception e) {
                fail("Unexpected Exception, see stackTrace");
                e.printStackTrace();
            }
        });
    }

    @Test
    void testLoginInCorrectPassword() throws MissingFieldException, ExistingUserException, InvalidPasswordException {
        ServiceTest.executeUserWorkflow(() -> {
            try {
                String username = GlobalConfig.TEST_USERNAME;
                String password = "inCorrectPassword";
                assertFalse(UserManagement.loginUser(username, password), "User should not be able to log in with incorrect credentials");
            } catch (IncorrectPasswordException e) {
                assertTrue(e != null, "User should not be able to log in with incorrect credentials");
            } catch (MissingFieldException | NoUserWithUserNameExistsException | NoUserFoundException e) {
                fail(e.getMessage());
            } catch (Exception e) {
                fail("Unexpected Exception, see stackTrace");
                e.printStackTrace();
            }
        });
    }

    @Test
    void testLoginInCorrectUsername() throws MissingFieldException, ExistingUserException, InvalidPasswordException {
        ServiceTest.executeUserWorkflow(() -> {
            try {
                String username = findNonExistentUsername();
                String password = "inCorrectPassword";
                assertFalse(UserManagement.loginUser(username, password), "User should not be able to log in with incorrect credentials");
            } catch (NoUserWithUserNameExistsException e) {
                assertTrue(e != null, "User should not be able to log in with incorrect credentials");
            } catch (MissingFieldException | IncorrectPasswordException | NoUserFoundException | AssertionError e) {
                fail(e.getMessage());
            } catch (Exception e) {
                fail("Unexpected Exception, see stackTrace");
                e.printStackTrace();
            }
        });
    }

    @Test
    void testLoginEmptyInput() throws MissingFieldException, ExistingUserException, InvalidPasswordException {
        ServiceTest.executeUserWorkflow(() -> {
            try {
                String username = "";
                String password = "";
                assertFalse(UserManagement.loginUser(username, password), "User should not be able to log in with incorrect credentials");
            } catch (MissingFieldException e) {
                assertTrue(e != null, "User should not be able to log in with incorrect credentials");
            } catch (NoUserWithUserNameExistsException | NoUserFoundException | IncorrectPasswordException e) {
                fail(e.getMessage());
            } catch (Exception e) {
                fail("Unexpected Exception, see stackTrace");
                e.printStackTrace();
            }
        });
    }

    // Helper test functions
    private static String findNonExistentUsername() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            long randomNumber = Math.abs(random.nextLong());
            String username = "inCorrectUsername-" + randomNumber;

            if (!UserService.doesUserExist(username)) {
                return username;
            }
        }

        throw new AssertionError("Failed to find a non-existent username after 10 attempts.");
    }
}
