package com.cabaggregator.rideservice.service;

import java.time.Duration;

public interface DurationCalculationService {
    Duration calculateDuration(String pickupAddress, String destinationAddress);
}
