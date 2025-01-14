package com.cabaggregator.passengerservice.service;

import com.cabaggregator.passengerservice.core.dto.page.PageDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSortField;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public interface PassengerService {
    PageDto<PassengerDto> getPageOfPassengers(
            Integer offset, Integer limit, PassengerSortField sortField, Sort.Direction sortOrder);

    PassengerDto getPassengerById(UUID id);

    PassengerDto savePassenger(PassengerAddingDto passengerDto);

    PassengerDto updatePassenger(UUID id, PassengerUpdatingDto passengerDto);

    void deletePassengerById(UUID id);
}
