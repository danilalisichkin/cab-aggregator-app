package com.cabaggregator.driverservice.core.mappers;

import com.cabaggregator.driverservice.core.dto.CarAddingDTO;
import com.cabaggregator.driverservice.core.dto.CarDTO;
import com.cabaggregator.driverservice.entities.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDTO entityToDto(Car car);

    Car dtoToEntity(CarDTO carDTO);

    Car addingDtoToEntity(CarAddingDTO carDTO);
}
