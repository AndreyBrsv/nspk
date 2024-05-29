package com.mycompany.interviewtask.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.interviewtask.model.CustomerDTO;
import com.mycompany.interviewtask.service.CustomerFileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerFileReaderImpl implements CustomerFileReader {

    private final ObjectMapper objectMapper;
    private final String PRE_PATH_FILE = "src/main/resources/";

    @Override
    public List<CustomerDTO> readFile(String fileName) {
        try {
            return objectMapper.readValue(
                    new File(PRE_PATH_FILE + fileName + ".json"), new TypeReference<>() {
                    }
            );
        } catch (Throwable e) {
            log.error("Exception when try read file: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}
