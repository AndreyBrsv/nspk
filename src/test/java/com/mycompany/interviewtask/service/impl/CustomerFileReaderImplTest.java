package com.mycompany.interviewtask.service.impl;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.interviewtask.exception.BizException;
import com.mycompany.interviewtask.model.CustomerDTO;
import com.mycompany.interviewtask.model.PrivilegeStatus;
import com.mycompany.interviewtask.service.CustomerFileReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@JsonTest
@ExtendWith(SpringExtension.class)
@Import(CustomerFileReaderImpl.class)
public class CustomerFileReaderImplTest {

    @Autowired
    private CustomerFileReader customerFileReader;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
    }

    @Test
    @DisplayName("Should read file correctly")
    public void testReadFile() {
        var customers = customerFileReader.readFile("src/test/resources/customers_test");

        var expectedCustomers = List.of(
                new CustomerDTO(
                        "Ivan",
                        "Ivanov",
                        PrivilegeStatus.SILVER,
                        100,
                        9,
                        "+7-999-888-77-66")
        );

        Assertions.assertThat(customers).isEqualTo(expectedCustomers);
    }

    @Test
    @DisplayName("Should return emptyList cause file not found")
    public void testFileNotFound() {
        Assertions.assertThatThrownBy(() -> customerFileReader.readFile("test_file"))
                .hasMessage("File test_file not found")
                .isInstanceOf(BizException.class);
    }

    @Test
    @DisplayName("Should return emptyList cause bad json at file")
    public void testInvalidJsonFile() {
        Assertions.assertThatThrownBy(() -> customerFileReader.readFile("src/test/resources/customers_test_bad"))
                .hasMessage("File src/test/resources/customers_test_bad has invalid json format")
                .isInstanceOf(BizException.class);
    }
}
