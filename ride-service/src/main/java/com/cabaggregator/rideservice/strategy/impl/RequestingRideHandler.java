package com.cabaggregator.rideservice.strategy.impl;

import com.cabaggregator.rideservice.core.enums.RideLifecyclePhase;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.security.util.SecurityUtil;
import com.cabaggregator.rideservice.strategy.RideLifecycleHandler;
import com.cabaggregator.rideservice.validator.RideValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RequestingRideHandler implements RideLifecycleHandler {

    private final RideValidator rideValidator;

    private final SecurityUtil securityUtil;

    @Override
    public void handle(Ride ride) {
        UUID userId = securityUtil.getUserIdFromSecurityContext();
        rideValidator.validatePassengerParticipation(ride, userId);

        ride.setOrderTime(LocalDateTime.now());
        ride.setStatus(RideStatus.REQUESTED);
    }

    @Override
    public RideLifecyclePhase getSupportedPhase() {
        return RideLifecyclePhase.REQUESTING;
    }
}
