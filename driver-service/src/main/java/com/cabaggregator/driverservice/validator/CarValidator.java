package com.cabaggregator.driverservice.validator;

import com.cabaggregator.driverservice.core.constant.ApplicationMessages;
import com.cabaggregator.driverservice.entity.Car;
import com.cabaggregator.driverservice.exception.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CarValidator {
    private final CarRepository carRepository;

    public void validateLicencePlateUniqueness(String licencePlate) {
        if (carRepository.existsByLicensePlate(licencePlate)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.CAR_WITH_LICENCE_PLATE_ALREADY_EXISTS,
                    licencePlate);
        }
    }

    public void validateExistenceOfCarWithId(Long id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    ApplicationMessages.CAR_WITH_ID_NOT_FOUND,
                    id);
        }
    }

    public boolean isUserCarOwner(Long id, UUID userId) {
        Optional<Car> car = carRepository.findById(id);

        return car.isPresent() && car.get().getDriver().getId().equals(userId);
    }
}
