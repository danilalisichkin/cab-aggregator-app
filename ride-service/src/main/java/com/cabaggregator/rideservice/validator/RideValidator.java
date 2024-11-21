package com.cabaggregator.rideservice.validator;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RideValidator {
    private final RideRepository rideRepository;

    public void validateAddresses(String pickupAddress, String destinationAddress) {
        if (pickupAddress.equals(destinationAddress)) {
            throw new BadRequestException(
                    ApplicationMessages.RIDE_PICKUP_AND_DESTINATION_SAME);
        }
    }

    public void validatePromoCodeApplication(Ride ride) {
        if (ride.getPromoCode() != null && !ride.getPromoCode().isEmpty()) {
            throw new BadRequestException(
                    ApplicationMessages.PROMO_CODE_ALREADY_APPLIED);
        }
    }

    public void validatePassengerParticipation(Ride ride, String passengerId) {
        if (!ride.getPassengerId().equals(passengerId)) {
            throw new ForbiddenException(
                    ApplicationMessages.USER_NOT_RIDE_PARTICIPANT);
        }
    }

    public void validateDriverParticipation(Ride ride, String driverId) {
        if (!ride.getDriverId().equals(driverId)) {
            throw new ForbiddenException(
                    ApplicationMessages.USER_NOT_RIDE_PARTICIPANT);
        }
    }
}
