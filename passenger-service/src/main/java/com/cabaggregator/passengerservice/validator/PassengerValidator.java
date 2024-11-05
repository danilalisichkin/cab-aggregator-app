package com.cabaggregator.passengerservice.validator;

import com.cabaggregator.passengerservice.core.constant.MessageKeys;
import com.cabaggregator.passengerservice.exception.DataUniquenessConflictException;
import com.cabaggregator.passengerservice.exception.ResourceNotFoundException;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerValidator {

    private final PassengerRepository passengerRepository;

    public void checkPhoneUniqueness(String phone) {
        if (passengerRepository.existsByPhoneNumber(phone)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ERROR_PASSENGER_WITH_PHONE_ALREADY_EXISTS,
                    phone);
        }
    }

    public void checkEmailUniqueness(String email) {
        if (passengerRepository.existsByEmail(email)) {
            throw new DataUniquenessConflictException(
                    MessageKeys.ERROR_PASSENGER_WITH_EMAIL_ALREADY_EXISTS,
                    email);
        }
    }

    public void checkExistenceOfPassengerWithId(long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    MessageKeys.ERROR_PASSENGER_WITH_ID_NOT_FOUND,
                    id);
        }
    }
}
