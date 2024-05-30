package com.mycompany.interviewtask.service.impl;

import com.mycompany.interviewtask.exception.BizException;
import com.mycompany.interviewtask.exception.CustomInternalException;
import com.mycompany.interviewtask.model.CustomerDTO;
import com.mycompany.interviewtask.model.PrivilegeStatus;
import com.mycompany.interviewtask.repository.CustomersRepository;
import com.mycompany.interviewtask.repository.model.CustomerDO;
import com.mycompany.interviewtask.service.CustomerFileReader;
import com.mycompany.interviewtask.service.PhoneService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest(classes = CustomerServiceImpl.class)
@ExtendWith(SpringExtension.class)
public class CustomerServiceImplTest {

    @MockBean
    private CustomersRepository customersRepository;

    @MockBean
    private CustomerFileReader customerFileReader;

    @MockBean
    private PhoneService phoneService;

    @Captor
    private ArgumentCaptor<List<CustomerDO>> saveCustomers;

    @Autowired
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("Should all be good")
    public void saveCustomers() {
        var filename = "testFile";
        var customers = List.of(
                new CustomerDTO(
                        "Andrey",
                        "Popov",
                        PrivilegeStatus.SILVER,
                        100,
                        10,
                        "+7-925-111-22-33"
                )
        );

        var expectedPhones = List.of("+7-925-111-22-33");

        Mockito.doReturn(customers).when(customerFileReader).readFile(Mockito.eq(filename));
        Mockito.doNothing().when(phoneService).saveTxtPhones(Mockito.eq(expectedPhones));
        Mockito.doReturn(null).when(customersRepository).saveAll(Mockito.any());

        customerService.runCustomerSaveFromFile(filename);

        Mockito.verify(phoneService).saveTxtPhones(Mockito.eq(expectedPhones));
        Mockito.verify(customerFileReader).readFile(Mockito.eq(filename));
        Mockito.verify(customersRepository).saveAll(saveCustomers.capture());

        var savedCustomers = saveCustomers.getValue();
        Assertions.assertThat(savedCustomers.size()).isEqualTo(1);
        Assertions.assertThat(savedCustomers.get(0).getRating()).isEqualTo(100);
    }

    @Test
    @DisplayName("Should not save customers")
    public void emptyCustomers() {
        var filename = "testFile";
        List<CustomerDTO> customers = List.of();

        List<String> expectedPhones = List.of();

        Mockito.doReturn(customers).when(customerFileReader).readFile(Mockito.eq(filename));
        Mockito.doNothing().when(phoneService).saveTxtPhones(Mockito.eq(expectedPhones));

        Assertions.assertThatThrownBy(() -> customerService.runCustomerSaveFromFile(filename))
                .hasMessage("No one customers to save")
                .isInstanceOf(BizException.class);

        Mockito.verify(phoneService).saveTxtPhones(Mockito.eq(expectedPhones));
        Mockito.verify(customerFileReader).readFile(Mockito.eq(filename));
        Mockito.verify(customersRepository, Mockito.times(0)).saveAll(Mockito.any());
    }

    @Test
    @DisplayName("Should return true cause no exception")
    public void test() {
        var filename = "testFile";
        var customers = List.of(
                new CustomerDTO(
                        "Andrey",
                        "Popov",
                        PrivilegeStatus.SILVER,
                        100,
                        10,
                        "+7-925-111-22-33"
                )
        );

        var expectedPhones = List.of("+7-925-111-22-33");

        Mockito.doReturn(customers).when(customerFileReader).readFile(Mockito.eq(filename));
        Mockito.doNothing().when(phoneService).saveTxtPhones(Mockito.eq(expectedPhones));
        Mockito.doThrow(new RuntimeException("Some exception when save")).when(customersRepository).saveAll(Mockito.any());

        Assertions.assertThatThrownBy(() -> customerService.runCustomerSaveFromFile(filename))
                .hasMessage("Exception when save customers to database")
                .isInstanceOf(CustomInternalException.class);

        Mockito.verify(phoneService).saveTxtPhones(Mockito.eq(expectedPhones));
        Mockito.verify(customerFileReader).readFile(Mockito.eq(filename));
        Mockito.verify(customersRepository).saveAll(saveCustomers.capture());

        var savedCustomers = saveCustomers.getValue();
        Assertions.assertThat(savedCustomers.size()).isEqualTo(1);
        Assertions.assertThat(savedCustomers.get(0).getRating()).isEqualTo(100);
    }
}
