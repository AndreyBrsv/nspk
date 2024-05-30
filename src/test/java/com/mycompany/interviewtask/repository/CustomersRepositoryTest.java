package com.mycompany.interviewtask.repository;

import com.mycompany.interviewtask.repository.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomersRepositoryTest extends TestContainersConfig {

    @Autowired
    private CustomersRepository customersRepository;

    @Test
    public void testFirst() {

    }
}
