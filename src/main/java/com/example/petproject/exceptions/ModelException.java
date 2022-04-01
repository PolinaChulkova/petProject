package com.example.petproject.exceptions;

import lombok.Getter;

public class ModelException extends Exception {
    @Getter
    private NameError nameError;

    public ModelException(NameError nameError) {
        this.nameError = nameError;
    }

    public ModelException(String message, NameError nameError) {
        super(message);
        this.nameError = nameError;
    }

    public ModelException(String message, Throwable cause, NameError nameError) {
        super(message, cause);
        this.nameError = nameError;
    }
}
