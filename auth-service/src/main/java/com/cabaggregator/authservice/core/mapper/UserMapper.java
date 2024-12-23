package com.cabaggregator.authservice.core.mapper;

import com.cabaggregator.authservice.core.dto.UserRegisterDto;
import com.cabaggregator.authservice.kafka.dto.DriverAddingDto;
import com.cabaggregator.authservice.kafka.dto.PassengerAddingDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    DriverAddingDto userToDriver(UserRegisterDto userRegisterDto);

    PassengerAddingDto userToPassenger(UserRegisterDto userRegisterDto);
}
