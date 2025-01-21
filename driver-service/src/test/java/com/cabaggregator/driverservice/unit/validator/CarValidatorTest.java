package com.cabaggregator.driverservice.unit.validator;

import com.cabaggregator.driverservice.entity.Car;
import com.cabaggregator.driverservice.entity.Driver;
import com.cabaggregator.driverservice.exception.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarRepository;
import com.cabaggregator.driverservice.util.CarTestUtil;
import com.cabaggregator.driverservice.util.DriverTestUtil;
import com.cabaggregator.driverservice.validator.CarValidator;
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
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CarValidatorTest {

    @InjectMocks
    private CarValidator carValidator;

    @Mock
    private CarRepository carRepository;

    @Test
    void validateLicencePlateUniqueness_ShouldThrowDataUniquenessConflictException_WhenLicencePlateNotUnique() {
        String licencePlate = CarTestUtil.LICENSE_PLATE;

        when(carRepository.existsByLicensePlate(licencePlate))
                .thenReturn(true);

        assertThatThrownBy(
                () -> carValidator.validateLicencePlateUniqueness(licencePlate))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(carRepository).existsByLicensePlate(licencePlate);
    }

    @Test
    void validateLicencePlateUniqueness_ShouldNotThrowAnyException_WhenLicencePlateUnique() {
        String licencePlate = CarTestUtil.NOT_EXISTING_LICENSE_PLATE;

        when(carRepository.existsByLicensePlate(licencePlate))
                .thenReturn(false);

        assertThatCode(
                () -> carValidator.validateLicencePlateUniqueness(licencePlate))
                .doesNotThrowAnyException();

        verify(carRepository).existsByLicensePlate(licencePlate);
    }

    @Test
    void validateExistenceOfCarWithId_ShouldThrowResourceNotFoundException_WhenCarWithIdNotFound() {
        Long carId = CarTestUtil.NOT_EXISTING_ID;

        when(carRepository.existsById(carId))
                .thenReturn(false);

        assertThatThrownBy(
                () -> carValidator.validateExistenceOfCarWithId(carId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(carRepository).existsById(carId);
    }

    @Test
    void validateExistenceOfCarWithId_ShouldNotThrowAnyException_WhenCarWithIdFound() {
        Long carId = CarTestUtil.ID;

        when(carRepository.existsById(carId))
                .thenReturn(true);

        assertThatCode(
                () -> carValidator.validateExistenceOfCarWithId(carId))
                .doesNotThrowAnyException();

        verify(carRepository).existsById(carId);
    }

    @Test
    void isUserCarOwner_ShouldReturnTrue_WhenUserIsTheDriverOfTheCar() {
        Driver driver = DriverTestUtil.buildDefaultDriver();
        Car car = CarTestUtil.buildDefaultCar().toBuilder()
                .driver(driver)
                .build();
        Long carId = car.getId();
        UUID userId = driver.getId();

        when(carRepository.findById(carId))
                .thenReturn(Optional.of(car));

        boolean actual = carValidator.isUserCarOwner(carId, userId);

        assertThat(actual).isTrue();

        verify(carRepository).findById(carId);
    }

    @Test
    void isUserCarOwner_ShouldReturnFalse_WhenUserIsNotTheDriverOfTheCar() {
        Driver driver = DriverTestUtil.buildDefaultDriver();
        Car car = CarTestUtil.buildDefaultCar().toBuilder()
                .driver(driver)
                .build();
        Long carId = car.getId();
        UUID userId = DriverTestUtil.OTHER_ID;

        when(carRepository.findById(carId))
                .thenReturn(Optional.of(car));

        boolean actual = carValidator.isUserCarOwner(carId, userId);

        assertThat(actual).isFalse();

        verify(carRepository).findById(carId);
    }
}
