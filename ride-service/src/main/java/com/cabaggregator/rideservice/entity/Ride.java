package com.cabaggregator.rideservice.entity;

import com.cabaggregator.rideservice.entity.enums.PaymentMethod;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import com.cabaggregator.rideservice.entity.enums.ServiceCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("rides")
public class Ride {
    @Id
    private ObjectId id;

    private long driverId;

    private long passengerId;

    private ServiceCategory serviceCategory;

    private RideStatus status;

    private PaymentMethod paymentMethod;

    private String pickupAddress;

    private String destinationAddress;

    private double cost;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
