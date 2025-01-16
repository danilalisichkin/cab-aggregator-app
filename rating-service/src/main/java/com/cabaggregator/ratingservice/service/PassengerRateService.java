package com.cabaggregator.ratingservice.service;

import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateSettingDto;
import com.cabaggregator.ratingservice.core.enums.sort.PassengerRateSortField;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface PassengerRateService {

    Double getPassengerRating(UUID passengerId);

    PageDto<PassengerRateDto> getPageOfPassengerRates(
            UUID passengerId, Integer offset, Integer limit, PassengerRateSortField sortBy, Sort.Direction sortOrder);

    PassengerRateDto getPassengerRate(UUID passengerId, ObjectId rideId);

    PassengerRateDto savePassengerRate(PassengerRateAddingDto addingDto);

    PassengerRateDto setPassengerRate(UUID passengerId, ObjectId rideId, PassengerRateSettingDto settingDto);
}
