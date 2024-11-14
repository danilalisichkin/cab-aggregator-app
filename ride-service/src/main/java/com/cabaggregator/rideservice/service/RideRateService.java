package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.core.enums.UserRole;
import org.bson.types.ObjectId;

public interface RideRateService {
    RideRateDto getRideRateById(ObjectId id);

    RideRateDto getRideRateByRideId(ObjectId rideId);

    RideRateDto saveRideRate(ObjectId rideId, UserRole role, Integer rate);
}
