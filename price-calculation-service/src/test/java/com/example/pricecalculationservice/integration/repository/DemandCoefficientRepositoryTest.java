package com.example.pricecalculationservice.integration.repository;

import com.example.pricecalculationservice.entity.DemandCoefficient;
import com.example.pricecalculationservice.repository.DemandCoefficientRepository;
import com.example.pricecalculationservice.util.DemandCoefficientTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DemandCoefficientRepositoryTest extends AbstractRepositoryIntegrationTest {
    @Autowired
    private DemandCoefficientRepository demandCoefficientRepository;

    private DemandCoefficient saveLowCoefficient() {
        return demandCoefficientRepository.save(
                DemandCoefficientTestUtil.getLowDemandCoefficientBuilder().build());
    }

    private DemandCoefficient saveDefaultCoefficient() {
        return demandCoefficientRepository.save(
                DemandCoefficientTestUtil.getStandardDemandCoefficientBuilder().build());
    }

    private DemandCoefficient saveHighCoefficient() {
        return demandCoefficientRepository.save(
                DemandCoefficientTestUtil.getHighDemandCoefficientBuilder().build());
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnStandardCoefficient_WhenCurrentOrdersIsUsual() {
        saveLowCoefficient();
        saveHighCoefficient();
        DemandCoefficient standardCoefficient = saveDefaultCoefficient();

        final Integer currentOrders = 15;

        Optional<DemandCoefficient> actual =
                demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(actual)
                .isNotEmpty()
                .contains(standardCoefficient);
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnHighCoefficient_WhenCurrentOrdersIsHighest() {
        saveLowCoefficient();
        saveDefaultCoefficient();
        DemandCoefficient highCoefficient = saveHighCoefficient();

        final Integer currentOrders = 25;

        Optional<DemandCoefficient> actual =
                demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(actual)
                .isNotEmpty()
                .contains(highCoefficient);
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnEmptyOptional_WhenCoefficientDoesNotExists() {
        final Integer currentOrders = 1;

        Optional<DemandCoefficient> actual =
                demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(actual).isEmpty();
    }
}
