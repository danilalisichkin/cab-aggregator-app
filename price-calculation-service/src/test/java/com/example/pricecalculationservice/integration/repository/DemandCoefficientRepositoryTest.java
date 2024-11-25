package com.example.pricecalculationservice.integration.repository;

import com.example.pricecalculationservice.entity.DemandCoefficient;
import com.example.pricecalculationservice.repository.DemandCoefficientRepository;
import com.example.pricecalculationservice.util.DemandCoefficientTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DemandCoefficientRepositoryTest {
    @Autowired
    private DemandCoefficientRepository demandCoefficientRepository;

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("price_calculation_database")
            .withUsername("postgres")
            .withPassword("root");

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnLowCoefficient_WhenCurrentOrdersIsNotHighest() {
        DemandCoefficient lowDemandCoefficient = DemandCoefficientTestUtil.buildLowDemandCoefficient();
        lowDemandCoefficient.setId(null);
        demandCoefficientRepository.save(lowDemandCoefficient);

        DemandCoefficient highDemandCoefficient = DemandCoefficientTestUtil.buildHighDemandCoefficient();
        highDemandCoefficient.setId(null);
        demandCoefficientRepository.save(highDemandCoefficient);

        final Integer currentOrders = 15;

        Optional<DemandCoefficient> result = demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(result)
                .isNotEmpty()
                .contains(lowDemandCoefficient);
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnHighCoefficient_WhenCurrentOrdersIsHighest() {
        DemandCoefficient lowDemandCoefficient = DemandCoefficientTestUtil.buildLowDemandCoefficient();
        lowDemandCoefficient.setId(null);
        demandCoefficientRepository.save(lowDemandCoefficient);

        DemandCoefficient highDemandCoefficient = DemandCoefficientTestUtil.buildHighDemandCoefficient();
        highDemandCoefficient.setId(null);
        demandCoefficientRepository.save(highDemandCoefficient);

        final Integer currentOrders = 25;

        Optional<DemandCoefficient> result = demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(result)
                .isNotEmpty()
                .contains(highDemandCoefficient);
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnEmptyOptional_WhenCoefficientDoesNotExists() {
        DemandCoefficient lowDemandCoefficient = DemandCoefficientTestUtil.buildLowDemandCoefficient();
        lowDemandCoefficient.setId(null);
        demandCoefficientRepository.save(lowDemandCoefficient);

        final Integer currentOrders = 1;

        Optional<DemandCoefficient> result = demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(result).isEmpty();
    }
}
