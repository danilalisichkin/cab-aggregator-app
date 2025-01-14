package com.cabaggregator.rideservice.strategy;

import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.security.enums.UserRole;

public interface RideStatusChangingHandler {
    void handle(Ride ride, RideStatus newStatus);

    UserRole getSupportedRole();
}
