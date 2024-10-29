package com.cabaggregator.driverservice.core.mappers;

import com.cabaggregator.driverservice.core.dto.CarDetailsDTO;
import com.cabaggregator.driverservice.entities.CarDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarDetailsMapper {
    CarDetailsDTO entityToDto(CarDetails carDetails);

    CarDetails dtoToEntity(CarDetailsDTO carDetailsDTO);

    CarDetails addingDtoToEntity(CarDetailsDTO carDetailsDTO);
}
