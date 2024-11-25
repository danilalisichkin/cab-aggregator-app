package com.cabaggregator.ratingservice.entity;

import com.cabaggregator.ratingservice.entity.enums.FeedbackOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("driver_rate")
public class DriverRate {
    @Id
    private ObjectId id;

    private ObjectId rideId;

    private UUID driverId;

    private UUID passengerId;

    private Integer rate;

    private Set<FeedbackOption> feedbackOptions;
}
