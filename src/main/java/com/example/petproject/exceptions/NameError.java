package com.example.petproject.exceptions;

import lombok.Getter;

public enum NameError {
    ERROR_IS_EXIST("User exist");

    @Getter
    private String errorText;

    NameError(String errorText) {
        this.errorText = errorText;
    }


}
