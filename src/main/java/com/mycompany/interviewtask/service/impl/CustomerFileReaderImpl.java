package com.mycompany.interviewtask.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.mycompany.interviewtask.exception.BizException;
import com.mycompany.interviewtask.model.CustomerDTO;
import com.mycompany.interviewtask.service.CustomerFileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

import static com.mycompany.interviewtask.exception.ErrorCodes.FILE_INVALID_FORMAT;
import static com.mycompany.interviewtask.exception.ErrorCodes.FILE_NOT_FOUND;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerFileReaderImpl implements CustomerFileReader {

    private final ObjectMapper objectMapper;

    @Override
    public List<CustomerDTO> readFile(String fileName) {
        try {
            return objectMapper.readValue(
                    new File(fileName + ".json"), new TypeReference<>() {
                    }
            );
        } catch (MismatchedInputException e) {
            logError(e);
            throw new BizException(FILE_INVALID_FORMAT, "File " + fileName + " has invalid json format");
        } catch (Throwable e) {
            logError(e);
            throw new BizException(FILE_NOT_FOUND, "File " + fileName + " not found");
        }
    }

    private void logError(Throwable e) {
        log.error("Exception when try read file: {}", e.getMessage(), e);
    }
}
