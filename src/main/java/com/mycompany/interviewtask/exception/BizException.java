package com.mycompany.interviewtask.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
    private final ErrorCodes code;

    public BizException(ErrorCodes code, String message) {
        super(message);
        this.code = code;
    }
}
