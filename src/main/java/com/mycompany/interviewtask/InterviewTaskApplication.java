package com.mycompany.interviewtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class InterviewTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewTaskApplication.class, args);
    }

}
