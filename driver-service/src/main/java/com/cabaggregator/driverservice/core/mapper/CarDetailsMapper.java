package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.entity.CarDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarDetailsMapper {
    CarDetailsDto entityToDto(CarDetails carDetails);

    void updateEntityFromDto(CarDetailsSettingDto carDetailsDto, @MappingTarget CarDetails carDetails);

    CarDetails dtoToEntity(CarDetailsSettingDto carDetailsDto);

    List<CarDetailsDto> entityListToDtoList(List<CarDetails> carDetails);

    default Page<CarDetailsDto> entityPageToDtoPage(Page<CarDetails> entityPage) {
        if (entityPage == null) {
            return null;
        }

        List<CarDetailsDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
