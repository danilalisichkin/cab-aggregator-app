package com.cabaggregator.rideservice.strategy.impl;

import com.cabaggregator.rideservice.core.enums.RideLifecyclePhase;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.strategy.RideLifecycleHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RequestingRideHandler implements RideLifecycleHandler {

    @Override
    public void handle(Ride ride) {
        ride.setOrderTime(LocalDateTime.now());
        ride.setStatus(RideStatus.REQUESTED);
    }

    @Override
    public RideLifecyclePhase getSupportedPhase() {
        return RideLifecyclePhase.REQUESTING;
    }
}
