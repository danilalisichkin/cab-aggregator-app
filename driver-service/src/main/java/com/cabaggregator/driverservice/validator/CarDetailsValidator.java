package com.cabaggregator.driverservice.validator;

import com.cabaggregator.driverservice.core.constant.MessageKeys;
import com.cabaggregator.driverservice.exception.BadRequestException;
import com.cabaggregator.driverservice.exception.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarDetailsRepository;
import com.cabaggregator.driverservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CarDetailsValidator {
    private final CarDetailsRepository carDetailsRepository;

    private final CarRepository carRepository;

    public void checkReleaseDate(LocalDate releaseDate) {
        if (releaseDate.isAfter(LocalDate.now())) {
            throw new BadRequestException(
                    MessageKeys.ApplicationMessages.CAR_RELEASE_DATE_IS_AFTER_PRESENT,
                    null);
        }
    }

    public void checkUniquenessOfCarDetailsWithCarId(long carId) {
        if (carDetailsRepository.existsByCarId(carId)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ApplicationMessages.CAR_DETAILS_WITH_CAR_ID_ALREADY_EXISTS,
                    carId);
        }
    }

    public void checkExistenceOfCarWithId(long id) {
        if (!carRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageKeys.ApplicationMessages.CAR_WITH_ID_NOT_FOUND,
                    id);
        }
    }
}
