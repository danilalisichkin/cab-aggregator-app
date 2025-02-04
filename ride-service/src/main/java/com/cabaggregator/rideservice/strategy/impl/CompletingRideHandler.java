package com.cabaggregator.rideservice.strategy.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.enums.RideLifecyclePhase;
import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.kafka.producer.RateProducer;
import com.cabaggregator.rideservice.kafka.util.RateBuilder;
import com.cabaggregator.rideservice.service.RidePayoutService;
import com.cabaggregator.rideservice.strategy.RideLifecycleHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CompletingRideHandler implements RideLifecycleHandler {

    private final RidePayoutService ridePayoutService;

    private final RateProducer rateProducer;

    private final RateBuilder rateBuilder;

    @Override
    public void handle(Ride ride) {
        if (!RidePaymentStatus.PAID.equals(ride.getPaymentStatus())
                && !RidePaymentStatus.PAID_IN_CASH.equals(ride.getPaymentStatus())) {
            throw new BadRequestException(ApplicationMessages.CANT_COMPLETE_RIDE_WHEN_IT_NOT_PAID);
        }

        ridePayoutService.createPayoutForRide(ride.getId());
        rateProducer.sendMessage(
                rateBuilder.buildFromRide(ride));

        ride.setEndTime(LocalDateTime.now());
        ride.setStatus(RideStatus.COMPLETED);
    }

    @Override
    public RideLifecyclePhase getSupportedPhase() {
        return RideLifecyclePhase.COMPLETING;
    }
}
