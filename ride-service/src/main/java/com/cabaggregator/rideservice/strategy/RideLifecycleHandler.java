package com.cabaggregator.rideservice.strategy;

import com.cabaggregator.rideservice.core.enums.RideLifecyclePhase;
import com.cabaggregator.rideservice.entity.Ride;

public interface RideLifecycleHandler {
    void handle(Ride ride);

    RideLifecyclePhase getSupportedPhase();
}
