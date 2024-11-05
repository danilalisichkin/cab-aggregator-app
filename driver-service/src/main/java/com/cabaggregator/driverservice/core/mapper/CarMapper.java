package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {
    CarDto entityToDto(Car car);

    Car updatingDtoToEntity(CarUpdatingDto carDto);

    Car addingDtoToEntity(CarAddingDto carDto);

    List<CarDto> entityListToDtoList(List<Car> cars);

    default Page<CarDto> entityPageToDtoPage(Page<Car> entityPage) {
        List<CarDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
