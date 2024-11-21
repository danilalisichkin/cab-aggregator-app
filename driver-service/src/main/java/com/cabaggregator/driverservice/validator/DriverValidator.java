package com.cabaggregator.driverservice.validator;

import com.cabaggregator.driverservice.core.constant.ApplicationMessages;
import com.cabaggregator.driverservice.exception.BadRequestException;
import com.cabaggregator.driverservice.exception.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DriverValidator {
    private final DriverRepository driverRepository;

    public void validateIdUniqueness(UUID id) {
        if (driverRepository.existsById(id)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.DRIVER_WITH_ID_ALREADY_EXISTS,
                    id);
        }
    }

    public void validatePhoneUniqueness(String phone) {
        if (driverRepository.existsByPhoneNumber(phone)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.DRIVER_WITH_PHONE_ALREADY_EXISTS,
                    phone);
        }
    }

    public void validateEmailUniqueness(String email) {
        if (driverRepository.existsByEmail(email)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.DRIVER_WITH_EMAIL_ALREADY_EXISTS,
                    email);
        }
    }

    public void validateDriverCarUniqueness(Long carId) {
        if (driverRepository.existsByCarId(carId)) {
            throw new BadRequestException(
                    ApplicationMessages.CAR_WITH_ID_ALREADY_USED,
                    carId);
        }
    }

    public void validateExistenceOfDriverWithId(UUID id) {
        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    ApplicationMessages.DRIVER_WITH_ID_NOT_FOUND,
                    id);
        }
    }
}
