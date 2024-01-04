package com.example.backend.exceptions;

public class MyEntityNotFoundException extends Exception {
    public MyEntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
