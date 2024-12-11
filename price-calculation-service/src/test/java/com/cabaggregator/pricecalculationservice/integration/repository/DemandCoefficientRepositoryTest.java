package com.cabaggregator.pricecalculationservice.integration.repository;

import com.cabaggregator.pricecalculationservice.config.AbstractIntegrationTest;
import com.cabaggregator.pricecalculationservice.entity.DemandCoefficient;
import com.cabaggregator.pricecalculationservice.repository.DemandCoefficientRepository;
import com.cabaggregator.pricecalculationservice.util.DemandCoefficientTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Sql(scripts = {
        "classpath:/postgresql/import_demand_coefficients.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DemandCoefficientRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private DemandCoefficientRepository demandCoefficientRepository;

    @Test
    void findHighestMatchingCoefficient_ShouldReturnStandardCoefficient_WhenCurrentDemandIsLow() {
        DemandCoefficient lowCoefficient = DemandCoefficientTestUtil.buildLowDemandCoefficient();

        final Integer currentOrders = 0;

        Optional<DemandCoefficient> actual =
                demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(actual)
                .isNotEmpty()
                .contains(lowCoefficient);
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnStandardCoefficient_WhenCurrentDemandIsStandard() {
        DemandCoefficient standardCoefficient = DemandCoefficientTestUtil.buildStandardDemandCoefficient();

        final Integer currentOrders = 7;

        Optional<DemandCoefficient> actual =
                demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(actual)
                .isNotEmpty()
                .contains(standardCoefficient);
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnMediumCoefficient_WhenCurrentDemandIsMedium() {
        DemandCoefficient mediumCoefficient = DemandCoefficientTestUtil.buildMediumDemandCoefficient();

        final Integer currentOrders = 10;

        Optional<DemandCoefficient> actual =
                demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(actual)
                .isNotEmpty()
                .contains(mediumCoefficient);
    }

    @Test
    void findHighestMatchingCoefficient_ShouldReturnHighCoefficient_WhenCurrentDemandIsHigh() {
        DemandCoefficient highCoefficient = DemandCoefficientTestUtil.buildHighDemandCoefficient();

        final Integer currentOrders = 25;

        Optional<DemandCoefficient> actual =
                demandCoefficientRepository.findHighestMatchingCoefficient(currentOrders);

        assertThat(actual)
                .isNotEmpty()
                .contains(highCoefficient);
    }
}
