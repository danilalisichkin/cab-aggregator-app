package com.cabaggregator.ratingservice.unit.validator;

import com.cabaggregator.ratingservice.entity.PassengerRate;
import com.cabaggregator.ratingservice.exception.BadRequestException;
import com.cabaggregator.ratingservice.exception.DataUniquenessConflictException;
import com.cabaggregator.ratingservice.repository.PassengerRateRepository;
import com.cabaggregator.ratingservice.util.PassengerRateTestUtil;
import com.cabaggregator.ratingservice.validator.PassengerRateValidator;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PassengerRateValidatorTest {
    @Mock
    private PassengerRateRepository passengerRateRepository;

    @InjectMocks
    private PassengerRateValidator passengerRateValidator;

    @Test
    void validatePassengerRateUniqueness_ShouldThrowDataUniquenessConflictException_WhenPassengerRateAlreadyCreated() {
        when(passengerRateRepository.existsByPassengerIdAndRideId(PassengerRateTestUtil.DRIVER_ID, PassengerRateTestUtil.RIDE_ID))
                .thenReturn(true);

        assertThatThrownBy(
                () -> passengerRateValidator.validatePassengerRateUniqueness(
                        PassengerRateTestUtil.DRIVER_ID, PassengerRateTestUtil.RIDE_ID))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(passengerRateRepository).existsByPassengerIdAndRideId(
                PassengerRateTestUtil.DRIVER_ID, PassengerRateTestUtil.RIDE_ID);
    }

    @Test
    void validatePassengerRateUniqueness_ShouldNotThrowException_WhenPassengerRateDoesNotExist() {
        when(passengerRateRepository.existsByPassengerIdAndRideId(PassengerRateTestUtil.DRIVER_ID, PassengerRateTestUtil.RIDE_ID))
                .thenReturn(false);

        assertThatCode(
                () -> passengerRateValidator.validatePassengerRateUniqueness(
                        PassengerRateTestUtil.DRIVER_ID, PassengerRateTestUtil.RIDE_ID))
                .doesNotThrowAnyException();

        verify(passengerRateRepository).existsByPassengerIdAndRideId(
                PassengerRateTestUtil.DRIVER_ID, PassengerRateTestUtil.RIDE_ID);
    }

    @Test
    void validatePassengerRateSetting_ShouldThrowBadRequestException_WhenPassengerRateAlreadySet() {
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();

        assertThatThrownBy(
                () -> passengerRateValidator.validatePassengerRateSetting(passengerRate))
                .isInstanceOf(BadRequestException.class);

        verifyNoInteractions(passengerRateRepository);
    }

    @Test
    void validatePassengerRateSetting_ShouldNotThrowException_WhenPassengerRateNotSet() {
        PassengerRate passengerRate =
                PassengerRateTestUtil.buildDefaultPassengerRate()
                        .toBuilder()
                        .rate(null)
                        .build();

        assertThatCode(
                () -> passengerRateValidator.validatePassengerRateSetting(passengerRate))
                .doesNotThrowAnyException();

        verifyNoInteractions(passengerRateRepository);
    }

    @Test
    void isDriverRideParticipant_ShouldReturnFalse_WhenUserIsNotRideParticipant() {
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        ObjectId id = passengerRate.getRideId();
        UUID driverId = PassengerRateTestUtil.OTHER_DRIVER_ID;

        when(passengerRateRepository.findByRideId(id))
                .thenReturn(Optional.of(passengerRate));

        boolean actual = passengerRateValidator.isDriverRideParticipant(id, driverId);

        assertThat(actual).isFalse();

        verify(passengerRateRepository).findByRideId(id);
    }

    @Test
    void isDriverRideParticipant_ShouldReturnTrue_WhenUserIsRideParticipant() {
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        ObjectId id = passengerRate.getRideId();
        UUID driverId = passengerRate.getDriverId();

        when(passengerRateRepository.findByRideId(id))
                .thenReturn(Optional.of(passengerRate));

        boolean actual = passengerRateValidator.isDriverRideParticipant(id, driverId);

        assertThat(actual).isTrue();

        verify(passengerRateRepository).findByRideId(id);
    }
}
