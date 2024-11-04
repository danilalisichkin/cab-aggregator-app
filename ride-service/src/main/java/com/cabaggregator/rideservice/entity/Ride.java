package com.cabaggregator.rideservice.entity;

import com.cabaggregator.rideservice.entity.conveter.PaymentMethodConverter;
import com.cabaggregator.rideservice.entity.conveter.RideStatusConverter;
import com.cabaggregator.rideservice.entity.conveter.ServiceCategoryConverter;
import com.cabaggregator.rideservice.entity.enums.PaymentMethod;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import com.cabaggregator.rideservice.entity.enums.ServiceCategory;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    private Long driverId;

    private long passengerId;

    private ObjectId promoCodeId;

    @JsonSerialize(using = ServiceCategoryConverter.Serializer.class)
    @JsonDeserialize(using = ServiceCategoryConverter.Deserializer.class)
    private ServiceCategory serviceCategory;

    @JsonSerialize(using = RideStatusConverter.Serializer.class)
    @JsonDeserialize(using = RideStatusConverter.Deserializer.class)
    private RideStatus status;

    @JsonSerialize(using = PaymentMethodConverter.Serializer.class)
    @JsonDeserialize(using = PaymentMethodConverter.Deserializer.class)
    private PaymentMethod paymentMethod;

    private String pickupAddress;

    private String destinationAddress;

    private double cost;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
