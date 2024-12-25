package com.cabaggregator.rideservice.mapper;

import com.cabaggregator.rideservice.core.RideAddingDto;
import com.cabaggregator.rideservice.core.RideDto;
import com.cabaggregator.rideservice.core.RideUpdatingDto;
import com.cabaggregator.rideservice.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideMapper {
    RideDto entityToDto(Ride ride);

    Ride dtoToEntity(RideAddingDto dto);

    void updateEntityFromOrderDto(RideUpdatingDto dto, @MappingTarget Ride ride);

    List<RideDto> entityListToDtoList(List<Ride> rides);

    default Page<RideDto> entityPageToDtoPage(Page<Ride> entityPage) {
        List<RideDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}