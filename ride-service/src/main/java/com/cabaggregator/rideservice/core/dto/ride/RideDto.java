package com.cabaggregator.rideservice.core.dto.ride;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.RideFare;
import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import org.bson.types.ObjectId;

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

        RidePaymentStatus paymentStatus,

        Address pickUpAddress,

        Address dropOffAddress,

        Long distance,

        Long price,

        LocalDateTime orderTime,

        LocalDateTime startTime,

        LocalDateTime endTime,

        Long estimatedDuration
) {
}
