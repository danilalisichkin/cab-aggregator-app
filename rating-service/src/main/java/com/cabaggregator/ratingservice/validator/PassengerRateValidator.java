package com.cabaggregator.ratingservice.validator;

import com.cabaggregator.ratingservice.core.constant.ApplicationMessages;
import com.cabaggregator.ratingservice.entity.PassengerRate;
import com.cabaggregator.ratingservice.exception.BadRequestException;
import com.cabaggregator.ratingservice.exception.DataUniquenessConflictException;
import com.cabaggregator.ratingservice.repository.PassengerRateRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PassengerRateValidator {
    public final PassengerRateRepository passengerRateRepository;

    public void validatePassengerRateUniqueness(UUID passengerId, ObjectId rideId) {
        if (passengerRateRepository.existsByPassengerIdAndRideId(passengerId, rideId)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.RIDE_RATE_WITH_RIDE_ID_ALREADY_EXISTS,
                    rideId);
        }
    }

    public void validatePassengerRateSetting(PassengerRate passengerRate) {
        if (passengerRate.getRate() != null) {
            throw new BadRequestException(ApplicationMessages.RIDE_RATE_ALREADY_SET);
        }
    }
}
