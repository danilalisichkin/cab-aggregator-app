package com.cabaggregator.ratingservice.integration.repository;

import com.cabaggregator.ratingservice.config.AbstractIntegrationTest;
import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.repository.DriverRateRepository;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import com.cabaggregator.ratingservice.util.JsonReader;
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
class DriverRateRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private DriverRateRepository driverRateRepository;

    @BeforeEach
    void setUp() throws IOException {
        driverRateRepository.deleteAll();

        List<DriverRate> data = JsonReader.readValues("/mongodb/driver_rates.json", DriverRate.class);

        driverRateRepository.saveAll(data);
    }

    @Test
    void findAllByDriverId_ShouldReturnPageOfDriverRates_WhenDriverRateWithDriverIdExists() {
        int pageNumber = 0;
        int pageSize = 10;
        int expectedContentSize = 1;
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();

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
    void findAllByDriverId_ShouldReturnEmptyPage_WhenDriverRateWithDriverIdDoesNotExist() {
        int pageNumber = 0;
        int pageSize = 10;

        Page<DriverRate> actual =
                driverRateRepository.findAllByDriverId(
                        DriverRateTestUtil.NOT_EXISTING_DRIVER_ID, PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findByDriverIdAndRideId_ShouldReturnDriverRate_WhenDriverRateExists() {
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();

        Optional<DriverRate> actual = driverRateRepository.findByDriverIdAndRideId(
                driverRate.getDriverId(), driverRate.getRideId());

        assertThat(actual).contains(driverRate);
    }

    @Test
    void findByDriverIdAndRideId_ShouldReturnEmptyOptional_WhenDriverRateDoesNotExist() {
        Optional<DriverRate> actual = driverRateRepository.findByDriverIdAndRideId(
                DriverRateTestUtil.NOT_EXISTING_DRIVER_ID, DriverRateTestUtil.NOT_EXISTING_RIDE_ID);

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void existsByDriverIdAndRideId_ShouldReturnTrue_WhenDriverRateExists() {
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();

        boolean actual = driverRateRepository.existsByDriverIdAndRideId(
                driverRate.getDriverId(), driverRate.getRideId());

        assertThat(actual).isTrue();
    }

    @Test
    void existsByDriverIdAndRideId_ShouldReturnFalse_WhenDriverRateDoesNotExist() {
        boolean actual = driverRateRepository.existsByDriverIdAndRideId(
                DriverRateTestUtil.NOT_EXISTING_DRIVER_ID, DriverRateTestUtil.NOT_EXISTING_RIDE_ID);

        assertThat(actual).isFalse();
    }
}
