package com.cabaggregator.passengerservice.unit.service.impl;

import com.cabaggregator.passengerservice.core.dto.page.PageDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSortField;
import com.cabaggregator.passengerservice.core.mapper.PageMapper;
import com.cabaggregator.passengerservice.core.mapper.PassengerMapper;
import com.cabaggregator.passengerservice.entity.Passenger;
import com.cabaggregator.passengerservice.exception.DataUniquenessConflictException;
import com.cabaggregator.passengerservice.exception.ResourceNotFoundException;
import com.cabaggregator.passengerservice.repository.PassengerRepository;
import com.cabaggregator.passengerservice.service.impl.PassengerServiceImpl;
import com.cabaggregator.passengerservice.util.PageRequestBuilder;
import com.cabaggregator.passengerservice.util.PaginationTestUtil;
import com.cabaggregator.passengerservice.util.PassengerTestUtil;
import com.cabaggregator.passengerservice.validator.PassengerValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {
    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private PassengerValidator passengerValidator;

    @Test
    void getPageOfPassengers_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        PassengerSortField sortBy = PassengerSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        Passenger passenger = PassengerTestUtil.getPassengerBuilder().build();
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Passenger> promoCodePage = PaginationTestUtil.buildPageFromSingleElement(passenger);
        Page<PassengerDto> promoCodeDtoPage = PaginationTestUtil.buildPageFromSingleElement(passengerDto);
        PageDto<PassengerDto> pageDto = PaginationTestUtil.buildPageDto(promoCodeDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(passengerRepository.findAll(pageRequest))
                    .thenReturn(promoCodePage);
            when(passengerMapper.entityPageToDtoPage(promoCodePage))
                    .thenReturn(promoCodeDtoPage);
            when(pageMapper.pageToPageDto(promoCodeDtoPage))
                    .thenReturn(pageDto);

            PageDto<PassengerDto> actual = passengerService.getPageOfPassengers(offset, limit, sortBy, sortOrder);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(passengerRepository).findAll(pageRequest);
            verify(passengerMapper).entityPageToDtoPage(promoCodePage);
            verify(pageMapper).pageToPageDto(promoCodeDtoPage);
        }
    }

    @Test
    void getPassengerById_ShouldReturnPassenger_WhenPassengerFound() {
        Passenger passenger = PassengerTestUtil.getPassengerBuilder().build();
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();

        when(passengerRepository.findById(passenger.getId()))
                .thenReturn(Optional.of(passenger));
        when(passengerMapper.entityToDto(passenger))
                .thenReturn(passengerDto);

        PassengerDto actual = passengerService.getPassengerById(passenger.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(passengerDto);

        verify(passengerRepository).findById(passenger.getId());
        verify(passengerMapper).entityToDto(passenger);
    }

    @Test
    void getPassengerById_ShouldThrowResourceNotFoundException_WhenPassengerNotFound() {
        Passenger passenger = PassengerTestUtil.getPassengerBuilder().build();

        when(passengerRepository.findById(passenger.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> passengerService.getPassengerById(passenger.getId()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(passengerRepository).findById(passenger.getId());
        verifyNoInteractions(passengerMapper);
    }

    @Test
    void updatePassenger_ShouldReturnPassenger_WhenPassengerFound() {
        Passenger passenger = PassengerTestUtil.getPassengerBuilder().build();
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();
        PassengerUpdatingDto passengerUpdatingDto = PassengerTestUtil.buildPassengerUpdatingDto();

        when(passengerRepository.findById(passenger.getId()))
                .thenReturn(Optional.of(passenger));
        doNothing().when(passengerMapper).updateEntityFromDto(passengerUpdatingDto, passenger);
        doNothing().when(passengerValidator).validatePhoneUniqueness(passengerUpdatingDto.phoneNumber());
        doNothing().when(passengerValidator).validateEmailUniqueness(passengerUpdatingDto.email());
        when(passengerRepository.save(passenger))
                .thenReturn(passenger);
        when(passengerMapper.entityToDto(passenger))
                .thenReturn(passengerDto);

        PassengerDto result = passengerService.updatePassenger(passenger.getId(), passengerUpdatingDto);

        assertThat(result)
                .isNotNull()
                .isEqualTo(passengerDto);

        verify(passengerRepository).findById(passenger.getId());
        verify(passengerValidator).validatePhoneUniqueness(passengerUpdatingDto.phoneNumber());
        verify(passengerValidator).validateEmailUniqueness(passengerUpdatingDto.email());
        verify(passengerMapper).updateEntityFromDto(passengerUpdatingDto, passenger);
        verify(passengerRepository).save(passenger);
        verify(passengerMapper).entityToDto(passenger);
        verifyNoMoreInteractions(passengerValidator);
    }

    @Test
    void updatePassenger_ShouldThrowResourceNotFoundException_WhenPassengerNotFound() {
        PassengerUpdatingDto passengerUpdatingDto = PassengerTestUtil.buildPassengerUpdatingDto();

        when(passengerRepository.findById(PassengerTestUtil.NOT_EXISTING_ID))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> passengerService.updatePassenger(PassengerTestUtil.NOT_EXISTING_ID, passengerUpdatingDto));

        verify(passengerRepository).findById(PassengerTestUtil.NOT_EXISTING_ID);
        verifyNoMoreInteractions(passengerRepository);
        verifyNoInteractions(passengerValidator, passengerMapper);
    }

    @Test
    void savePassenger_ShouldThrowDataUniquenessException_WhenPassengerIdNotUnique() {
        PassengerAddingDto passengerAddingDto = PassengerTestUtil.buildPassengerAddingDto();

        doThrow(new DataUniquenessConflictException("error"))
                .when(passengerValidator).validateIdUniqueness(passengerAddingDto.id());

        assertThatThrownBy(
                () -> passengerService.savePassenger(passengerAddingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(passengerValidator).validateIdUniqueness(passengerAddingDto.id());
        verifyNoMoreInteractions(passengerValidator);
        verifyNoInteractions(passengerRepository, passengerMapper, pageMapper);
    }

    @Test
    void savePassenger_ShouldThrowDataUniquenessException_WhenPassengerPhoneNotUnique() {
        PassengerAddingDto passengerAddingDto = PassengerTestUtil.buildPassengerAddingDto();

        doNothing().when(passengerValidator).validateIdUniqueness(passengerAddingDto.id());
        doThrow(new DataUniquenessConflictException("error"))
                .when(passengerValidator).validatePhoneUniqueness(passengerAddingDto.phoneNumber());

        assertThatThrownBy(
                () -> passengerService.savePassenger(passengerAddingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(passengerValidator).validateIdUniqueness(passengerAddingDto.id());
        verify(passengerValidator).validatePhoneUniqueness(passengerAddingDto.phoneNumber());
        verifyNoMoreInteractions(passengerValidator);
        verifyNoInteractions(passengerRepository, passengerMapper, pageMapper);
    }

    @Test
    void savePassenger_ShouldThrowDataUniquenessException_WhenPassengerEmailNotUnique() {
        PassengerAddingDto passengerAddingDto = PassengerTestUtil.buildPassengerAddingDto();

        doNothing().when(passengerValidator).validateIdUniqueness(passengerAddingDto.id());
        doNothing().when(passengerValidator).validatePhoneUniqueness(passengerAddingDto.phoneNumber());
        doThrow(new DataUniquenessConflictException("error"))
                .when(passengerValidator).validateEmailUniqueness(passengerAddingDto.email());

        assertThatThrownBy(
                () -> passengerService.savePassenger(passengerAddingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(passengerValidator).validateIdUniqueness(passengerAddingDto.id());
        verify(passengerValidator).validatePhoneUniqueness(passengerAddingDto.phoneNumber());
        verify(passengerValidator).validateEmailUniqueness(passengerAddingDto.email());
        verifyNoInteractions(passengerRepository, passengerMapper, pageMapper);
    }

    @Test
    void savePassenger_ShouldReturnPassenger_WhenPassengerIsValid() {
        PassengerAddingDto passengerAddingDto = PassengerTestUtil.buildPassengerAddingDto();
        Passenger passenger = PassengerTestUtil.getPassengerBuilder().build();
        PassengerDto passengerDto = PassengerTestUtil.buildPassengerDto();

        doNothing().when(passengerValidator).validateIdUniqueness(passengerAddingDto.id());
        doNothing().when(passengerValidator).validatePhoneUniqueness(passengerAddingDto.phoneNumber());
        doNothing().when(passengerValidator).validateEmailUniqueness(passengerAddingDto.email());
        when(passengerMapper.dtoToEntity(passengerAddingDto))
                .thenReturn(passenger);
        when(passengerRepository.save(passenger))
                .thenReturn(passenger);
        when(passengerMapper.entityToDto(passenger))
                .thenReturn(passengerDto);

        PassengerDto actual = passengerService.savePassenger(passengerAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(passengerDto);

        verify(passengerValidator).validateIdUniqueness(passengerAddingDto.id());
        verify(passengerValidator).validatePhoneUniqueness(passengerAddingDto.phoneNumber());
        verify(passengerValidator).validateEmailUniqueness(passengerAddingDto.email());
        verify(passengerMapper).dtoToEntity(passengerAddingDto);
        verify(passengerRepository).save(passenger);
        verify(passengerMapper).entityToDto(passenger);
    }
}
