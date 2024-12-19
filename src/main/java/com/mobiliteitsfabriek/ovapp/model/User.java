package com.mobiliteitsfabriek.ovapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.mobiliteitsfabriek.ovapp.enums.UserType;
import com.mobiliteitsfabriek.ovapp.general.ValidationFunctions;

@JsonRootName(value = "user")
public class User {
    private String id;
    private String username;
    private String password;

    @JsonCreator
    public User(@JsonProperty("id") String id, @JsonProperty("username") String username, @JsonProperty("password") String password) {
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

    @JsonIgnore
    public UserType getUserType() {
        return UserType.USER;
    }

    @JsonProperty("userType")
    public UserType serializeUserType() {
        return getUserType();
    }
}
