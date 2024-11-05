package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DriverMapper {
    DriverDto entityToDto(Driver driver);

    Driver updatingDtoToEntity(DriverUpdatingDto driverDto);

    Driver addingDtoToEntity(DriverAddingDto driverDto);

    List<DriverDto> entityListToDtoList(List<Driver> drivers);

    default Page<DriverDto> entityPageToDtoPage(Page<Driver> entityPage) {
        List<DriverDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
