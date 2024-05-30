package com.mycompany.interviewtask.controller.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class RestResponse<T> {
    private T data;
    private ResponseError error;
}
