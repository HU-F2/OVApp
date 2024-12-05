package com.mobiliteitsfabriek.ovapp.exceptions;

public class ApiRequestException extends RuntimeException {
    public ApiRequestException(String message) {
        super(message);
    }
}
