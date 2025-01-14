package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface RidePassengerService {
    PageDto<RideDto> getPageOfPassengerRides(
            UUID passengerId, Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder,
            RideStatus status);

    RideDto getRide(UUID passengerId, ObjectId id);

    RideDto updateRide(UUID passengerId, ObjectId id, RideUpdatingDto updatingDto);

    RideDto changeRideStatus(UUID passengerId, ObjectId id, RideStatus status);
}
