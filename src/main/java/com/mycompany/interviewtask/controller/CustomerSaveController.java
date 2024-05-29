package com.mycompany.interviewtask.controller;

import com.mycompany.interviewtask.controller.model.RunCustomerSaveRequest;
import com.mycompany.interviewtask.service.CustomerService;
import com.mycompany.interviewtask.service.batch.BatchCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
public class CustomerSaveController {

    private final CustomerService customerService;

    @PostMapping("/run")
    public void runCustomerSave(@RequestBody @Valid RunCustomerSaveRequest request) {
        customerService.runCustomerSaveFromFile(request.fileName);
    }
}
