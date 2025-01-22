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
public class CancellingRideWhenInProgressHandler implements RideLifecycleHandler {

    @Override
    public void handle(Ride ride) {
        ride.setEndTime(LocalDateTime.now());
        ride.setStatus(RideStatus.CANCELED);
    }

    @Override
    public RideLifecyclePhase getSupportedPhase() {
        return RideLifecyclePhase.CANCELLING_WHEN_IN_PROGRESS;
    }
}
