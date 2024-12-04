package com.cabaggregator.ratingservice.unit.validator;

import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.exception.BadRequestException;
import com.cabaggregator.ratingservice.exception.DataUniquenessConflictException;
import com.cabaggregator.ratingservice.repository.DriverRateRepository;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import com.cabaggregator.ratingservice.validator.DriverRateValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();

        assertThatThrownBy(
                () -> driverRateValidator.validateDriverRateSetting(driverRate))
                .isInstanceOf(BadRequestException.class);

        verifyNoInteractions(driverRateRepository);
    }

    @Test
    void validateDriverRateSetting_ShouldNotThrowException_WhenDriverRateNotSet() {
        DriverRate driverRate =
                DriverRateTestUtil.getDriverRateBuilder()
                        .rate(null)
                        .build();

        assertThatCode(
                () -> driverRateValidator.validateDriverRateSetting(driverRate))
                .doesNotThrowAnyException();

        verifyNoInteractions(driverRateRepository);
    }
}
