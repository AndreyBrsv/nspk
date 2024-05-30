package com.mycompany.interviewtask.controller;

import com.mycompany.interviewtask.controller.model.RunCustomerSaveRequest;
import com.mycompany.interviewtask.controller.model.common.Response;
import com.mycompany.interviewtask.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer/v1")
@RequiredArgsConstructor
public class CustomerSaveControllerV1 {

    private final CustomerService customerService;

    @PostMapping("/run")
    public Response<Boolean> runCustomerSave(@RequestBody @Valid RunCustomerSaveRequest request) {
        customerService.runCustomerSaveFromFile(request.fileName);
        return new Response<>(true, null);
    }
}
