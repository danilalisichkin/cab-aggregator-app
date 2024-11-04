package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingAddingDto;
import com.cabaggregator.rideservice.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideMapper {
    @Mapping(source = "id", target = "rideId")
    @Mapping(target = "promoCode", ignore = true)
    @Mapping(source = "serviceCategory.value", target = "serviceCategory")
    @Mapping(source = "status.value", target = "status")
    @Mapping(source = "paymentMethod.value", target = "paymentMethod")
    RideDto entityToDto(Ride ride);

    @Mapping(source = "rideId", target = "id")
    @Mapping(target = "promoCodeId", ignore = true)
    Ride dtoToEntity(RideDto dto);

    @Mapping(target = "promoCodeId", ignore = true)
    Ride rideBookingAddingDtoToEntity(RideBookingAddingDto dto);

    List<RideDto> entityListToDtoList(List<Ride> rides);

    default Page<RideDto> entityPageToDtoPage(Page<Ride> entityPage) {
        List<RideDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
