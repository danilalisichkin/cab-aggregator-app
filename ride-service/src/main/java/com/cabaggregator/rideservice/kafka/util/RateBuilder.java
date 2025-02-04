package com.cabaggregator.rideservice.kafka.util;

import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.kafka.dto.RateAddingDto;
import org.springframework.stereotype.Component;

@Component
public class RateBuilder {
    public RateAddingDto buildFromRide(Ride ride) {
        return new RateAddingDto(
                ride.getId(),
                ride.getDriverId(),
                ride.getPassengerId());
    }
}
