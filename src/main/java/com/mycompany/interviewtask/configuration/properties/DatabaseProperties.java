package com.mycompany.interviewtask.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("database")
@Data
public class DatabaseProperties {
    private String username;
    private String password;
    private String url;
}
