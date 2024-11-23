package com.cabaggregator.promocodeservice.core.mapper;

import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatDto;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PromoStatMapper {
    @Mapping(source = "promoCode.value", target = "promoCode")
    PromoStatDto entityToDto(PromoStat entity);

    @Mapping(target = "promoCode", ignore = true)
    PromoStat dtoToEntity(PromoStatAddingDto dto);

    List<PromoStatDto> entityListToDtoList(List<PromoStat> entityList);

    default Page<PromoStatDto> entityPageToDtoPage(Page<PromoStat> entityPage) {
        List<PromoStatDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
