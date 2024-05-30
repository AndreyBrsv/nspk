package com.mycompany.interviewtask.controller.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    private T data;
    private ResponseError error;
}
