package com.cabaggregator.pricecalculationservice.repository;

import org.bson.types.ObjectId;

public interface CellRideStore {
    boolean exists(String gridCell, ObjectId rideId);

    void set(String gridCell, ObjectId rideId);
}
