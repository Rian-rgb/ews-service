package com.ews.service.exception.custom;

public class ConstraintValueException extends RuntimeException{
    public ConstraintValueException(String message) {
        super(message);
    }
}
