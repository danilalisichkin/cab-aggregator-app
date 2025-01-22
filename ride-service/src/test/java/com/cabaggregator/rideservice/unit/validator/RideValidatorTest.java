package com.cabaggregator.rideservice.unit.validator;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.util.RideTestUtil;
import com.cabaggregator.rideservice.validator.RideValidator;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
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

    /**
     * Checks if driver is participant of the ride.
     **/
    public boolean isDriverRideParticipant(ObjectId rideId, UUID driverId) {
        Optional<Ride> ride = rideRepository.findById(rideId);

        return ride.isPresent() && ride.get().getDriverId().equals(driverId);
    }

    @Test
    void isPassengerFreeNow_ShouldReturnFalse_WhenPassengerIsParticipatingInRideNow() {
        UUID passengerId = RideTestUtil.PASSENGER_ID;

        when(rideRepository.existsByPassengerIdAndStatusNotIn(any(UUID.class), anySet()))
                .thenReturn(true);

        boolean actual = rideValidator.isPassengerFreeNow(passengerId);

        assertThat(actual).isFalse();

        verify(rideRepository).existsByPassengerIdAndStatusNotIn(any(UUID.class), anySet());
    }

    @Test
    void isPassengerFreeNow_ShouldReturnTrue_WhenPassengerIsNotParticipatingInRideNow() {
        UUID passengerId = RideTestUtil.PASSENGER_ID;

        when(rideRepository.existsByPassengerIdAndStatusNotIn(any(UUID.class), anySet()))
                .thenReturn(false);

        boolean actual = rideValidator.isPassengerFreeNow(passengerId);

        assertThat(actual).isTrue();

        verify(rideRepository).existsByPassengerIdAndStatusNotIn(any(UUID.class), anySet());
    }

    @Test
    void isDriverRideParticipant_ShouldReturnTrue_WhenDriverIsRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID driverId = ride.getDriverId();
        ObjectId rideId = ride.getId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));

        boolean actual = rideValidator.isDriverRideParticipant(rideId, driverId);

        assertThat(actual).isTrue();

        verify(rideRepository).findById(rideId);
    }

    @Test
    void isDriverRideParticipant_ShouldReturnFalse_WhenDriverIsNotRideParticipant() {
        Ride ride = RideTestUtil.buildDefaultRide();
        UUID driverId = RideTestUtil.NOT_EXISTING_DRIVER_ID;
        ObjectId rideId = ride.getId();

        when(rideRepository.findById(rideId))
                .thenReturn(Optional.of(ride));

        boolean actual = rideValidator.isDriverRideParticipant(rideId, driverId);

        assertThat(actual).isFalse();

        verify(rideRepository).findById(rideId);
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
