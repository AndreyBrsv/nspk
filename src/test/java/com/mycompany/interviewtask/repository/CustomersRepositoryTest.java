package com.mycompany.interviewtask.repository;

import com.mycompany.interviewtask.repository.config.TestContainersConfig;
import com.mycompany.interviewtask.repository.model.CustomerDO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class CustomersRepositoryTest extends TestContainersConfig {

    @Autowired
    private CustomersRepository customersRepository;

    @Test
    @DisplayName("Should return empty customers")
    public void findEmptyTable() {
        var customers = customersRepository.findAll();
        Assertions.assertThat(customers).isEmpty();
    }

    @Test
    @DisplayName("Should save customers")
    public void saveCustomers() {
        LocalDateTime now = LocalDateTime.now();
        var customer = new CustomerDO();
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);
        customer.setFirstName("Andrey");
        customer.setLastName("Popov");
        customer.setRating(100);
        customer.setPhoneNumber("+7-985-581-22-33");

        List<CustomerDO> customersList = List.of(customer);

        customersRepository.saveAll(customersList);
        List<CustomerDO> findCustomers = (List<CustomerDO>) customersRepository.findAll();

        customer.setId(1L);
        Assertions.assertThat(findCustomers.size()).isEqualTo(1);
        Assertions.assertThat(findCustomers.get(0)).isEqualTo(customer);
    }
}
