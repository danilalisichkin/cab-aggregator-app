package com.cabaggregator.ratingservice.integration.repository;

import com.cabaggregator.ratingservice.config.AbstractIntegrationTest;
import com.cabaggregator.ratingservice.entity.PassengerRate;
import com.cabaggregator.ratingservice.repository.PassengerRateRepository;
import com.cabaggregator.ratingservice.util.PassengerRateTestUtil;
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
class PassengerRateRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private PassengerRateRepository passengerRateRepository;

    @BeforeEach
    public void setUp() {
        passengerRateRepository.deleteAll();
    }

    @Test
    void findAllByPassengerId_ShouldReturnPageOfDriverRates_WhenPassengerIdIsEqualToProvided() {
        int pageNumber = 0, pageSize = 10, expectedContentSize = 1;
        PassengerRate passengerRate =
                PassengerRateTestUtil.getPassengerRateBuilder()
                        .id(null)
                        .build();
        passengerRateRepository.save(passengerRate);

        Page<PassengerRate> actual =
                passengerRateRepository.findAllByPassengerId(
                        passengerRate.getPassengerId(), PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(expectedContentSize)
                .contains(passengerRate);
    }

    @Test
    void findAllByDriverId_ShouldReturnEmptyPage_WhenPassengerIdIsNotEqualToProvided() {
        int pageNumber = 0, pageSize = 10;
        PassengerRate passengerRate =
                PassengerRateTestUtil.getPassengerRateBuilder()
                        .id(null)
                        .build();

        Page<PassengerRate> actual =
                passengerRateRepository.findAllByPassengerId(
                        passengerRate.getPassengerId(), PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findByPassengerIdAndRideId_ShouldReturnPassengerRate_WhenPassengerRateExists() {
        PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();
        passengerRateRepository.save(passengerRate);

        Optional<PassengerRate> actual = passengerRateRepository.findByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(actual).contains(passengerRate);
    }

    @Test
    void findByPassengerIdAndRideId_ShouldReturnEmptyOptional_WhenPassengerRateDoesNotExist() {
        PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();

        Optional<PassengerRate> actual = passengerRateRepository.findByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void existsByPassengerIdAndRideId_ShouldReturnTrue_WhenPassengerRateExists() {
        PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();
        passengerRateRepository.save(passengerRate);

        boolean actual = passengerRateRepository.existsByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(actual).isTrue();
    }

    @Test
    void existsByPassengerIdAndRideId_ShouldReturnFalse_WhenPassengerRateDoesNotExist() {
        PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();

        boolean actual = passengerRateRepository.existsByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(actual).isFalse();
    }
}
