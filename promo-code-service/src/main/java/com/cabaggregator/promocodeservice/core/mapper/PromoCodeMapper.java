package com.cabaggregator.promocodeservice.core.mapper;

import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PromoCodeMapper {
    PromoCodeDto entityToDto(PromoCode entity);

    PromoCode dtoToEntity(PromoCodeAddingDto dto);

    void updateEntityFromDto(PromoCodeUpdatingDto dto, @MappingTarget PromoCode entity);

    List<PromoCodeDto> entityListToDtoList(List<PromoCode> enitityList);

    default Page<PromoCodeDto> entityPageToDtoPage(Page<PromoCode> entityPage) {
        List<PromoCodeDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
