package com.cabaggregator.pricecalculationservice.repository.impl;

import com.cabaggregator.pricecalculationservice.config.redis.RedisKeyConfig;
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

    private final RedisKeyConfig redisKeyConfig;

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
        valueOperations.set(key, rideId.toString(), Duration.ofMinutes(redisKeyConfig.getTtl()));
    }

    private String generateKey(String gridCell, Object rideId) {
        return redisKeyConfig.getPrefix().getCell() + gridCell + ":" + redisKeyConfig.getPrefix().getRide() + rideId;
    }
}

