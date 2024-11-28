package com.cabaggregator.ratingservice.core.mapper;

import com.cabaggregator.ratingservice.core.dto.driver.DriverRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateSettingDto;
import com.cabaggregator.ratingservice.entity.DriverRate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverRateMapper {
    DriverRateDto entityToDto(DriverRate entity);

    void updateEntityFromDto(DriverRateSettingDto dto, @MappingTarget DriverRate entity);

    DriverRate dtoToEntity(DriverRateAddingDto dto);

    List<DriverRateDto> entityListToDtoList(List<DriverRate> entityList);

    default Page<DriverRateDto> entityPageToDtoPage(Page<DriverRate> entityPage) {
        List<DriverRateDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
