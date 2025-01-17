package com.cabaggregator.ratingservice.unit.validator;

import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.exception.BadRequestException;
import com.cabaggregator.ratingservice.exception.DataUniquenessConflictException;
import com.cabaggregator.ratingservice.exception.ForbiddenException;
import com.cabaggregator.ratingservice.repository.DriverRateRepository;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import com.cabaggregator.ratingservice.validator.DriverRateValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class DriverRateValidatorTest {
    @Mock
    private DriverRateRepository driverRateRepository;

    @InjectMocks
    private DriverRateValidator driverRateValidator;

    @Test
    void validateDriverRateUniqueness_ShouldThrowDataUniquenessConflictException_WhenDriverRateAlreadyCreated() {
        when(driverRateRepository.existsByDriverIdAndRideId(DriverRateTestUtil.DRIVER_ID, DriverRateTestUtil.RIDE_ID))
                .thenReturn(true);

        assertThatThrownBy(
                () -> driverRateValidator.validateDriverRateUniqueness(
                        DriverRateTestUtil.DRIVER_ID, DriverRateTestUtil.RIDE_ID))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(driverRateRepository).existsByDriverIdAndRideId(
                DriverRateTestUtil.DRIVER_ID, DriverRateTestUtil.RIDE_ID);
    }

    @Test
    void validateDriverRateUniqueness_ShouldNotThrowException_WhenDriverRateDoesNotExist() {
        when(driverRateRepository.existsByDriverIdAndRideId(DriverRateTestUtil.DRIVER_ID, DriverRateTestUtil.RIDE_ID))
                .thenReturn(false);

        assertThatCode(
                () -> driverRateValidator.validateDriverRateUniqueness(
                        DriverRateTestUtil.DRIVER_ID, DriverRateTestUtil.RIDE_ID))
                .doesNotThrowAnyException();

        verify(driverRateRepository).existsByDriverIdAndRideId(
                DriverRateTestUtil.DRIVER_ID, DriverRateTestUtil.RIDE_ID);
    }

    @Test
    void validateDriverRateSetting_ShouldThrowBadRequestException_WhenDriverRateAlreadySet() {
        DriverRate driverRate = DriverRateTestUtil.buildDefaultDriverRate();

        assertThatThrownBy(
                () -> driverRateValidator.validateDriverRateSetting(driverRate))
                .isInstanceOf(BadRequestException.class);

        verifyNoInteractions(driverRateRepository);
    }

    @Test
    void validateDriverRateSetting_ShouldNotThrowException_WhenDriverRateNotSet() {
        DriverRate driverRate =
                DriverRateTestUtil.buildDefaultDriverRate()
                        .toBuilder()
                        .rate(null)
                        .build();

        assertThatCode(
                () -> driverRateValidator.validateDriverRateSetting(driverRate))
                .doesNotThrowAnyException();

        verifyNoInteractions(driverRateRepository);
    }

    @Test
    void validatePassengerParticipation_ShouldThrowForbiddenException_WhenUserIsNotRideParticipant() {
        DriverRate driverRate = DriverRateTestUtil.buildDefaultDriverRate();
        UUID passengerId = DriverRateTestUtil.OTHER_PASSENGER_ID;

        assertThatThrownBy(
                () -> driverRateValidator.validatePassengerParticipation(driverRate, passengerId))
                .isInstanceOf(ForbiddenException.class);

        verifyNoInteractions(driverRateRepository);
    }

    @Test
    void validatePassengerParticipation_ShouldNotThrowException_WhenUserIsRideParticipant() {
        DriverRate driverRate = DriverRateTestUtil.buildDefaultDriverRate();
        UUID passengerId = driverRate.getPassengerId();

        assertThatCode(
                () -> driverRateValidator.validatePassengerParticipation(driverRate, passengerId))
                .doesNotThrowAnyException();

        verifyNoInteractions(driverRateRepository);
    }
}
