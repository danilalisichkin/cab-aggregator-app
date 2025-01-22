package com.cabaggregator.passengerservice.service.impl;

import com.cabaggregator.passengerservice.core.constant.ApplicationMessages;
import com.cabaggregator.passengerservice.core.dto.page.PageDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSortField;
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
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    private final PassengerMapper passengerMapper;

    private final PageMapper pageMapper;

    private final PassengerValidator passengerValidator;

    @Override
    public PageDto<PassengerDto> getPageOfPassengers(
            Integer offset, Integer limit, PassengerSortField sortField, Sort.Direction sortOrder) {

        PageRequest request = PageRequestBuilder.buildPageRequest(offset, limit, sortField.getValue(), sortOrder);

        return pageMapper.pageToPageDto(
                passengerMapper.entityPageToDtoPage(
                        passengerRepository.findAll(request)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    public PassengerDto getPassengerById(UUID id) {
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

        return passengerMapper.entityToDto(
                passengerRepository.save(passenger));
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    public PassengerDto updatePassenger(UUID id, PassengerUpdatingDto passengerDto) {
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
    public void deletePassengerById(UUID id) {
        passengerValidator.validateExistenceOfPassengerWithId(id);

        passengerRepository.deleteById(id);
    }

    private Passenger getPassengerEntityById(UUID id) {
        return passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ApplicationMessages.ERROR_PASSENGER_WITH_ID_NOT_FOUND,
                        id));
    }
}
