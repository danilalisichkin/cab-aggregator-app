package com.cabaggregator.passengerservice.service;

import com.cabaggregator.passengerservice.core.dto.PagedDto;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSort;

public interface PassengerService {
    PagedDto<PassengerDto> getPageOfPassengers(Integer offset, Integer limit, PassengerSort sort);

    PassengerDto getPassengerById(String id);

    PassengerDto savePassenger(PassengerAddingDto passengerDto);

    PassengerDto updatePassenger(String id, PassengerUpdatingDto passengerDto);

    PassengerDto updatePassengerRating(String id, Double rating);

    void deletePassengerById(String id);
}
