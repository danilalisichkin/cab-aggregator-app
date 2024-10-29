package com.cabaggregator.driverservice.validator;

import com.cabaggregator.driverservice.core.constants.MessageKeys;
import com.cabaggregator.driverservice.dao.repository.CarRepository;
import com.cabaggregator.driverservice.entities.Car;
import com.cabaggregator.driverservice.exceptions.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CarValidator {
    private final CarRepository carRepository;

    @Autowired
    public CarValidator(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void checkLicencePlateUniqueness(String licencePlate) {
        if (carRepository.existsByLicensePlate(licencePlate)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ERROR_CAR_WITH_LICENCE_PLATE_ALREADY_EXISTS,
                    licencePlate);
        }
    }

    public void checkExistenceOfOtherCarWithSameLicencePlate(Car car) {
        Optional<Car> carWithSameLicensePlate = carRepository.findByLicensePlate(car.getLicensePlate());

        if (carWithSameLicensePlate.isPresent() && !carWithSameLicensePlate.get().equals(car)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ERROR_CAR_WITH_LICENCE_PLATE_ALREADY_EXISTS,
                    car.getLicensePlate());
        }
    }

    public void checkExistenceOfCarWithId(long id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageKeys.ERROR_CAR_WITH_ID_NOT_FOUND,
                    id);
        }
    }
}
