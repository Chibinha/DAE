package com.example.backend.exceptions;

public class MyEntityNotFoundException extends Exception {
    public MyEntityNotFoundException(String message) {
        super(message);
    }
}