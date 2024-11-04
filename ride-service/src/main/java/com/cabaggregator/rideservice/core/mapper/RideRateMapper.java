package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.entity.RideRate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideRateMapper {
    RideRateDto entityToDto(RideRate entity);

    RideRate dtoToEntity(RideRateDto dto);
}
