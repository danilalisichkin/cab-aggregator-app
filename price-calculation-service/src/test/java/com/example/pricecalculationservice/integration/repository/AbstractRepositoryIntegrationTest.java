package com.example.pricecalculationservice.integration.repository;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractRepositoryIntegrationTest {

    static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("price_calculation_database")
            .withUsername("postgres")
            .withPassword("root")
            .withReuse(false);

    static {
        postgresqlContainer.start();
    }

    @DynamicPropertySource
    public static void mongoDBProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }
}