package com.cabaggregator.ratingservice.kafka.mapper;

import com.cabaggregator.ratingservice.core.dto.driver.DriverRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.kafka.dto.RateAddingDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RateAddingDtoMapper {
    DriverRateAddingDto toDriverAddingDto(RateAddingDto rateAddingDto);

    PassengerRateAddingDto toPassengerAddingDto(RateAddingDto rateAddingDto);
}
