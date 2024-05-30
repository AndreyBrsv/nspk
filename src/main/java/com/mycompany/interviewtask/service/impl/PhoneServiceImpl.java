package com.mycompany.interviewtask.service.impl;

import com.mycompany.interviewtask.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PhoneServiceImpl implements PhoneService {

    private static final String TXT_PHONES_FILENAME = "phone_numbers";
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+\\d{1,2}-\\d{3}-\\d{3}-\\d{2}-\\d{2}$");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_hh-mm-ss");

    @Override
    public void saveTxtPhones(List<String> phones) {
        log.debug("Try to save phones to {}", TXT_PHONES_FILENAME);

        var now = LocalDateTime.now(ZoneId.of("GMT+3"));
        var filename = TXT_PHONES_FILENAME + "_" + now.format(DATE_TIME_FORMATTER);

        var sortedValidPhones = phones.stream()
                .filter(this::checkValidPhone)
                .filter(it -> !it.startsWith("+7"))
                .sorted()
                .collect(Collectors.toList());

        if (!sortedValidPhones.isEmpty()) {
            try (PrintWriter writer = new PrintWriter(filename)) {
                sortedValidPhones.forEach(writer::println);
            } catch (Exception e) {
                log.error("Error when save phones: {}", e.getMessage(), e);
            }
        } else {
            log.warn("No valid phones to save");
        }
    }

    private boolean checkValidPhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }
}
