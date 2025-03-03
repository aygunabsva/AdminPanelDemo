package com.example.adminpaneldemo.exception;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String ex) {
        super(ex);
    }
}