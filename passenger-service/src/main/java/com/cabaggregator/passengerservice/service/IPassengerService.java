package com.cabaggregator.passengerservice.service;

import com.cabaggregator.passengerservice.core.dto.PagedDto;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;

public interface IPassengerService {
    PagedDto<PassengerDto> getPageOfPassengers(int pageNumber, int pageSize, String sortField, String sortOrder);

    PassengerDto getPassengerById(long id);

    PassengerDto savePassenger(PassengerAddingDto passengerDto);

    PassengerDto updatePassenger(long id, PassengerUpdatingDto passengerDto);

    void deletePassengerById(long id);
}
