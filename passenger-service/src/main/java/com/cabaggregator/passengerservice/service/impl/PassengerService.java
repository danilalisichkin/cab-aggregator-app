package com.cabaggregator.passengerservice.service.impl;

import com.cabaggregator.passengerservice.core.constant.MessageKeys;
import com.cabaggregator.passengerservice.core.constant.PassengerFieldDefaultValues;
import com.cabaggregator.passengerservice.core.dto.PagedDto;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.mapper.PageMapper;
import com.cabaggregator.passengerservice.core.mapper.PassengerMapper;
import com.cabaggregator.passengerservice.entity.Passenger;
import com.cabaggregator.passengerservice.exception.ResourceNotFoundException;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import com.cabaggregator.passengerservice.service.IPassengerService;
import com.cabaggregator.passengerservice.util.PageRequestBuilder;
import com.cabaggregator.passengerservice.validator.PassengerValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerService implements IPassengerService {

    private final PassengerRepository passengerRepository;

    private final PassengerMapper passengerMapper;

    private final PageMapper pageMapper;

    private final PassengerValidator passengerValidator;

    private final PageRequestBuilder pageRequestBuilder;

    @Override
    public PagedDto<PassengerDto> getPageOfPassengers(int pageNumber, int pageSize, String sortField, String sortOrder) {
        PageRequest request = pageRequestBuilder.buildPageRequest(pageNumber, pageSize, sortField, sortOrder);

        return pageMapper.pageToPagedDto(
                passengerMapper.entityPageToDtoPage(
                        passengerRepository.findAll(request)));
    }

    @Override
    public PassengerDto getPassengerById(long id) {
        return passengerMapper.entityToDto(
                getPassengerEntityById(id));
    }

    @Override
    @Transactional
    public PassengerDto savePassenger(PassengerAddingDto passengerDto) {
        passengerValidator.checkPhoneUniqueness(passengerDto.phoneNumber());
        passengerValidator.checkEmailUniqueness(passengerDto.email());

        Passenger passenger = passengerMapper.addingDtoToEntity(passengerDto);
        passenger.setRating(PassengerFieldDefaultValues.DEFAULT_RATING);

        return passengerMapper.entityToDto(
                passengerRepository.save(passenger));
    }

    @Override
    @Transactional
    public PassengerDto updatePassenger(long id, PassengerUpdatingDto passengerDto) {
        Passenger passengerToUpdate = getPassengerEntityById(id);

        if (!passengerDto.phoneNumber().equals(passengerToUpdate.getPhoneNumber())) {
            passengerValidator.checkPhoneUniqueness(passengerDto.phoneNumber());
        }
        if (!passengerDto.email().equals(passengerToUpdate.getEmail())) {
            passengerValidator.checkEmailUniqueness(passengerDto.email());
        }

        return passengerMapper.entityToDto(
                passengerRepository.save(
                        passengerMapper.updatingDtoToEntity(passengerDto)));
    }

    @Override
    @Transactional
    public void deletePassengerById(long id) {
        passengerValidator.checkExistenceOfPassengerWithId(id);
        passengerRepository.deleteById(id);
    }

    private Passenger getPassengerEntityById(long id) {
        return passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageKeys.ERROR_PASSENGER_WITH_ID_NOT_FOUND,
                        id));
    }
}
