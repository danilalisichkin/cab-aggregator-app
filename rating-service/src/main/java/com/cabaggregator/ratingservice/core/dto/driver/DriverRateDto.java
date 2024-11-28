package com.cabaggregator.ratingservice.core.dto.driver;

import com.cabaggregator.ratingservice.entity.enums.FeedbackOption;
import org.bson.types.ObjectId;

import java.util.Set;
import java.util.UUID;

public record DriverRateDto(
        ObjectId id,
        ObjectId rideId,
        UUID driverId,
        UUID passengerId,
        Integer rate,
        Set<FeedbackOption> feedbackOptions
) {
}
