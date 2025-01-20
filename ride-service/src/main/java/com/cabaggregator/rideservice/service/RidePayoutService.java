package com.cabaggregator.rideservice.service;

import org.bson.types.ObjectId;

public interface RidePayoutService {
    void createPayoutForRide(ObjectId id);
}
