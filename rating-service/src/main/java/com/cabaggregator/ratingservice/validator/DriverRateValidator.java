package com.cabaggregator.ratingservice.validator;

import com.cabaggregator.ratingservice.core.constant.ApplicationMessages;
import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.exception.BadRequestException;
import com.cabaggregator.ratingservice.exception.DataUniquenessConflictException;
import com.cabaggregator.ratingservice.repository.DriverRateRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DriverRateValidator {
    public final DriverRateRepository driverRateRepository;

    public void validateDriverRateUniqueness(UUID driverId, ObjectId rideId) {
        if (driverRateRepository.existsByDriverIdAndRideId(driverId, rideId)) {
            throw new DataUniquenessConflictException(
                    ApplicationMessages.RIDE_RATE_WITH_RIDE_ID_ALREADY_EXISTS,
                    rideId);
        }
    }

    public void validateDriverRateSetting(DriverRate driverRate) {
        if (driverRate.getRate() != null) {
            throw new BadRequestException(ApplicationMessages.RIDE_RATE_ALREADY_SET);
        }
    }

    public boolean isPassengerRideParticipant(ObjectId id, UUID passengerId) {
        Optional<DriverRate> driverRate = driverRateRepository.findByRideId(id);

        return driverRate.isPresent() && driverRate.get().getPassengerId().equals(passengerId);
    }
}
