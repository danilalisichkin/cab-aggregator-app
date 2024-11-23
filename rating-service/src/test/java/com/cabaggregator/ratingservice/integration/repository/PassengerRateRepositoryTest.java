package com.cabaggregator.ratingservice.integration.repository;

import com.cabaggregator.ratingservice.entity.PassengerRate;
import com.cabaggregator.ratingservice.repository.PassengerRateRepository;
import com.cabaggregator.ratingservice.util.PassengerRateTestUtil;
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
class PassengerRateRepositoryTest {
    @Autowired
    private PassengerRateRepository passengerRateRepository;

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0").withExposedPorts(27016);

    @BeforeEach
    public void setUp() {
        passengerRateRepository.deleteAll();
    }

    @Test
    void findByPassengerIdAndRideId_ShouldReturnPassengerRate_WhenPassengerRateExists() {
        final PassengerRate passengerRate = PassengerRateTestUtil.buildPassengerRate();
        passengerRateRepository.save(passengerRate);

        Optional<PassengerRate> foundPassengerRate = passengerRateRepository.findByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(foundPassengerRate).contains(passengerRate);
    }

    @Test
    void findByDriverIdAndRideId_ShouldReturnEmptyOptional_WhenDriverRateDoesNotExist() {
        final PassengerRate passengerRate = PassengerRateTestUtil.buildPassengerRate();

        Optional<PassengerRate> foundPassengerRate = passengerRateRepository.findByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(foundPassengerRate)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void existsDriverIdAndRideId_ShouldReturnTrue_WhenDriverRateExists() {
        final PassengerRate passengerRate = PassengerRateTestUtil.buildPassengerRate();
        passengerRateRepository.save(passengerRate);

        boolean result = passengerRateRepository.existsByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(result).isTrue();
    }

    @Test
    void existsDriverIdAndRideId_ShouldReturnFalse_WhenDriverRateDoesNotExist() {
        final PassengerRate passengerRate = PassengerRateTestUtil.buildPassengerRate();

        boolean result = passengerRateRepository.existsByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(result).isFalse();
    }
}
