package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.core.dto.promo.PromoCodeAddingDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.entity.PromoCode;
import com.cabaggregator.rideservice.entity.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PromoCodeMapper {
    PromoCodeDto entityToDto(PromoCode entity);

    PromoCode dtoToEntity(PromoCodeDto dto);

    PromoCode addingDtoToEntity(PromoCodeAddingDto dto);

    PromoCode updatingDtoToEntity(PromoCodeUpdatingDto dto);

    List<PromoCodeDto> entityListToDtoList(List<PromoCode> promoCodes);

    default Page<PromoCodeDto> entityPageToDtoPage(Page<PromoCode> entityPage) {
        List<PromoCodeDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
