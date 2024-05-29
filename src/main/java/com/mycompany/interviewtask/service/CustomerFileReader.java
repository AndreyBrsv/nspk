package com.mycompany.interviewtask.service;

import com.mycompany.interviewtask.model.CustomerDTO;

import java.util.List;

public interface CustomerFileReader {

    List<CustomerDTO> readFile(String fileName);
}
