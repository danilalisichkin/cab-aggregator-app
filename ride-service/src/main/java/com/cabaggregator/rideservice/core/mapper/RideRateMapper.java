package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.entity.RideRate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideRateMapper {
    RideRateDto entityToDto(RideRate entity);

    List<RideRateDto> entityListToDtoList(List<RideRate> rates);

    default Page<RideRateDto> entityPageToDtoPage(Page<RideRate> entityPage) {
        if (entityPage == null) {
            return null;
        }

        List<RideRateDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
