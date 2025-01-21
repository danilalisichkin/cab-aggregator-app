package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import org.bson.types.ObjectId;

import java.util.UUID;

public interface RidePaymentService {
    RideDto changeRidePaymentStatus(UUID driverId, ObjectId id, RidePaymentStatus paymentStatus);

    RideDto changeRidePaymentStatus(ObjectId id, RidePaymentStatus paymentStatus);
}
