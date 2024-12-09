package com.cabaggregator.rideservice.entity;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.RideFare;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.util.DurationConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("rides")
public class Ride {
    @Id
    private ObjectId id;

    @Indexed
    private UUID passengerId;

    @Indexed
    private UUID driverId;

    private String promoCode;

    private RideFare fare;

    private RideStatus status;

    private PaymentMethod paymentMethod;

    private Address pickUpAddress;

    private Address dropOffAddress;

    private Long distance;

    private Long price;

    private LocalDateTime orderTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @JsonSerialize(using = DurationConverter.Serializer.class)
    @JsonDeserialize(using = DurationConverter.Deserializer.class)
    private Duration estimatedDuration;
}
