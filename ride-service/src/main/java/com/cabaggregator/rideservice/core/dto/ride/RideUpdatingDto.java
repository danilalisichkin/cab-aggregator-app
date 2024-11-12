package com.cabaggregator.rideservice.core.dto.ride;

import com.cabaggregator.rideservice.entity.enums.PaymentMethod;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import com.cabaggregator.rideservice.entity.enums.ServiceCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public record RideUpdatingDto(
        @NotNull
        Long passengerId,

        @NotNull
        @Size(min = 2, max = 50)
        String promoCode,

        @NotNull
        ServiceCategory serviceCategory,

        @NotNull
        RideStatus status,

        @NotNull
        PaymentMethod paymentMethod,

        @NotNull
        @Size(min = 3, max = 100)
        String pickupAddress,

        @NotNull
        @Size(min = 3, max = 100)
        String destinationAddress,

        @NotNull
        @Positive
        BigDecimal cost,

        @NotNull
        LocalDateTime startTime,

        @NotNull
        LocalDateTime endTime
) {
    public BigDecimal cost() {
        return cost.setScale(2, RoundingMode.HALF_UP);
    }
}
