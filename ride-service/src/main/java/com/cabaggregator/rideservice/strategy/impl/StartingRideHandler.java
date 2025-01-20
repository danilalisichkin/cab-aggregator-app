package com.cabaggregator.rideservice.strategy.impl;

import com.cabaggregator.rideservice.core.enums.RideLifecyclePhase;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.kafka.dto.PaymentDefaultRequest;
import com.cabaggregator.rideservice.kafka.enums.PaymentContextType;
import com.cabaggregator.rideservice.kafka.producer.PaymentProducer;
import com.cabaggregator.rideservice.security.util.SecurityUtil;
import com.cabaggregator.rideservice.strategy.RideLifecycleHandler;
import com.cabaggregator.rideservice.validator.RideValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StartingRideHandler implements RideLifecycleHandler {

    private final RideValidator rideValidator;

    private final SecurityUtil securityUtil;

    private final PaymentProducer paymentProducer;

    @Override
    public void handle(Ride ride) {
        UUID userId = securityUtil.getUserIdFromSecurityContext();
        rideValidator.validateDriverParticipation(ride, userId);

        PaymentDefaultRequest defaultRequest = buildPaymentDefaultRequest(ride);
        paymentProducer.sendMessage(defaultRequest);

        ride.setStartTime(LocalDateTime.now());
        ride.setStatus(RideStatus.IN_PROGRESS);
    }

    @Override
    public RideLifecyclePhase getSupportedPhase() {
        return RideLifecyclePhase.STARTING;
    }

    private PaymentDefaultRequest buildPaymentDefaultRequest(Ride ride) {
        return new PaymentDefaultRequest(
                ride.getPassengerId(),
                ride.getPrice(),
                PaymentContextType.RIDE,
                ride.getId().toString());
    }
}
