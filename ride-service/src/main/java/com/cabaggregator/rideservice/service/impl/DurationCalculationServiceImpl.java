package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.service.DurationCalculationService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class DurationCalculationServiceImpl implements DurationCalculationService {
    @Override
    public Duration calculateDuration(String pickupAddress, String destinationAddress) {
        // TODO: implement using Google Cloud maps API

        return null;
    }
}
