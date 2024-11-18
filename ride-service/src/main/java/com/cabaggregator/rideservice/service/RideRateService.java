package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.core.enums.UserRole;
import org.bson.types.ObjectId;

import java.util.List;

public interface RideRateService {
    RideRateDto getRideRateById(ObjectId id);

    RideRateDto getRideRateByRideId(ObjectId rideId);

    List<RideRateDto> getListOfRideRates(String passengerId, String driverId);

    RideRateDto saveRideRate(ObjectId rideId, UserRole role, Integer rate);
}
