package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.entity.Ride;

public interface RouteService {
    void setRouteSummary(Ride ride, RideAddingDto addingDto);
}
