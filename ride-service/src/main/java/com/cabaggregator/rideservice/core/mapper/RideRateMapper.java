package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.entity.RideRate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideRateMapper {
    RideRateDto entityToDto(RideRate entity);

    RideRate dtoToEntity(RideRateDto dto);

    List<RideRateDto> entityListToDtoList(List<RideRate> rates);

    default Page<RideRateDto> entityPageToDtoPage(Page<RideRate> entityPage) {
        List<RideRateDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
