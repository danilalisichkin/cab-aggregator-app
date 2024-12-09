package com.cabaggregator.rideservice.integration.repository;

import com.cabaggregator.rideservice.config.AbstractIntegrationTest;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.util.JsonReader;
import com.cabaggregator.rideservice.util.RideTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataMongoTest
@TestPropertySource(properties = {"mongock.enabled=false"})
class RideRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private RideRepository rideRepository;

    @BeforeEach
    void setUp() throws IOException {
        rideRepository.deleteAll();

        List<Ride> data = JsonReader.readValues("/mongodb/rides.json", Ride.class);

        rideRepository.saveAll(data);
    }

    @Test
    void findAllByPassengerId_ShouldReturnPageOfRides_WhenRideWithPassengerIdExists() {
        int pageNumber = 0;
        int pageSize = 10;
        int expectedContentSize = 1;
        Ride ride = RideTestUtil.getRideBuilder().build();

        Page<Ride> actual = rideRepository.findAllByPassengerId(
                ride.getPassengerId(), PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(expectedContentSize)
                .contains(ride);
    }

    @Test
    void findAllByPassengerId_ShouldReturnEmptyPage_WhenRideWithPassengerIdDoesNotExist() {
        int pageNumber = 0;
        int pageSize = 10;

        Page<Ride> actual = rideRepository.findAllByPassengerId(
                RideTestUtil.NOT_EXISTING_PASSENGER_ID, PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByDriverId_ShouldReturnPageOfRides_WhenRideWithDriverIdExists() {
        int pageNumber = 0;
        int pageSize = 10;
        int expectedContentSize = 1;
        Ride ride = RideTestUtil.getRideBuilder().build();

        Page<Ride> actual = rideRepository.findAllByDriverId(
                ride.getDriverId(), PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(expectedContentSize)
                .contains(ride);
    }

    @Test
    void findAllByDriverId_ShouldReturnEmptyPage_WhenRideWithDriverIdDoesNotExist() {
        int pageNumber = 0;
        int pageSize = 10;

        Page<Ride> actual = rideRepository.findAllByDriverId(
                RideTestUtil.NOT_EXISTING_DRIVER_ID, PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByPassengerIdAndStatus_ShouldReturnPageOfRides_WhenRideWithPassengerIdAndStatusExists() {
        int pageNumber = 0;
        int pageSize = 10;
        int expectedContentSize = 1;
        Ride ride = RideTestUtil.getRideBuilder().build();

        Page<Ride> actual = rideRepository.findAllByPassengerIdAndStatus(
                ride.getPassengerId(), ride.getStatus(), PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(expectedContentSize)
                .contains(ride);
    }

    @Test
    void findAllByPassengerIdAndStatus_ShouldReturnEmptyPage_WhenRideWithPassengerIdDoesNotExist() {
        int pageNumber = 0;
        int pageSize = 10;

        Page<Ride> actual = rideRepository.findAllByPassengerIdAndStatus(
                RideTestUtil.NOT_EXISTING_PASSENGER_ID, RideTestUtil.STATUS, PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByPassengerIdAndStatus_ShouldReturnEmptyPage_WhenRideWithStatusDoesNotExist() {
        int pageNumber = 0;
        int pageSize = 10;

        Page<Ride> actual = rideRepository.findAllByPassengerIdAndStatus(
                RideTestUtil.PASSENGER_ID, RideTestUtil.NOT_EXISTING_STATUS, PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByDriverIdAndStatus_ShouldReturnPageOfRides_WhenRideWithDriverIdAndStatusExists() {
        int pageNumber = 0;
        int pageSize = 10;
        int expectedContentSize = 1;
        Ride ride = RideTestUtil.getRideBuilder().build();

        Page<Ride> actual = rideRepository.findAllByDriverIdAndStatus(
                ride.getDriverId(), ride.getStatus(), PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(expectedContentSize)
                .contains(ride);
    }

    @Test
    void findAllByDriverIdAndStatus_ShouldReturnPageOfRides_WhenRideWithDriverIdDoesNotExist() {
        int pageNumber = 0;
        int pageSize = 10;

        Page<Ride> actual = rideRepository.findAllByDriverIdAndStatus(
                RideTestUtil.NOT_EXISTING_DRIVER_ID, RideTestUtil.STATUS, PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByDriverIdAndStatus_ShouldReturnPageOfRides_WhenRideWithStatusDoesNotExist() {
        int pageNumber = 0;
        int pageSize = 10;
        Page<Ride> actual = rideRepository.findAllByDriverIdAndStatus(
                RideTestUtil.DRIVER_ID, RideTestUtil.NOT_EXISTING_STATUS, PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }

    @Test
    void findAllByStatus_ShouldReturnPageOfRides_WhenRideWithStatusExists() {
        int pageNumber = 0;
        int pageSize = 10;
        int expectedContentSize = 1;
        Ride ride = RideTestUtil.getRideBuilder().build();

        Page<Ride> actual = rideRepository.findAllByStatus(
                ride.getStatus(), PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent())
                .isNotEmpty()
                .hasSize(expectedContentSize)
                .contains(ride);
    }

    @Test
    void findAllByStatus_ShouldReturnPageOfRides_WhenRideWithStatusDoesNotExist() {
        int pageNumber = 0;
        int pageSize = 10;

        Page<Ride> actual = rideRepository.findAllByStatus(
                RideTestUtil.NOT_EXISTING_STATUS, PageRequest.of(pageNumber, pageSize));

        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).isEmpty();
    }
}
