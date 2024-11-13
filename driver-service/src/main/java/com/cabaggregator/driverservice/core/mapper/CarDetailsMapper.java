package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.entity.CarDetails;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarDetailsMapper {
    CarDetailsDto entityToDto(CarDetails carDetails);

    void updateEntityFromDto(CarDetailsSettingDto carDetailsDto, @MappingTarget CarDetails carDetails);
}
