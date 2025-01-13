package com.cabaggregator.ratingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document("passenger_rate")
public class PassengerRate {
    @Id
    private ObjectId id;

    private ObjectId rideId;

    private UUID passengerId;

    private UUID driverId;

    private Integer rate;
}
