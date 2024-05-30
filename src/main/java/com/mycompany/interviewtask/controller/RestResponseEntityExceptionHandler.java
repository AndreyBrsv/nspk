package com.mycompany.interviewtask.controller;

import com.mycompany.interviewtask.controller.model.common.ResponseError;
import com.mycompany.interviewtask.controller.model.common.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse<Object> handleValidation(
            MethodArgumentNotValidException ex) {
        return new RestResponse<>(
                null,
                new ResponseError(400, "Not valid fields at request: " + getNotValidFields(ex))
        );
    }

    private List<String> getNotValidFields(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList());
    }
}
