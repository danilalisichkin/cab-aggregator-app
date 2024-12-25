package com.cabaggregator.driverservice.unit.validator;

import com.cabaggregator.driverservice.exception.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.DriverRepository;
import com.cabaggregator.driverservice.util.CarTestUtil;
import com.cabaggregator.driverservice.util.DriverTestUtil;
import com.cabaggregator.driverservice.validator.DriverValidator;
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
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class DriverValidatorTest {

    @InjectMocks
    private DriverValidator driverValidator;

    @Mock
    private DriverRepository driverRepository;

    @Test
    void validateIdUniqueness_ShouldThrowDataUniquenessConflictException_WhenIdNotUnique() {
        UUID driverId = DriverTestUtil.DRIVER_ID;

        when(driverRepository.existsById(driverId))
                .thenReturn(true);

        assertThatThrownBy(
                () -> driverValidator.validateIdUniqueness(driverId))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(driverRepository).existsById(driverId);
    }

    @Test
    void validateIdUniqueness_ShouldNotThrowAnyException_WhenIdIsUnique() {
        UUID driverId = DriverTestUtil.NOT_EXISTING_ID;

        when(driverRepository.existsById(driverId))
                .thenReturn(false);

        assertThatCode(
                () -> driverValidator.validateIdUniqueness(driverId))
                .doesNotThrowAnyException();

        verify(driverRepository).existsById(driverId);
    }

    @Test
    void validatePhoneUniqueness_ShouldThrowDataUniquenessConflictException_WhenPhoneNotUnique() {
        String phone = DriverTestUtil.PHONE_NUMBER;

        when(driverRepository.existsByPhoneNumber(phone))
                .thenReturn(true);

        assertThatThrownBy(
                () -> driverValidator.validatePhoneUniqueness(phone))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(driverRepository).existsByPhoneNumber(phone);
    }

    @Test
    void validatePhoneUniqueness_ShouldNotThrowAnyException_WhenPhoneIsUnique() {
        String phone = DriverTestUtil.NOT_EXISTING_PHONE_NUMBER;

        when(driverRepository.existsByPhoneNumber(phone))
                .thenReturn(false);

        assertThatCode(
                () -> driverValidator.validatePhoneUniqueness(phone))
                .doesNotThrowAnyException();

        verify(driverRepository).existsByPhoneNumber(phone);
    }

    @Test
    void validateEmailUniqueness_ShouldThrowDataUniquenessConflictException_WhenEmailNotUnique() {
        String email = DriverTestUtil.EMAIL;

        when(driverRepository.existsByEmail(email))
                .thenReturn(true);

        assertThatThrownBy(
                () -> driverValidator.validateEmailUniqueness(email))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(driverRepository).existsByEmail(email);
    }

    @Test
    void validateEmailUniqueness_ShouldNotThrowAnyException_WhenEmailIsUnique() {
        String email = DriverTestUtil.EMAIL;

        when(driverRepository.existsByEmail(email))
                .thenReturn(false);

        assertThatCode(
                () -> driverValidator.validateEmailUniqueness(email))
                .doesNotThrowAnyException();

        verify(driverRepository).existsByEmail(email);
    }

    @Test
    void validateDriverCarUniqueness_ShouldThrowDataUniquenessConflictException_WhenCarIsNotUnique() {
        Long carId = CarTestUtil.CAR_ID;

        when(driverRepository.existsByCarId(carId))
                .thenReturn(true);

        assertThatThrownBy(
                () -> driverValidator.validateDriverCarUniqueness(carId))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(driverRepository).existsByCarId(carId);
    }

    @Test
    void validateDriverCarUniqueness_ShouldNotThrowAnyException_WhenCarIsUnique() {
        Long carId = CarTestUtil.CAR_ID;

        when(driverRepository.existsByCarId(carId))
                .thenReturn(false);

        assertThatCode(
                () -> driverValidator.validateDriverCarUniqueness(carId))
                .doesNotThrowAnyException();

        verify(driverRepository).existsByCarId(carId);
    }

    @Test
    void validateExistenceOfDriverWithId_ShouldThrowResourceNotFoundException_WhenDriverNotFound() {
        UUID driverId = DriverTestUtil.NOT_EXISTING_ID;

        when(driverRepository.existsById(driverId))
                .thenReturn(false);

        assertThatThrownBy(
                () -> driverValidator.validateExistenceOfDriverWithId(driverId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(driverRepository).existsById(driverId);
    }

    @Test
    void validateExistenceOfDriverWithId_ShouldNotThrowAnyException_WhenDriverFound() {
        UUID driverId = DriverTestUtil.DRIVER_ID;

        when(driverRepository.existsById(driverId))
                .thenReturn(true);

        assertThatCode(
                () -> driverValidator.validateExistenceOfDriverWithId(driverId))
                .doesNotThrowAnyException();

        verify(driverRepository).existsById(driverId);
    }
}
