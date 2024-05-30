package com.mycompany.interviewtask.controller;

import com.mycompany.interviewtask.controller.model.common.Response;
import com.mycompany.interviewtask.controller.model.common.ResponseError;
import com.mycompany.interviewtask.exception.BizException;
import com.mycompany.interviewtask.exception.CustomInternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResponseExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<Object> handleValidation(
            MethodArgumentNotValidException ex) {
        return new Response<>(
                null,
                new ResponseError(400, "Not valid fields at request: " + getNotValidFields(ex))
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<Object> handleRequest(
            HttpMessageNotReadableException ex) {
        return new Response<>(
                null,
                new ResponseError(400, "Not valid request: " + ex.getMessage())
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizException.class)
    public Response<Object> handleBizException(
            BizException ex) {
        return new Response<>(
                null,
                new ResponseError(ex.getCode().getErrorCode(), ex.getMessage())
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomInternalException.class)
    public Response<Object> handleInternalException(
            CustomInternalException ex) {
        return new Response<>(
                null,
                new ResponseError(500, ex.getMessage())
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Response<Object> handleAnyException(
            Exception ex) {
        return new Response<>(
                null,
                new ResponseError(500, "Something went wrong..")
        );
    }


    private List<String> getNotValidFields(MethodArgumentNotValidException ex) {
        return ex.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList());
    }
}
