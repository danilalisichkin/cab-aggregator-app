package com.cabaggregator.passengerservice.validator;

import com.cabaggregator.passengerservice.core.constant.ApplicationMessages;
import com.cabaggregator.passengerservice.exception.DataUniquenessConflictException;
import com.cabaggregator.passengerservice.exception.ResourceNotFoundException;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerValidator {

    private final PassengerRepository passengerRepository;

    public void validateIdUniqueness(String id) {
        if (passengerRepository.existsById(id)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.PASSENGER_WITH_ID_ALREADY_EXISTS,
                    id);
        }
    }

    public void validatePhoneUniqueness(String phone) {
        if (passengerRepository.existsByPhoneNumber(phone)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.ERROR_PASSENGER_WITH_PHONE_ALREADY_EXISTS,
                    phone);
        }
    }

    public void validateEmailUniqueness(String email) {
        if (passengerRepository.existsByEmail(email)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.ERROR_PASSENGER_WITH_EMAIL_ALREADY_EXISTS,
                    email);
        }
    }

    public void validateExistenceOfPassengerWithId(String id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    ApplicationMessages.ERROR_PASSENGER_WITH_ID_NOT_FOUND,
                    id);
        }
    }
}
