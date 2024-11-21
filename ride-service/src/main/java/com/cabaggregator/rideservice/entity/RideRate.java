package com.cabaggregator.rideservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("ride_rates")
public class RideRate {
    @Id
    private ObjectId id;

    @Indexed(unique=true)
    private ObjectId rideId;

    private Integer passengerRate;

    private Integer driverRate;
}
