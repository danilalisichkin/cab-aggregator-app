package com.cabaggregator.pricecalculationservice.repository.impl;

import com.cabaggregator.pricecalculationservice.config.redis.RedisKeyConfig;
import com.cabaggregator.pricecalculationservice.repository.CellDemandStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CellDemandRedisStore implements CellDemandStore {

    private final ValueOperations<String, Object> valueOperations;

    private final RedisKeyConfig redisKeyConfig;

    /**
     * Retrieves the current demand for the specified grid cell.
     */
    @Override
    public Optional<Integer> get(String gridCell) {
        String key = generateKey(gridCell);

        return Optional.ofNullable((Integer) valueOperations.get(key));
    }

    /**
     * Sets the demand for the specified grid cell.
     */
    @Override
    public void set(String gridCell, int demand) {
        String key = generateKey(gridCell);
        valueOperations.set(key, demand, Duration.ofMinutes(redisKeyConfig.getTtl()));
    }

    /**
     * Increments the current demand for the specified grid cell by 1.
     */
    @Override
    public void increment(String gridCell) {
        String key = generateKey(gridCell);
        valueOperations.increment(key, 1);
    }

    private String generateKey(String gridCell) {
        return redisKeyConfig.getPrefix().getCellDemand() + gridCell;
    }
}
