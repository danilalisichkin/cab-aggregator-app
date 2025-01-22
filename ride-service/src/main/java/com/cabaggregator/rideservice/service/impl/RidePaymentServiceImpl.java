package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import com.cabaggregator.rideservice.core.mapper.RideMapper;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.exception.ResourceNotFoundException;
import com.cabaggregator.rideservice.repository.RideRepository;
import com.cabaggregator.rideservice.service.RidePaymentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RidePaymentServiceImpl implements RidePaymentService {

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;

    /**
     * Updates the ride payment status.
     * Used by Driver to confirm payment if it's set as CASH.
     **/
    @Override
    @Transactional
    @PreAuthorize("@rideValidator.isDriverRideParticipant(#id, authentication.principal)")
    public RideDto changeRidePaymentStatus(UUID driverId, ObjectId id, RidePaymentStatus paymentStatus) {
        Ride rideToUpdate = getRideEntity(id);

        boolean isRequiredPaymentWithCash = PaymentMethod.CASH.equals(rideToUpdate.getPaymentMethod());
        if (isRequiredPaymentWithCash && paymentStatus.equals(RidePaymentStatus.PAID_IN_CASH)) {
            rideToUpdate.setPaymentStatus(paymentStatus);
        } else {
            throw new ForbiddenException(ApplicationMessages.CANT_CHANGE_PAYMENT_STATUS_WHEN_PAID_WITH_CARD);
        }

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    /**
     * Updates the ride payment status.
     * Used by payment service to manage actual status of payment.
     **/
    @Override
    @Transactional
    public RideDto changeRidePaymentStatus(ObjectId id, RidePaymentStatus paymentStatus) {
        Ride rideToUpdate = getRideEntity(id);

        boolean isRequiredPaymentWithCard = PaymentMethod.CARD.equals(rideToUpdate.getPaymentMethod());
        if (isRequiredPaymentWithCard) {
            rideToUpdate.setPaymentStatus(paymentStatus);
        } else {
            throw new ForbiddenException(ApplicationMessages.CANT_CHANGE_PAYMENT_STATUS_WHEN_PAID_WITH_CASH);
        }

        return rideMapper.entityToDto(
                rideRepository.save(rideToUpdate));
    }

    /**
     * Return existing ride or throws exception if it doesn't exist.
     **/
    private Ride getRideEntity(ObjectId id) {
        return rideRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.RIDE_WITH_ID_NOT_FOUND,
                        id.toString()));
    }
}
