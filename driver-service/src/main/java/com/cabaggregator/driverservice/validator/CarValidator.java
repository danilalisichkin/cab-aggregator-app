package com.cabaggregator.driverservice.validator;

import com.cabaggregator.driverservice.core.constant.MessageKeys;
import com.cabaggregator.driverservice.exception.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarValidator {
    private final CarRepository carRepository;

    public void checkLicencePlateUniqueness(String licencePlate) {
        if (carRepository.existsByLicensePlate(licencePlate)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ApplicationMessages.CAR_WITH_LICENCE_PLATE_ALREADY_EXISTS,
                    licencePlate);
        }
    }

    public void checkExistenceOfCarWithId(Long id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageKeys.ApplicationMessages.CAR_WITH_ID_NOT_FOUND,
                    id);
        }
    }
}
