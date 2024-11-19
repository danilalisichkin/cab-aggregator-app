package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverMapper {
    @Mapping(source = "car.id", target = "carId")
    DriverDto entityToDto(Driver driver);

    void updateEntityFromDto(DriverUpdatingDto driverDto, @MappingTarget Driver driver);

    Driver dtoToEntity(DriverAddingDto driverDto);

    List<DriverDto> entityListToDtoList(List<Driver> drivers);

    default Page<DriverDto> entityPageToDtoPage(Page<Driver> entityPage) {
        if (entityPage == null) {
            return null;
        }

        List<DriverDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
