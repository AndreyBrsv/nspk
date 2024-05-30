package com.mycompany.interviewtask.repository.config;


import com.mycompany.interviewtask.InterviewTaskApplication;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.integration.spring.SpringResourceAccessor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.DriverManager;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = InterviewTaskApplication.class)
public class TestContainersConfig {

    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:13.3")
            .withDatabaseName("integration-tests")
            .withUsername("test")
            .withPassword("test");

    @BeforeAll
    public void setUp() throws Exception {
        String jdbcUrl = postgresqlContainer.getJdbcUrl();
        String username = postgresqlContainer.getUsername();
        String password = postgresqlContainer.getPassword();

        var connection = DriverManager.getConnection(jdbcUrl, username, password);
        var database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        var liquibase = new Liquibase(
                "src/main/resources/db.changelog/changelog-master.xml",
                new SpringResourceAccessor(new DefaultResourceLoader()),
                database
        );

        liquibase.update(new Contexts(), new LabelExpression());
    }

    @Test
    public void testFirst() {

    }
}

