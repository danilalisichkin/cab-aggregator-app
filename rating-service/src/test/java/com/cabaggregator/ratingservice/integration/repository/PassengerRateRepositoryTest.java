package com.cabaggregator.ratingservice.integration.repository;

import com.cabaggregator.ratingservice.entity.PassengerRate;
import com.cabaggregator.ratingservice.repository.PassengerRateRepository;
import com.cabaggregator.ratingservice.util.PassengerRateTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataMongoTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"mongock.enabled=false"})
class PassengerRateRepositoryTest extends AbstractRepositoryIntegrationTest {
    @Autowired
    private PassengerRateRepository passengerRateRepository;

    @BeforeEach
    public void setUp() {
        passengerRateRepository.deleteAll();
    }

    @Test
    void findByPassengerIdAndRideId_ShouldReturnPassengerRate_WhenPassengerRateExists() {
        final PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();
        passengerRateRepository.save(passengerRate);

        Optional<PassengerRate> actual = passengerRateRepository.findByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(actual).contains(passengerRate);
    }

    @Test
    void findByDriverIdAndRideId_ShouldReturnEmptyOptional_WhenDriverRateDoesNotExist() {
        final PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();

        Optional<PassengerRate> actual = passengerRateRepository.findByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void existsDriverIdAndRideId_ShouldReturnTrue_WhenDriverRateExists() {
        final PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();
        passengerRateRepository.save(passengerRate);

        boolean actual = passengerRateRepository.existsByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(actual).isTrue();
    }

    @Test
    void existsDriverIdAndRideId_ShouldReturnFalse_WhenDriverRateDoesNotExist() {
        final PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();

        boolean actual = passengerRateRepository.existsByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(actual).isFalse();
    }
}
