package com.cabaggregator.rideservice.kafka.dto;

import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;

import java.util.UUID;

@AllArgsConstructor
public class RateAddingDto {
    private ObjectId rideId;
    private UUID driverId;
    private UUID passengerId;
}
