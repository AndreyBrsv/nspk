package com.mycompany.interviewtask.exception;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    EMPTY_CUSTOMERS(10),
    FILE_NOT_FOUND(20),
    FILE_INVALID_FORMAT(21);

    private final int errorCode;

    ErrorCodes(int errorCode) {
        this.errorCode = errorCode;
    }
}
