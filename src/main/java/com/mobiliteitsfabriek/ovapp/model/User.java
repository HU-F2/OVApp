package com.mobiliteitsfabriek.ovapp.model;

import com.mobiliteitsfabriek.ovapp.enums.UserType;
import com.mobiliteitsfabriek.ovapp.general.ValidationFunctions;

public class User {
    private String id;
    private String username;
    private String password;

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public boolean checkCorrectPassword(String otherPassword) {
        return ValidationFunctions.checkCorrectPassword(otherPassword, this.password);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return this.password;
    }

    public UserType getUserType() {
        return UserType.USER;
    }
}
