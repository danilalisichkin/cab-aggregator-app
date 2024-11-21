package com.cabaggregator.passengerservice.service;

import com.cabaggregator.passengerservice.core.dto.PagedDto;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSort;

import java.util.UUID;

public interface PassengerService {
    PagedDto<PassengerDto> getPageOfPassengers(Integer offset, Integer limit, PassengerSort sort);

    PassengerDto getPassengerById(UUID id);

    PassengerDto savePassenger(PassengerAddingDto passengerDto);

    PassengerDto updatePassenger(UUID id, PassengerUpdatingDto passengerDto);

    PassengerDto updatePassengerRating(UUID id, Double rating);

    void deletePassengerById(UUID id);
}
