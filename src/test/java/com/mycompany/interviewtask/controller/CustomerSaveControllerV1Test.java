package com.mycompany.interviewtask.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.interviewtask.controller.model.common.Response;
import com.mycompany.interviewtask.controller.model.common.ResponseError;
import com.mycompany.interviewtask.exception.BizException;
import com.mycompany.interviewtask.exception.ErrorCodes;
import com.mycompany.interviewtask.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

@WebMvcTest(CustomerSaveControllerV1.class)
public class CustomerSaveControllerV1Test {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return true cause no exception")
    public void shouldReturnTrue() throws Exception {
        var fileName = "test_filename";
        var jsonString = "{\n" +
                "    \"file_name\" : \"" + fileName + "\"\n" +
                "}";

        doNothing().when(customerService).runCustomerSaveFromFile(Mockito.eq(fileName));
        var mvcResult = mvc.perform(
                        MockMvcRequestBuilders.post("/customer/v1/run")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(jsonString)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var expectedResult = new Response<>(true, null);
        var result = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<Response<Boolean>>() {
                }
        );

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(customerService).runCustomerSaveFromFile(Mockito.eq(fileName));
    }

    @Test
    @DisplayName("Should throw bizException")
    public void shouldReturnException() throws Exception {
        var fileName = "test_filename";
        var jsonString = "{\n" +
                "    \"file_name\" : \"" + fileName + "\"\n" +
                "}";

        Mockito.doThrow(new BizException(ErrorCodes.EMPTY_CUSTOMERS, "Some exception")).when(customerService).runCustomerSaveFromFile(Mockito.eq(fileName));
        var mvcResult = mvc.perform(
                        MockMvcRequestBuilders.post("/customer/v1/run")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(jsonString)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        var expectedResult = new Response<>(null, new ResponseError(10, "Some exception"));
        var result = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<Response<Boolean>>() {
                }
        );

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(customerService).runCustomerSaveFromFile(Mockito.eq(fileName));
    }

    @Test
    @DisplayName("Should return bad request")
    public void shouldReturnBadRequest() throws Exception {
        var jsonString = "{\"\"\"}\"}";
        mvc.perform(
                        MockMvcRequestBuilders.post("/customer/v1/run")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(jsonString)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(customerService, times(0)).runCustomerSaveFromFile(Mockito.any());
    }

    @Test
    @DisplayName("Should return bad request cause filename is null")
    public void shouldReturnBadRequestCauseFilenameIsNull() throws Exception {
        var jsonString = "{}";

        var mvcResult = mvc.perform(
                        MockMvcRequestBuilders.post("/customer/v1/run")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(jsonString)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        var expectedResult = new Response<>(null, new ResponseError(400, "Not valid fields at request: [fileName]"));
        var result = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<Response<Boolean>>() {
                }
        );

        Assertions.assertThat(result).isEqualTo(expectedResult);
        Mockito.verify(customerService, times(0)).runCustomerSaveFromFile(Mockito.any());
    }
}
