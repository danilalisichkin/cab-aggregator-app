package com.cabaggregator.ratingservice.core.mapper;

import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateSettingDto;
import com.cabaggregator.ratingservice.entity.PassengerRate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerRateMapper {
    PassengerRateDto entityToDto(PassengerRate entity);

    void updateEntityFromDto(PassengerRateSettingDto dto, @MappingTarget PassengerRate entity);

    PassengerRate dtoToEntity(PassengerRateAddingDto dto);

    List<PassengerRateDto> entityListToDtoList(List<PassengerRate> entityList);

    default Page<PassengerRateDto> entityPageToDtoPage(Page<PassengerRate> entityPage) {
        List<PassengerRateDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
