package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.core.dto.promo.PromoCodeAddingDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.promo.RidePromoCodeDto;
import com.cabaggregator.rideservice.entity.PromoCode;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PromoCodeMapper {
    PromoCodeDto entityToDto(PromoCode entity);

    RidePromoCodeDto entityToRideDto(PromoCode entity);

    PromoCode dtoToEntity(PromoCodeAddingDto dto);

    void updateEntityFromDto(PromoCodeUpdatingDto dto, @MappingTarget PromoCode entity);

    List<PromoCodeDto> entityListToDtoList(List<PromoCode> promoCodes);

    default Page<PromoCodeDto> entityPageToDtoPage(Page<PromoCode> entityPage) {
        if (entityPage == null) {
            return null;
        }

        List<PromoCodeDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
