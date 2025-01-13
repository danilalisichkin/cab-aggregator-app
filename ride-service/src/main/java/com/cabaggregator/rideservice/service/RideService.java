package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;

public interface RideService {
    PageDto<RideDto> getPageOfRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder, RideStatus status);

    PageDto<RideDto> getPageOfRequestedRides(
            Integer offset, Integer limit, RideSortField sortBy, Sort.Direction sortOrder);

    RideDto getRide(ObjectId id);

    RideDto createRide(RideAddingDto addingDto);

    RideDto acceptRide(ObjectId id);
}
