package com.cabaggregator.passengerservice.service.impl;

import com.cabaggregator.passengerservice.core.constant.ApplicationMessages;
import com.cabaggregator.passengerservice.core.constant.DefaultValues;
import com.cabaggregator.passengerservice.core.dto.PagedDto;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSort;
import com.cabaggregator.passengerservice.core.mapper.PageMapper;
import com.cabaggregator.passengerservice.core.mapper.PassengerMapper;
import com.cabaggregator.passengerservice.entity.Passenger;
import com.cabaggregator.passengerservice.exception.ResourceNotFoundException;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import com.cabaggregator.passengerservice.service.PassengerService;
import com.cabaggregator.passengerservice.util.PageRequestBuilder;
import com.cabaggregator.passengerservice.validator.PassengerValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    private final PassengerMapper passengerMapper;
    private final PageMapper pageMapper;

    private final PassengerValidator passengerValidator;


    @Override
    public PagedDto<PassengerDto> getPageOfPassengers(Integer offset, Integer limit, PassengerSort sort) {
        PageRequest request = PageRequestBuilder.buildPageRequest(offset, limit, sort.getSortValue());

        return pageMapper.pageToPagedDto(
                passengerMapper.entityPageToDtoPage(
                        passengerRepository.findAll(request)));
    }

    @Override
    public PassengerDto getPassengerById(String id) {
        return passengerMapper.entityToDto(
                getPassengerEntityById(id));
    }

    @Override
    @Transactional
    public PassengerDto savePassenger(PassengerAddingDto passengerDto) {
        passengerValidator.validateIdUniqueness(passengerDto.id());
        passengerValidator.validatePhoneUniqueness(passengerDto.phoneNumber());
        passengerValidator.validateEmailUniqueness(passengerDto.email());

        Passenger passenger = passengerMapper.dtoToEntity(passengerDto);
        passenger.setRating(DefaultValues.DEFAULT_RATING);

        return passengerMapper.entityToDto(
                passengerRepository.save(passenger));
    }

    @Override
    @Transactional
    public PassengerDto updatePassenger(String id, PassengerUpdatingDto passengerDto) {
        Passenger passengerToUpdate = getPassengerEntityById(id);

        if (!passengerDto.phoneNumber().equals(passengerToUpdate.getPhoneNumber())) {
            passengerValidator.validatePhoneUniqueness(passengerDto.phoneNumber());
        }
        if (!passengerDto.email().equals(passengerToUpdate.getEmail())) {
            passengerValidator.validateEmailUniqueness(passengerDto.email());
        }

        passengerMapper.updateEntityFromDto(passengerDto, passengerToUpdate);

        return passengerMapper.entityToDto(
                passengerRepository.save(passengerToUpdate));
    }

    @Override
    @Transactional
    public PassengerDto updatePassengerRating(String id, Double rating) {
        Passenger passengerToUpdate = getPassengerEntityById(id);

        passengerToUpdate.setRating(rating);

        return passengerMapper.entityToDto(
                passengerRepository.save(passengerToUpdate));
    }

    @Override
    @Transactional
    public void deletePassengerById(String id) {
        passengerValidator.validateExistenceOfPassengerWithId(id);

        passengerRepository.deleteById(id);
    }

    private Passenger getPassengerEntityById(String id) {
        return passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.ERROR_PASSENGER_WITH_ID_NOT_FOUND,
                        id));
    }
}
