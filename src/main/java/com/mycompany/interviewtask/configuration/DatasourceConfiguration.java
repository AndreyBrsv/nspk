package com.mycompany.interviewtask.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        String databaseName = "postgres";
        config.setUsername("postgres");
        config.setPassword("password");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/" + databaseName + "?prepareThreshold=0");
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaxLifetime(600_000);
        config.setMaximumPoolSize(20);

        return new HikariDataSource(config);
    }
}
