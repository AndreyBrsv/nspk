package com.mycompany.interviewtask.controller.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseError {
    private Integer code;
    private String error;
}
