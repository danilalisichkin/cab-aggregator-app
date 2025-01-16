package com.cabaggregator.ratingservice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RateAddingDto {
    private ObjectId rideId;
    private UUID driverId;
    private UUID passengerId;
}
