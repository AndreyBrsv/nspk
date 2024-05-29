package com.mycompany.interviewtask.configuration;

import com.mycompany.interviewtask.configuration.properties.DatabaseProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DatabaseProperties.class)
@RequiredArgsConstructor
public class DatasourceConfiguration {

    private final DatabaseProperties databaseProperties;

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setUsername(databaseProperties.getUsername());
        config.setPassword(databaseProperties.getPassword());
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/" + databaseProperties.getDatabase() + "?prepareThreshold=0");
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaxLifetime(600_000);
        config.setMaximumPoolSize(20);

        return new HikariDataSource(config);
    }
}
