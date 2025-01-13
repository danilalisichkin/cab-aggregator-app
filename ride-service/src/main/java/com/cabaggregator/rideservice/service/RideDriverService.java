package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface RideDriverService {
    PageDto<RideDto> getPageOfDriverRides(
            UUID driverId, Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder,
            RideStatus status);

    RideDto getRide(UUID driverId, ObjectId id);

    RideDto changeRideStatus(UUID driverId, ObjectId id, RideStatus status);
}
