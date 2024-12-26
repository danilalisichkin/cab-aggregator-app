package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.enums.PaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface RideService {
    PageDto<RideDto> getPageOfRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder, RideStatus status);

    PageDto<RideDto> getPageOfAvailableRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder);

    PageDto<RideDto> getPageOfDriverRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder, RideStatus status, UUID driverId);

    PageDto<RideDto> getPageOfPassengerRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder, RideStatus status, UUID passengerId);

    RideDto getRide(ObjectId id);

    RideDto createRide(RideAddingDto addingDto);

    RideDto updateRide(ObjectId id, RideUpdatingDto updatingDto);

    RideDto changeRideStatus(ObjectId id, RideStatus status);

    RideDto changeRidePaymentStatus(ObjectId id, PaymentStatus paymentStatus);
}
