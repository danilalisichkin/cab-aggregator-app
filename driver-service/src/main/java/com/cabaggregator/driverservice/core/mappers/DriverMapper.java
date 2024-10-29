package com.cabaggregator.driverservice.core.mappers;

import com.cabaggregator.driverservice.core.dto.DriverAddingDTO;
import com.cabaggregator.driverservice.core.dto.DriverDTO;
import com.cabaggregator.driverservice.entities.Driver;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverDTO entityToDto(Driver driver);

    Driver dtoToEntity(DriverDTO driverDTO);

    Driver addingDtoToEntity(DriverAddingDTO driverDTO);
}
