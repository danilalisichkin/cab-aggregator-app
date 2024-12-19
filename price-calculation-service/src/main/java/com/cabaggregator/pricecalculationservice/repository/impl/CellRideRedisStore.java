package com.cabaggregator.pricecalculationservice.repository.impl;

import com.cabaggregator.pricecalculationservice.repository.CellRideStore;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class CellRideRedisStore implements CellRideStore {

    private final ValueOperations<String, Object> valueOperations;

    private static final String RIDE_KEY_PREFIX = "ride:";
    private static final String CELL_KEY_PREFIX = "cell:";

    /**
     * Checks if the ride has already been counted for the given grid cell.
     * Return true if the ride has been counted for the grid cell, false otherwise.
     */
    @Override
    public boolean exists(String gridCell, ObjectId rideId) {
        String key = generateKey(gridCell, rideId);

        Boolean exists = valueOperations.getOperations().hasKey(key);
        return Boolean.TRUE.equals(exists);
    }

    /**
     * Adds the ride to the grid cell so that it is counted for demand calculation.
     */
    @Override
    public void set(String gridCell, ObjectId rideId) {
        String key = generateKey(gridCell, rideId);
        valueOperations.set(key, rideId.toString(), Duration.ofMinutes(10));
    }

    private String generateKey(String gridCell, Object rideId) {
        return CELL_KEY_PREFIX + gridCell + ":" + RIDE_KEY_PREFIX + rideId;
    }
}

