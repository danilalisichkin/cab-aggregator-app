package com.example.pricecalculationservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class PostgreSQLContainerConfig {
    private static final PostgreSQLContainer<?> postgresqlContainer;

    static {
        postgresqlContainer = new PostgreSQLContainer<>("postgres:14-alpine")
                .withDatabaseName("price_calculation_database")
                .withUsername("postgres")
                .withPassword("root");
        postgresqlContainer.start();
    }

    @Bean
    public PostgreSQLContainer<?> postgresqlContainer() {
        return postgresqlContainer;
    }

    @DynamicPropertySource
    public static void mongoDBProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }
}
