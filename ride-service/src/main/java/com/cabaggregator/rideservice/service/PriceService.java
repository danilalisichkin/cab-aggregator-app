package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.entity.Ride;

public interface PriceService {
    void calculateBasePrice(Ride ride, RideAddingDto addingDto);

    void recalculatePriceWithDiscount(Ride ride, RideAddingDto addingDto);
}
