package com.example.backend.exceptions;

public class MyIllegalArgumentException extends Exception {
    public MyIllegalArgumentException(String errorMessage) {
        super(errorMessage);
    }
}
