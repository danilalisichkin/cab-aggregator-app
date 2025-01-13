package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.PaymentStatus;
import org.bson.types.ObjectId;

public interface RidePaymentService {
    RideDto changeRidePaymentStatus(ObjectId id, PaymentStatus paymentStatus);
}
