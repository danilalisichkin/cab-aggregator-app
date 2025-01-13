package com.cabaggregator.ratingservice.integration.repository;

import com.cabaggregator.ratingservice.config.AbstractIntegrationTest;
import com.cabaggregator.ratingservice.entity.PassengerRate;
import com.cabaggregator.ratingservice.repository.PassengerRateRepository;
import com.cabaggregator.ratingservice.util.JsonReader;
import com.cabaggregator.ratingservice.util.PassengerRateTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataMongoTest
class PassengerRateRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private PassengerRateRepository passengerRateRepository;

    @BeforeEach
    void setUp() throws IOException {
        passengerRateRepository.deleteAll();

        List<PassengerRate> data = JsonReader.readValues("/mongodb/passenger_rates.json", PassengerRate.class);

        passengerRateRepository.saveAll(data);
    }

    @Test
    void findAllByPassengerId_ShouldReturnPageOfDriverRates_WhenPassengerRateWithPassengerIdExists() {
        int pageNumber = 0;
        int pageSize = 10;
        int expectedContentSize = 1;
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();

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
    void findAllByDriverId_ShouldReturnEmptyPage_WhenPassengerRateWithPassengerIdDoesNotExist() {
        int pageNumber = 0;
        int pageSize = 10;

        Page<PassengerRate> actual =
                passengerRateRepository.findAllByPassengerId(
                        PassengerRateTestUtil.NOT_EXISTING_PASSENGER_ID, PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findByPassengerIdAndRideId_ShouldReturnPassengerRate_WhenPassengerRateExists() {
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();

        Optional<PassengerRate> actual = passengerRateRepository.findByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(actual).contains(passengerRate);
    }

    @Test
    void findByPassengerIdAndRideId_ShouldReturnEmptyOptional_WhenPassengerRateDoesNotExist() {
        Optional<PassengerRate> actual = passengerRateRepository.findByPassengerIdAndRideId(
                PassengerRateTestUtil.NOT_EXISTING_PASSENGER_ID, PassengerRateTestUtil.NOT_EXISTING_RIDE_ID);

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void existsByPassengerIdAndRideId_ShouldReturnTrue_WhenPassengerRateExists() {
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();

        boolean actual = passengerRateRepository.existsByPassengerIdAndRideId(
                passengerRate.getPassengerId(), passengerRate.getRideId());

        assertThat(actual).isTrue();
    }

    @Test
    void existsByPassengerIdAndRideId_ShouldReturnFalse_WhenPassengerRateDoesNotExist() {
        boolean actual = passengerRateRepository.existsByPassengerIdAndRideId(
                PassengerRateTestUtil.NOT_EXISTING_PASSENGER_ID, PassengerRateTestUtil.NOT_EXISTING_RIDE_ID);

        assertThat(actual).isFalse();
    }
}
