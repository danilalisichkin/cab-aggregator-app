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

import static com.cabaggregator.rideservice.util.RideLifecyclePhaseChecker.isAccepting;
import static com.cabaggregator.rideservice.util.RideLifecyclePhaseChecker.isCancellingWhenInProgress;
import static com.cabaggregator.rideservice.util.RideLifecyclePhaseChecker.isCompleting;
import static com.cabaggregator.rideservice.util.RideLifecyclePhaseChecker.isStarting;
import static com.cabaggregator.rideservice.util.RideLifecyclePhaseChecker.isWaiting;

/**
 * Handles changing of ride status by Driver.
 * Uses ride lifecycle manager.
 **/
@Component
@RequiredArgsConstructor
public class RideStatusChangingByDriverHandler implements RideStatusChangingHandler {

    private final RideLifecycleManager rideLifecycleManager;

    @Override
    public void handle(Ride ride, RideStatus newStatus) {
        RideStatus oldStatus = ride.getStatus();
        if (isAccepting(oldStatus, newStatus)) {
            rideLifecycleManager.processLifecyclePhaseChange(ride, RideLifecyclePhase.ACCEPTING);
        } else if (isWaiting(oldStatus, newStatus)) {
            rideLifecycleManager.processLifecyclePhaseChange(ride, RideLifecyclePhase.WAITING_FOR_PASSENGER);
        } else if (isStarting(oldStatus, newStatus)) {
            rideLifecycleManager.processLifecyclePhaseChange(ride, RideLifecyclePhase.STARTING);
        } else if (isCancellingWhenInProgress(oldStatus, newStatus)) {
            rideLifecycleManager.processLifecyclePhaseChange(ride, RideLifecyclePhase.CANCELLING_WHEN_IN_PROGRESS);
        } else if (isCompleting(oldStatus, newStatus)) {
            rideLifecycleManager.processLifecyclePhaseChange(ride, RideLifecyclePhase.COMPLETING);
        } else {
            throw new ForbiddenException(ApplicationMessages.STATUS_CHANGING_NOT_ALLOWED);
        }
    }

    @Override
    public UserRole getSupportedRole() {
        return UserRole.DRIVER;
    }
}
