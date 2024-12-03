package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;

public interface RideService {
    RideDto getRideById(String accessToken, ObjectId id);

    PageDto<RideDto> getPageOfRides(
            String accessToken, Integer offset, Integer limit, RideSortField sortField, Sort.Direction sortOrder,
            RideStatus status);

    RideRateDto getRideRate(String accessToken, ObjectId id);

    RideRateDto setRideRate(String accessToken, ObjectId id, Integer rate);

    RideDto orderRide(String accessToken, RideOrderAddingDto addingDto);

    RideDto updateRideOrder(String accessToken, ObjectId id, RideOrderUpdatingDto updatingDto);

    RideDto changeRideStatus(String accessToken, ObjectId id);

    RideDto applyPromoCode(String accessToken, ObjectId id, String code);
}
