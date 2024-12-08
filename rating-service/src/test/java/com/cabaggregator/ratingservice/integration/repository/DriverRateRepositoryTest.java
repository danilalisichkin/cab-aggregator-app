package com.cabaggregator.ratingservice.integration.repository;

import com.cabaggregator.ratingservice.config.AbstractIntegrationTest;
import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.repository.DriverRateRepository;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataMongoTest
@TestPropertySource(properties = {"mongock.enabled=false"})
class DriverRateRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private DriverRateRepository driverRateRepository;

    @BeforeEach
    public void setUp() {
        driverRateRepository.deleteAll();
    }

    @Test
    void findAllByDriverId_ShouldReturnPageOfDriverRates_WhenDriverIdIsEqualToProvided() {
        int pageNumber = 0, pageSize = 10, expectedContentSize = 1;
        DriverRate driverRate =
                DriverRateTestUtil.getDriverRateBuilder()
                        .id(null)
                        .build();
        driverRateRepository.save(driverRate);

        Page<DriverRate> actual =
                driverRateRepository.findAllByDriverId(
                        driverRate.getDriverId(), PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(expectedContentSize)
                .contains(driverRate);
    }

    @Test
    void findAllByDriverId_ShouldReturnEmptyPage_WhenDriverIdIsNotEqualToProvided() {
        int pageNumber = 0, pageSize = 10;
        DriverRate driverRate =
                DriverRateTestUtil.getDriverRateBuilder()
                        .id(null)
                        .build();

        Page<DriverRate> actual =
                driverRateRepository.findAllByDriverId(
                        driverRate.getDriverId(), PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findByDriverIdAndRideId_ShouldReturnDriverRate_WhenDriverRateExists() {
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        driverRateRepository.save(driverRate);

        Optional<DriverRate> actual = driverRateRepository.findByDriverIdAndRideId(
                driverRate.getDriverId(), driverRate.getRideId());

        assertThat(actual).contains(driverRate);
    }

    @Test
    void findByDriverIdAndRideId_ShouldReturnEmptyOptional_WhenDriverRateDoesNotExist() {
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();

        Optional<DriverRate> actual = driverRateRepository.findByDriverIdAndRideId(
                driverRate.getDriverId(), driverRate.getRideId());

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void existsByDriverIdAndRideId_ShouldReturnTrue_WhenDriverRateExists() {
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        driverRateRepository.save(driverRate);

        boolean actual = driverRateRepository.existsByDriverIdAndRideId(
                driverRate.getDriverId(), driverRate.getRideId());

        assertThat(actual).isTrue();
    }

    @Test
    void existsByDriverIdAndRideId_ShouldReturnFalse_WhenDriverRateDoesNotExist() {
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();

        boolean actual = driverRateRepository.existsByDriverIdAndRideId(
                driverRate.getDriverId(), driverRate.getRideId());

        assertThat(actual).isFalse();
    }
}
