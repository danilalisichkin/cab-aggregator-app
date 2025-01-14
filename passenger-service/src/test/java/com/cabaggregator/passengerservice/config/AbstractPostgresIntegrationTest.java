package com.cabaggregator.passengerservice.config;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class AbstractPostgresIntegrationTest {

    static final PostgreSQLContainer<?> postgresqlContainer
            = new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("test_passenger_database")
            .withUsername("postgres")
            .withPassword("root")
            .withReuse(false);

    static {
        postgresqlContainer.start();
    }

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }
}
