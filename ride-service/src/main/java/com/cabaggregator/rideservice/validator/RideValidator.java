package com.cabaggregator.rideservice.validator;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RideValidator {
    private final RideRepository rideRepository;

    /**
     * Validates that pick up and drop off addresses are not same.
     **/
    public void validateAddresses(Address pickUpAddress, Address dropOffAddress) {
        if (pickUpAddress.coordinates().equals(dropOffAddress.coordinates())) {
            throw new BadRequestException(ApplicationMessages.PICKUP_AND_DROPOFF_ADDRESSES_SAME);
        }
    }

    /**
     * Validates that user is driver of ride.
     **/
    public void validateDriverParticipation(Ride ride, UUID driverId) {
        if (!ride.getDriverId().equals(driverId)) {
            throw new ForbiddenException(ApplicationMessages.USER_NOT_RIDE_PARTICIPANT);
        }
    }

    /**
     * Validates that user is passenger of ride.
     **/
    public void validatePassengerParticipation(Ride ride, UUID passengerId) {
        if (!ride.getPassengerId().equals(passengerId)) {
            throw new ForbiddenException(ApplicationMessages.USER_NOT_RIDE_PARTICIPANT);
        }
    }

    /**
     * Validates that passenger is not participating in any ride now.
     **/
    public void validatePassengerFreedom(UUID passengerId) {
        Set<RideStatus> endStatuses = Set.of(RideStatus.CANCELED, RideStatus.COMPLETED);

        boolean isParticipatingInAnyRideNow
                = rideRepository.existsByPassengerIdAndStatusNotIn(passengerId, endStatuses);

        if (isParticipatingInAnyRideNow) {
            throw new BadRequestException(ApplicationMessages.USER_PARTICIPATING_IN_ANOTHER_RIDE_NOW);
        }
    }

    /**
     * Validates that driver is not participating in any ride now.
     **/
    public void validateDriverFreedom(UUID driverId) {
        Set<RideStatus> endStatuses = Set.of(RideStatus.CANCELED, RideStatus.COMPLETED);

        boolean isParticipatingInAnyRideNow
                = rideRepository.existsByDriverIdAndStatusNotIn(driverId, endStatuses);

        if (isParticipatingInAnyRideNow) {
            throw new BadRequestException(ApplicationMessages.USER_PARTICIPATING_IN_ANOTHER_RIDE_NOW);
        }
    }
}
