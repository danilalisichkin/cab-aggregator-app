package com.cabaggregator.rideservice.core.dto.ride;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.PaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideFare;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import org.bson.types.ObjectId;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public record RideDto(
        ObjectId id,

        UUID passengerId,

        UUID driverId,

        String promoCode,

        RideFare fare,

        RideStatus status,

        PaymentMethod paymentMethod,

        PaymentStatus paymentStatus,

        Address pickUpAddress,

        Address dropOffAddress,

        Long price,

        LocalDateTime orderTime,

        LocalDateTime startTime,

        LocalDateTime endTime,

        Duration estimatedDuration
) {
}
