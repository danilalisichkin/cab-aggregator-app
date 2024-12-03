package com.cabaggregator.passengerservice.core.mapper;

import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassengerMapper {
    PassengerDto entityToDto(Passenger passenger);

    void updateEntityFromDto(PassengerUpdatingDto passengerDto, @MappingTarget Passenger passenger);

    Passenger dtoToEntity(PassengerAddingDto dto);

    List<PassengerDto> entityListToDtoList(List<Passenger> passengers);

    default Page<PassengerDto> entityPageToDtoPage(Page<Passenger> entityPage) {
        List<PassengerDto> dtoList = entityListToDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
