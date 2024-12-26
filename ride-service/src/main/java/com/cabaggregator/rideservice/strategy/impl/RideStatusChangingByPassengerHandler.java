package com.cabaggregator.rideservice.strategy.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.enums.RideLifecyclePhase;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.security.enums.UserRole;
import com.cabaggregator.rideservice.strategy.RideStatusChangingHandler;
import com.cabaggregator.rideservice.strategy.manager.RideLifecycleManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.cabaggregator.rideservice.util.RideLifecyclePhaseChecker.isCancellingBeforeStart;
import static com.cabaggregator.rideservice.util.RideLifecyclePhaseChecker.isRequesting;

/**
 * Handles changing of ride status by Passenger.
 * Uses ride lifecycle manager.
 **/
@Component
@RequiredArgsConstructor
public class RideStatusChangingByPassengerHandler implements RideStatusChangingHandler {

    private final RideLifecycleManager rideLifecycleManager;

    @Override
    public void handle(Ride ride, RideStatus newStatus) {
        RideStatus oldStatus = ride.getStatus();
        if (isRequesting(oldStatus, newStatus)) {
            rideLifecycleManager.processLifecyclePhaseChange(ride, RideLifecyclePhase.REQUESTING);
        } else if (isCancellingBeforeStart(oldStatus, newStatus)) {
            rideLifecycleManager.processLifecyclePhaseChange(ride, RideLifecyclePhase.CANCELLING_BEFORE_START);
        } else {
            throw new ForbiddenException(ApplicationMessages.STATUS_CHANGING_NOT_ALLOWED);
        }
    }

    @Override
    public UserRole getSupportedRole() {
        return UserRole.PASSENGER;
    }
}
