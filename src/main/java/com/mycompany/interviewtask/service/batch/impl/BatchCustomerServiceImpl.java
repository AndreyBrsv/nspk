//package com.mycompany.interviewtask.service.batch.impl;
//
//import com.mycompany.interviewtask.model.CustomerDTO;
//import com.mycompany.interviewtask.repository.CustomersRepository;
//import com.mycompany.interviewtask.repository.model.CustomerDO;
//import com.mycompany.interviewtask.service.batch.BatchCustomerService;
//import com.mycompany.interviewtask.service.batch.JsonReaderResource;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Arrays;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class BatchCustomerServiceImpl implements BatchCustomerService {
//    private final CustomersRepository customersRepository;
//
//    @Override
//    public void runCustomerSaveFromFile(String fileName) {
//        var fullPath = "/Users/andrey.borisov/Documents/abrsv/interview-task-master/src/main/resources/customers.json";
//        try (JsonReaderResource<CustomerDTO> jsonReaderResource = new JsonReaderResource<>(fullPath, CustomerDTO.class)) {
//            var itemReader = jsonReaderResource.getJsonItemReader();
//
//            try {
//                CustomerDTO current = itemReader.read();
//                while (current != null) {
//                    log.info(String.valueOf(current));
//                    current = itemReader.read();
//                }
//
//            } catch (Exception e) {
//                log.error(Arrays.toString(e.getStackTrace()));
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
//
//    private CustomerDO toCustomerDO(CustomerDTO customer) {
//        var now = LocalDateTime.now(ZoneId.of("GMT+3"));
//
//        var customerDO = new CustomerDO();
//        customerDO.setCreatedAt(now);
//        customerDO.setUpdatedAt(now);
//        customerDO.setFirstName(customer.getFirstName());
//        customerDO.setLastName(customer.getLastName());
//        customerDO.setPhoneNumber(customer.getPhoneNumber());
//        customerDO.setRating(customer.rating());
//
//        return customerDO;
//    }
//}
