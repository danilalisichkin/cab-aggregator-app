package com.cabaggregator.rideservice.unit.validator;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.util.RideTestUtil;
import com.cabaggregator.rideservice.validator.RideValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class RideValidatorTest {
    @InjectMocks
    private RideValidator rideValidator;

    @Mock
    private RideRepository rideRepository;

    @Test
    void validateAddresses_ShouldThrowBadRequestException_WhenAddressesAreEqual() {
        Address pickUpAddress = RideTestUtil.buildPickUpAddress();

        assertThatThrownBy(
                () -> rideValidator.validateAddresses(pickUpAddress, pickUpAddress))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void validateAddresses_ShouldNotThrowAnyException_WhenAddressesAreNotEqual() {
        Address pickUpAddress = RideTestUtil.buildPickUpAddress();
        Address dropOffAddress = RideTestUtil.buildDropOffAddress();

        assertThatCode(
                () -> rideValidator.validateAddresses(pickUpAddress, dropOffAddress))
                .doesNotThrowAnyException();
    }

    @Test
    void validateDriverParticipation_ShouldThrowForbiddenException_WhenDriverIsNotRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID driverId = RideTestUtil.NOT_EXISTING_DRIVER_ID;

        assertThatThrownBy(
                () -> rideValidator.validateDriverParticipation(ride, driverId))
                .isInstanceOf(ForbiddenException.class);
    }

    @Test
    void validateDriverParticipation_ShouldNotThrowAnyException_WhenDriverIsRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID driverId = ride.getDriverId();

        assertThatCode(
                () -> rideValidator.validateDriverParticipation(ride, driverId))
                .doesNotThrowAnyException();
    }

    @Test
    void validatePassengerParticipation_ShouldThrowForbiddenException_WhenPassengerIsNotRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID passengerId = RideTestUtil.NOT_EXISTING_PASSENGER_ID;

        assertThatThrownBy(
                () -> rideValidator.validatePassengerParticipation(ride, passengerId))
                .isInstanceOf(ForbiddenException.class);
    }

    @Test
    void validatePassengerParticipation_ShouldNotThrowAnyException_WhenPassengerIsRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID passengerId = ride.getPassengerId();

        assertThatCode(
                () -> rideValidator.validatePassengerParticipation(ride, passengerId))
                .doesNotThrowAnyException();
    }

    @Test
    void validatePassengerFreedom_ShouldThrowBadRequestException_WhenPassengerIsParticipatingInRideNow() {
        UUID passengerId = RideTestUtil.PASSENGER_ID;

        when(rideRepository.existsByPassengerIdAndStatusNotIn(any(UUID.class), anySet()))
                .thenReturn(true);

        assertThatThrownBy(
                () -> rideValidator.validatePassengerFreedom(passengerId))
                .isInstanceOf(BadRequestException.class);

        verify(rideRepository).existsByPassengerIdAndStatusNotIn(any(UUID.class), anySet());
    }

    @Test
    void validatePassengerFreedom_ShouldNotThrowAnyException_WhenPassengerIsNotParticipatingInRideNow() {
        UUID passengerId = RideTestUtil.PASSENGER_ID;

        when(rideRepository.existsByPassengerIdAndStatusNotIn(any(UUID.class), anySet()))
                .thenReturn(false);

        assertThatCode(
                () -> rideValidator.validatePassengerFreedom(passengerId))
                .doesNotThrowAnyException();

        verify(rideRepository).existsByPassengerIdAndStatusNotIn(any(UUID.class), anySet());
    }

    @Test
    void validateDriverFreedom_ShouldThrowBadRequestException_WhenDriverIsParticipatingInRideNow() {
        UUID driverId = RideTestUtil.DRIVER_ID;

        when(rideRepository.existsByDriverIdAndStatusNotIn(any(UUID.class), anySet()))
                .thenReturn(true);

        assertThatThrownBy(
                () -> rideValidator.validateDriverFreedom(driverId))
                .isInstanceOf(BadRequestException.class);

        verify(rideRepository).existsByDriverIdAndStatusNotIn(any(UUID.class), anySet());
    }

    @Test
    void validateDriverFreedom_ShouldNotThrowAnyException_WhenDriverIsNotParticipatingInRideNow() {
        UUID driverId = RideTestUtil.DRIVER_ID;

        when(rideRepository.existsByDriverIdAndStatusNotIn(any(UUID.class), anySet()))
                .thenReturn(false);

        assertThatCode(
                () -> rideValidator.validateDriverFreedom(driverId))
                .doesNotThrowAnyException();

        verify(rideRepository).existsByDriverIdAndStatusNotIn(any(UUID.class), anySet());
    }
}
