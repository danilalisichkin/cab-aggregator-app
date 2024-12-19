package com.cabaggregator.pricecalculationservice.service;

import org.bson.types.ObjectId;

public interface DemandService {
    int calculateCurrentDemandForRide(String gridCell, ObjectId rideId);
}
