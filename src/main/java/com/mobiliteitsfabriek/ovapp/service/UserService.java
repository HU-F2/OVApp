package com.mobiliteitsfabriek.ovapp.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.NoNullArgumentAllowed;
import com.mobiliteitsfabriek.ovapp.exceptions.NoUserWithUserNameExistsException;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.User;

public class UserService {
    private static final ObjectMapper objectMapper;
    private static final File databaseFile;

    static {
        objectMapper = GeneralService.getObjectMapper();
        databaseFile = new File(GlobalConfig.FILE_PATH_USERS);
    }

    public static ArrayList<User> loadUsers() {
        try {
            if (!databaseFile.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<User>(Arrays.asList(objectMapper.readValue(databaseFile, User[].class)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read user database.", e);
        }
    }

    public static void saveUsers(ArrayList<User> users) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(databaseFile, users);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not save user database.", e);
        }
    }

    public static void addUser(User user) {
        if (UtilityFunctions.checkEmpty(user)) {
            throw new NoNullArgumentAllowed();
        }
        ArrayList<User> users = loadUsers();
        users.add(user);
        saveUsers(users);
    }

    public static boolean doesUserExist(String username) {
        return loadUsers().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public static User getUserByUsername(String username) throws NoUserWithUserNameExistsException {
        return loadUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new NoUserWithUserNameExistsException(username));
    }

    public static void deleteUser(String username) {
        ArrayList<User> users = loadUsers();
        users.removeIf(user -> user.getUsername().equals(username));
        saveUsers(users);
    }

    public static void updateUser(User user) {
        deleteUser(user.getUsername());
        addUser(user);
    }
}
