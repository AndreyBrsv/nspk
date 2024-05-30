package com.mycompany.interviewtask.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = PhoneServiceImpl.class)
@ExtendWith(SpringExtension.class)
public class PhoneServiceImplTest {

    @Autowired
    private PhoneServiceImpl phoneService;

    private static final String TEST_FILENAME_PREFIX = "phone_numbers";

    @BeforeEach
    public void deleteFilesBefore() throws IOException {
        deleteTestFiles();
    }

    @AfterEach
    public void deleteFilesAfter() throws IOException {
        deleteTestFiles();
    }

    @Test
    @DisplayName("Should save only 1 phone")
    public void saveTxtPhones() throws IOException {
        List<String> phones = List.of(
                "+7-903-125-52-24",
                "+2-153-153-52-42",
                "+7-9222-41-5-145"
        );

        phoneService.saveTxtPhones(phones);
        List<String> expectedPhones = List.of("+2-153-153-52-42");
        List<String> actualPhones = readPhonesFromTestFile();
        Assertions.assertThat(actualPhones).isEqualTo(expectedPhones);
    }

    @Test
    @DisplayName("Should not save cause all phones not valid")
    public void invalidPhones() throws IOException {
        List<String> phones = List.of(
                "123-456-78-90",
                "+7-123-78-90",
                "invalid-phone"
        );

        phoneService.saveTxtPhones(phones);
        List<String> actualPhones = readPhonesFromTestFile();
        Assertions.assertThat(actualPhones).isEmpty();
    }

    @Test
    @DisplayName("Should not save")
    public void emptyPhones() throws IOException {
        List<String> phones = List.of();

        phoneService.saveTxtPhones(phones);
        List<String> actualPhones = readPhonesFromTestFile();
        Assertions.assertThat(actualPhones).isEmpty();
    }

    private List<String> readPhonesFromTestFile() throws IOException {
        Path testFilePath = Files.list(Paths.get("."))
                .filter(path -> path.getFileName().toString().startsWith(TEST_FILENAME_PREFIX))
                .findFirst()
                .orElse(null);

        if (testFilePath == null) {
            return List.of();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath.toFile()))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    private void deleteTestFiles() throws IOException {
        Files.list(Paths.get("."))
                .filter(path -> path.getFileName().toString().startsWith(TEST_FILENAME_PREFIX))
                .forEach(path -> path.toFile().delete());
    }
}