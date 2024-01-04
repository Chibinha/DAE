package com.example.backend.exceptions;

public class MyEntityExistsException extends Exception {
    public MyEntityExistsException(String errorMessage) {
        super(errorMessage);
    }
}
