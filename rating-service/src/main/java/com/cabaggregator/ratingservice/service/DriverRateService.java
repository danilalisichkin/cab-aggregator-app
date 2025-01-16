package com.cabaggregator.ratingservice.service;

import com.cabaggregator.ratingservice.core.dto.driver.DriverRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateSettingDto;
import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.enums.sort.DriverRateSortField;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface DriverRateService {

    Double getDriverRating(UUID driverId);

    PageDto<DriverRateDto> getPageOfDriverRates(
            UUID driverId, Integer offset, Integer limit, DriverRateSortField sortBy, Sort.Direction sortOrder);

    DriverRateDto getDriverRate(UUID driverId, ObjectId rideId);

    DriverRateDto saveDriverRate(DriverRateAddingDto addingDto);

    DriverRateDto setDriverRate(UUID driverId, ObjectId rideId, DriverRateSettingDto settingDto);
}
