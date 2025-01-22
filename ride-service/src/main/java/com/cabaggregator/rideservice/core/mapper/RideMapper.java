package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.client.dto.PriceRecalculationDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideMapper {
    RideDto entityToDto(Ride ride);

    Ride dtoToEntity(RideAddingDto dto);

    @Mapping(target = "rideId", source = "id")
    @Mapping(target = "pickUpCoordinates", source = "pickUpAddress.coordinates")
    @Mapping(target = "duration", source = "estimatedDuration")
    PriceCalculationRequest entityToPriceCalculationRequest(Ride ride);

    PriceRecalculationDto entityToPriceRecalculationDto(Ride ride);

    void updateEntityFromDto(RideUpdatingDto dto, @MappingTarget Ride ride);

    List<RideDto> entityListToDtoList(List<Ride> rides);

    default Page<RideDto> entityPageToDtoPage(Page<Ride> entityPage) {
        List<RideDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
