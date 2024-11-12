package com.cabaggregator.rideservice.core.dto.ride;

import com.cabaggregator.rideservice.entity.enums.PaymentMethod;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import com.cabaggregator.rideservice.entity.enums.ServiceCategory;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public record RideDto(
        ObjectId id,
        Long passengerId,
        Long driverId,
        String promoCode,
        ServiceCategory serviceCategory,
        RideStatus status,
        PaymentMethod paymentMethod,
        String pickupAddress,
        String destinationAddress,
        BigDecimal cost,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
    public BigDecimal cost() {
        return cost.setScale(2, RoundingMode.HALF_UP);
    }
}
