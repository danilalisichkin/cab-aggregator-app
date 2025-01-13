package com.cabaggregator.rideservice.mapper;

import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.core.dto.price.PriceRecalculationDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
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

    PriceCalculationRequest entityToPriceCalculationRequest(Ride ride);

    PriceRecalculationDto entityToPriceRecalculationDto(Ride ride);

    void updateEntityFromOrderDto(RideUpdatingDto dto, @MappingTarget Ride ride);

    List<RideDto> entityListToDtoList(List<Ride> rides);

    default Page<RideDto> entityPageToDtoPage(Page<Ride> entityPage) {
        List<RideDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
