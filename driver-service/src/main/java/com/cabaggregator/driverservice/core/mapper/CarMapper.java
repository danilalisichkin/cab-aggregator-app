package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {
    CarDto entityToDto(Car car);

    void updateEntityFromDto(CarUpdatingDto carDto, @MappingTarget Car car);

    Car dtoToEntity(CarAddingDto carDto);

    List<CarDto> entityListToDtoList(List<Car> cars);

    default Page<CarDto> entityPageToDtoPage(Page<Car> entityPage) {
        if (entityPage == null) {
            return null;
        }

        List<CarDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
