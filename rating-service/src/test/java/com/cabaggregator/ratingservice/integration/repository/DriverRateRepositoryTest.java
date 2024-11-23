package com.cabaggregator.ratingservice.integration.repository;

import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.repository.DriverRateRepository;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Testcontainers
@DataMongoTest
@TestPropertySource(properties = "mongock.enabled=false")
class DriverRateRepositoryTest {
    @Autowired
    private DriverRateRepository driverRateRepository;

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0").withExposedPorts(27016);

    @BeforeEach
    public void setUp() {
        driverRateRepository.deleteAll();
    }

    @Test
    void findByDriverIdAndRideId_ShouldReturnDriverRate_WhenDriverRateExists() {
        final DriverRate driverRate = DriverRateTestUtil.buildDriverRate();
        driverRateRepository.save(driverRate);

        Optional<DriverRate> foundDriverRate = driverRateRepository.findByDriverIdAndRideId(
                driverRate.getDriverId(), driverRate.getRideId());

        assertThat(foundDriverRate).contains(driverRate);
    }

    @Test
    void findByDriverIdAndRideId_ShouldReturnEmptyOptional_WhenDriverRateDoesNotExist() {
        final DriverRate driverRate = DriverRateTestUtil.buildDriverRate();

        Optional<DriverRate> foundDriverRate = driverRateRepository.findByDriverIdAndRideId(
                driverRate.getDriverId(), driverRate.getRideId());

        assertThat(foundDriverRate)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void existsDriverIdAndRideId_ShouldReturnTrue_WhenDriverRateExists() {
        final DriverRate driverRate = DriverRateTestUtil.buildDriverRate();
        driverRateRepository.save(driverRate);

        boolean result = driverRateRepository.existsByDriverIdAndRideId(
                driverRate.getDriverId(), driverRate.getRideId());

        assertThat(result).isTrue();
    }

    @Test
    void existsDriverIdAndRideId_ShouldReturnFalse_WhenDriverRateDoesNotExist() {
        final DriverRate driverRate = DriverRateTestUtil.buildDriverRate();

        boolean result = driverRateRepository.existsByDriverIdAndRideId(
                driverRate.getDriverId(), driverRate.getRideId());

        assertThat(result).isFalse();
    }
}
