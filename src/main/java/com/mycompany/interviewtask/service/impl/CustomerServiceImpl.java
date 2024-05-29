package com.mycompany.interviewtask.service.impl;

import com.mycompany.interviewtask.model.CustomerDTO;
import com.mycompany.interviewtask.repository.CustomersRepository;
import com.mycompany.interviewtask.repository.model.CustomerDO;
import com.mycompany.interviewtask.service.CustomerFileReader;
import com.mycompany.interviewtask.service.CustomerService;
import com.mycompany.interviewtask.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomersRepository customersRepository;
    private final CustomerFileReader customerFileReader;
    private final PhoneService phoneService;

    @Override
    public void runCustomerSaveFromFile(String fileName) {
        log.debug("Try to read customers from file: {}", fileName);
        var customersFromFile = customerFileReader.readFile(fileName);

        var customersToSave = customersFromFile.stream().map(this::toCustomerDO).collect(Collectors.toList());
        var phones = customersToSave.stream().map(CustomerDO::getPhoneNumber).collect(Collectors.toList());
        phoneService.saveTxtPhones(phones);

        if (!customersToSave.isEmpty()) {
            try {
                customersRepository.saveAll(customersToSave);
            } catch (Exception e) {
                log.error("Exception when save customers to database: {}", e.getMessage());
            }
        } else {
            log.warn("No one customers to save");
        }
    }

    private CustomerDO toCustomerDO(CustomerDTO customer) {
        var now = LocalDateTime.now(ZoneId.of("GMT+3"));

        var customerDO = new CustomerDO();
        customerDO.setCreatedAt(now);
        customerDO.setUpdatedAt(now);
        customerDO.setFirstName(customer.getFirstName());
        customerDO.setLastName(customer.getLastName());
        customerDO.setPhoneNumber(customer.getPhoneNumber());
        customerDO.setRating(customer.rating());

        return customerDO;
    }

}
