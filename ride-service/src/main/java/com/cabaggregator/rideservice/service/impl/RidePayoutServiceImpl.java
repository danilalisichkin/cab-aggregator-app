package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.config.PayoutPolicyConfig;
import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.constant.OperationTranscriptTemplates;
import com.cabaggregator.rideservice.core.enums.RideFare;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.kafka.dto.BalanceOperationRequest;
import com.cabaggregator.rideservice.kafka.producer.PayoutProducer;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.service.RidePayoutService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RidePayoutServiceImpl implements RidePayoutService {

    private final RideRepository rideRepository;

    private final PayoutPolicyConfig payoutPolicyConfig;

    private final PayoutProducer payoutProducer;

    /**
     * Creates payout to Driver's account for completed ride.
     **/
    @Override
    @Transactional
    public void createPayoutForRide(ObjectId id) {
        Ride ride = getRideEntity(id);

        BalanceOperationRequest operationRequest = buildOperationRequest(ride);

        payoutProducer.sendMessage(operationRequest);
    }

    /**
     * Builds balance operation request (payout request) for requested ride.
     **/
    private BalanceOperationRequest buildOperationRequest(Ride ride) {
        UUID payoutAccountId = ride.getDriverId();
        Long payoutAmount = calculatePayoutAmount(ride);
        String operationTranscript = buildOperationTranscript(ride);

        return new BalanceOperationRequest(payoutAccountId, payoutAmount, operationTranscript);
    }

    /**
     * Calculates payout amount basing on ride fare and payout policy.
     **/
    private Long calculatePayoutAmount(Ride ride) {
        PayoutPolicyConfig.Policy policy = getFarePayoutPolicy(ride.getFare());

        return (ride.getPrice() * policy.getPercentage()) / 100;
    }

    /**
     * Returns payout policy for requested ride fare.
     **/
    private PayoutPolicyConfig.Policy getFarePayoutPolicy(RideFare fare) {
        return switch (fare) {
            case ECONOMY -> payoutPolicyConfig.getEconomy();
            case COMFORT -> payoutPolicyConfig.getComfort();
            case BUSINESS -> payoutPolicyConfig.getBusiness();
        };
    }

    /**
     * Returns transcription for payout operation.
     **/
    private String buildOperationTranscript(Ride ride) {
        return String.format(OperationTranscriptTemplates.RIDE_PAYOUT, ride.getId(), ride.getOrderTime());
    }

    /**
     * Returns existing ride or throws exception if it doesn't exist.
     **/
    private Ride getRideEntity(ObjectId id) {
        return rideRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_WITH_ID_NOT_FOUND,
                        id.toString()));
    }
}
