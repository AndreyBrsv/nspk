package com.mycompany.interviewtask.service.impl;

import com.mycompany.interviewtask.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {

    private static final String TXT_PHONES_FILENAME = "phone_numbers";

    @Override
    public void saveTxtPhones(List<String> phones) {
        log.debug("Try to save phones to {}", TXT_PHONES_FILENAME);

        var sortedPhones = phones.stream().filter(it -> !it.startsWith("+7")).sorted().collect(Collectors.toList());
        if (!sortedPhones.isEmpty()) {
            try (PrintWriter writer = new PrintWriter(TXT_PHONES_FILENAME)) {
                sortedPhones.forEach(writer::println);
            } catch (Exception e) {
                log.error("Error when save phones: {}", e.getMessage());
            }
        }
    }
}
