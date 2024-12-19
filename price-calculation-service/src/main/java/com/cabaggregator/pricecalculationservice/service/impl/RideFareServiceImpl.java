package com.cabaggregator.pricecalculationservice.service.impl;

import com.cabaggregator.pricecalculationservice.entity.RideFare;
import com.cabaggregator.pricecalculationservice.exception.InternalErrorException;
import com.cabaggregator.pricecalculationservice.repository.RideFareRepository;
import com.cabaggregator.pricecalculationservice.service.RideFareService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareServiceImpl implements RideFareService {

    private final RideFareRepository rideFareRepository;

    /**
     * Retrieves ride fare price policy.
     * Result is cached.
     **/
    @Override
    @Cacheable(value = "fareCache", key = "#fare", unless = "#result == null")
    public RideFare getRideFare(String fare) {
        return rideFareRepository
                .findById(fare)
                .orElseThrow(() -> new InternalErrorException(
                        String.format("no price policy found for fare = %s", fare)));
    }
}
