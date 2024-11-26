package com.example.pricecalculationservice.integration.repository;

import com.example.pricecalculationservice.config.PostgreSQLContainerConfig;
import com.example.pricecalculationservice.entity.DemandCoefficient;
import com.example.pricecalculationservice.repository.DemandCoefficientRepository;
import com.example.pricecalculationservice.util.DemandCoefficientTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Testcontainers
@ContextConfiguration(classes = PostgreSQLContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DemandCoefficientRepositoryTest {
    @Autowired
    private DemandCoefficientRepository demandCoefficientRepository;

    @Test
    void findHighestMatchingCoefficient_ShouldReturnLowCoefficient_WhenCurrentOrdersIsNotHighest() {
        DemandCoefficient lowDemandCoefficient = DemandCoefficientTestUtil.getLowDemandCoefficientBuilder()
                .id(null)
                .build();
        demandCoefficientRepository.save(lowDemandCoefficient);

        DemandCoefficient highDemandCoefficient = DemandCoefficientTestUtil.getHighDemandCoefficientBuilder()
                .id(null)
                .build();
        demandCoefficientRepository.save(highDemandCoefficient);

        final Integer currentOrders = 15;

        Optional<DemandCoefficient> actual =
                demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(actual)
                .isNotEmpty()
                .contains(lowDemandCoefficient);
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnHighCoefficient_WhenCurrentOrdersIsHighest() {
        DemandCoefficient lowDemandCoefficient = DemandCoefficientTestUtil.getLowDemandCoefficientBuilder()
                .id(null)
                .build();
        demandCoefficientRepository.save(lowDemandCoefficient);

        DemandCoefficient highDemandCoefficient = DemandCoefficientTestUtil.getHighDemandCoefficientBuilder()
                .id(null)
                .build();
        demandCoefficientRepository.save(highDemandCoefficient);

        final Integer currentOrders = 25;

        Optional<DemandCoefficient> actual =
                demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(actual)
                .isNotEmpty()
                .contains(highDemandCoefficient);
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnEmptyOptional_WhenCoefficientDoesNotExists() {
        DemandCoefficient lowDemandCoefficient = DemandCoefficientTestUtil.getLowDemandCoefficientBuilder()
                .id(null)
                .build();
        demandCoefficientRepository.save(lowDemandCoefficient);

        final Integer currentOrders = 1;

        Optional<DemandCoefficient> actual =
                demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(actual).isEmpty();
    }
}
