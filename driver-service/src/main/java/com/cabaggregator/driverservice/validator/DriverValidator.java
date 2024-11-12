package com.cabaggregator.driverservice.validator;

import com.cabaggregator.driverservice.core.constant.MessageKeys;
import com.cabaggregator.driverservice.exception.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarRepository;
import com.cabaggregator.driverservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverValidator {
    private final DriverRepository driverRepository;

    private final CarRepository carRepository;

    public void checkPhoneUniqueness(String phone) {
        if (driverRepository.existsByPhoneNumber(phone)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ApplicationMessages.DRIVER_WITH_PHONE_ALREADY_EXISTS,
                    phone);
        }
    }

    public void checkEmailUniqueness(String email) {
        if (driverRepository.existsByEmail(email)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ApplicationMessages.DRIVER_WITH_EMAIL_ALREADY_EXISTS,
                    email);
        }
    }

    public void checkExistenceOfDriverWithId(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageKeys.ApplicationMessages.DRIVER_WITH_ID_NOT_FOUND,
                    id);
        }
    }

    public void checkExistenceOfDriverCar(Long carId) {
        if (carId != null && !carRepository.existsById(carId)) {
            throw new ResourceNotFoundException(
                    MessageKeys.ApplicationMessages.CAR_WITH_ID_NOT_FOUND,
                    carId);
        }
    }
}
