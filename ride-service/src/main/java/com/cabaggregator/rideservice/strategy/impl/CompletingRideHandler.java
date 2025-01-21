package com.cabaggregator.rideservice.strategy.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.enums.RideLifecyclePhase;
import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.BadRequestException;
import com.cabaggregator.rideservice.security.util.SecurityUtil;
import com.cabaggregator.rideservice.service.RidePayoutService;
import com.cabaggregator.rideservice.strategy.RideLifecycleHandler;
import com.cabaggregator.rideservice.validator.RideValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompletingRideHandler implements RideLifecycleHandler {

    private final RideValidator rideValidator;

    private final SecurityUtil securityUtil;

    private final RidePayoutService ridePayoutService;

    @Override
    public void handle(Ride ride) {
        UUID userId = securityUtil.getUserIdFromSecurityContext();
        rideValidator.validateDriverParticipation(ride, userId);

        if (!RidePaymentStatus.PAID.equals(ride.getPaymentStatus())
                && !RidePaymentStatus.PAID_IN_CASH.equals(ride.getPaymentStatus())) {
            throw new BadRequestException(ApplicationMessages.CANT_COMPLETE_RIDE_WHEN_IT_NOT_PAID);
        }

        ridePayoutService.createPayoutForRide(ride.getId());

        ride.setEndTime(LocalDateTime.now());
        ride.setStatus(RideStatus.COMPLETED);
    }

    @Override
    public RideLifecyclePhase getSupportedPhase() {
        return RideLifecyclePhase.COMPLETING;
    }
}
