package com.cabaggregator.driverservice.validator;

import com.cabaggregator.driverservice.core.constants.MessageKeys;
import com.cabaggregator.driverservice.dao.repository.CarDetailsRepository;
import com.cabaggregator.driverservice.dao.repository.CarRepository;
import com.cabaggregator.driverservice.entities.CarDetails;
import com.cabaggregator.driverservice.exceptions.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CarDetailsValidator {
    private final CarDetailsRepository carDetailsRepository;

    private final CarRepository carRepository;

    @Autowired
    public CarDetailsValidator(CarDetailsRepository carDetailsRepository, CarRepository carRepository) {
        this.carDetailsRepository = carDetailsRepository;
        this.carRepository = carRepository;
    }

    public void checkUniquenessOfCarDetailsWithCarId(long carId) {
        if (carDetailsRepository.existsByCarId(carId)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ERROR_CAR_DETAILS_WITH_CAR_ID_ALREADY_EXISTS,
                    carId);
        }
    }

    public void checkExistenceOfCarWithId(long id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageKeys.ERROR_CAR_WITH_ID_NOT_FOUND,
                    id);
        }
    }

    public void checkExistenceOfCarDetailsWithCarId(long id) {
        if (!carDetailsRepository.existsByCarId(id)) {
            throw new ResourceNotFoundException(
                    MessageKeys.ERROR_CAR_DETAILS_WITH_CAR_ID_NOT_FOUND,
                    id);
        }
    }
}
