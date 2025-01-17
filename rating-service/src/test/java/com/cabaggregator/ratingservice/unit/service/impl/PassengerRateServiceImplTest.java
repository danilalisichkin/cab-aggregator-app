package com.cabaggregator.ratingservice.unit.service.impl;

import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateSettingDto;
import com.cabaggregator.ratingservice.core.enums.sort.PassengerRateSortField;
import com.cabaggregator.ratingservice.core.mapper.PageMapper;
import com.cabaggregator.ratingservice.core.mapper.PassengerRateMapper;
import com.cabaggregator.ratingservice.entity.PassengerRate;
import com.cabaggregator.ratingservice.exception.BadRequestException;
import com.cabaggregator.ratingservice.exception.DataUniquenessConflictException;
import com.cabaggregator.ratingservice.exception.ForbiddenException;
import com.cabaggregator.ratingservice.exception.ResourceNotFoundException;
import com.cabaggregator.ratingservice.repository.PassengerRateRepository;
import com.cabaggregator.ratingservice.security.enums.UserRole;
import com.cabaggregator.ratingservice.security.util.SecurityUtil;
import com.cabaggregator.ratingservice.service.impl.PassengerRateServiceImpl;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import com.cabaggregator.ratingservice.util.PageRequestBuilder;
import com.cabaggregator.ratingservice.util.PaginationTestUtil;
import com.cabaggregator.ratingservice.util.PassengerRateTestUtil;
import com.cabaggregator.ratingservice.util.UserRoleExtractor;
import com.cabaggregator.ratingservice.validator.PassengerRateValidator;
import org.bson.types.ObjectId;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PassengerRateServiceImplTest {

    @InjectMocks
    private PassengerRateServiceImpl passengerRateService;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private UserRoleExtractor userRoleExtractor;


    @Mock
    private PassengerRateRepository passengerRateRepository;

    @Mock
    private PassengerRateMapper passengerRateMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private PassengerRateValidator passengerRateValidator;


    @Test
    void getPassengerRating_ShouldReturnAveragePassengerRating_WhenPassengerWasEverRated() {
        UUID passengerId = PassengerRateTestUtil.PASSENGER_ID;
        Double rating = PassengerRateTestUtil.AVERAGE_RATING;

        when(passengerRateRepository.findAverageRateByPassengerId(passengerId))
                .thenReturn(Optional.of(rating));

        Double actual = passengerRateService.getPassengerRating(passengerId);

        assertThat(actual).isEqualTo(rating);

        verify(passengerRateRepository).findAverageRateByPassengerId(passengerId);
    }

    @Test
    void getPassengerRating_ShouldThrowResourceNotFoundException_WhenPassengerWasNeverRated() {
        UUID passengerId = PassengerRateTestUtil.PASSENGER_ID;

        when(passengerRateRepository.findAverageRateByPassengerId(passengerId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> passengerRateService.getPassengerRating(passengerId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(passengerRateRepository).findAverageRateByPassengerId(passengerId);
    }

    @Test
    void getPageOfPassengerRates_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0, limit = 10;
        PassengerRateSortField sortBy = PassengerRateSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        UUID passengerId = passengerRate.getPassengerId();
        PassengerRateDto passengerRateDto = PassengerRateTestUtil.buildPassengerRateDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<PassengerRate> passengerRatePage = PaginationTestUtil.buildPageFromSingleElement(passengerRate);
        Page<PassengerRateDto> passengerRateDtoPage = PaginationTestUtil.buildPageFromSingleElement(passengerRateDto);
        PageDto<PassengerRateDto> pageDto = PaginationTestUtil.buildPageDto(passengerRateDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            when(securityUtil.getUserIdFromSecurityContext())
                    .thenReturn(passengerId);
            when(userRoleExtractor.extractCurrentUserRole())
                    .thenReturn(UserRole.PASSENGER);
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(passengerRateRepository.findAllByPassengerId(passengerId, pageRequest))
                    .thenReturn(passengerRatePage);
            when(passengerRateMapper.entityPageToDtoPage(passengerRatePage))
                    .thenReturn(passengerRateDtoPage);
            when(pageMapper.pageToPageDto(passengerRateDtoPage))
                    .thenReturn(pageDto);

            PageDto<PassengerRateDto> actual =
                    passengerRateService.getPageOfPassengerRates(passengerId, offset, limit, sortBy, sortOrder);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            verify(securityUtil).getUserIdFromSecurityContext();
            verify(userRoleExtractor).extractCurrentUserRole();
            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(passengerRateRepository).findAllByPassengerId(passengerRate.getPassengerId(), pageRequest);
            verify(passengerRateMapper).entityPageToDtoPage(passengerRatePage);
            verify(pageMapper).pageToPageDto(passengerRateDtoPage);
        }
    }

    @Test
    void getPageOfPassengerRates_ShouldThrowForbiddenException_WhenUserIsAnotherPassenger() {
        UUID passengerId = PassengerRateTestUtil.PASSENGER_ID;
        UUID userId = DriverRateTestUtil.OTHER_PASSENGER_ID;
        Integer offset = 0, limit = 10;
        PassengerRateSortField sortBy = PassengerRateSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(UserRole.PASSENGER);

        assertThatThrownBy(
                () -> passengerRateService.getPageOfPassengerRates(passengerId, offset, limit, sortBy, sortOrder))
                .isInstanceOf(ForbiddenException.class);

        verify(securityUtil).getUserIdFromSecurityContext();
        verify(userRoleExtractor).extractCurrentUserRole();
        verifyNoInteractions(passengerRateRepository, passengerRateMapper, pageMapper);
    }

    @Test
    void getPassengerRate_ShouldReturnPassengerRate_WhenPassengerRateFound() {
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        UUID passengerId = passengerRate.getPassengerId();
        ObjectId rideId = passengerRate.getRideId();
        PassengerRateDto passengerRateDto = PassengerRateTestUtil.buildPassengerRateDto();

        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(passengerId);
        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(UserRole.PASSENGER);
        when(passengerRateRepository.findByPassengerIdAndRideId(passengerId, rideId))
                .thenReturn(Optional.of(passengerRate));
        when(passengerRateMapper.entityToDto(passengerRate))
                .thenReturn(passengerRateDto);

        PassengerRateDto actual = passengerRateService.getPassengerRate(passengerId, rideId);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(passengerRateDto);

        verify(securityUtil).getUserIdFromSecurityContext();
        verify(userRoleExtractor).extractCurrentUserRole();
        verify(passengerRateRepository).findByPassengerIdAndRideId(passengerId, rideId);
        verify(passengerRateMapper).entityToDto(passengerRate);
    }

    @Test
    void getPassengerRate_ShouldThrowForbiddenException_WhenUserIsAnotherPassenger() {
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        UUID passengerId = passengerRate.getPassengerId();
        UUID userId = DriverRateTestUtil.OTHER_PASSENGER_ID;
        ObjectId rideId = passengerRate.getRideId();

        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(UserRole.PASSENGER);

        assertThatThrownBy(
                () -> passengerRateService.getPassengerRate(passengerId, rideId))
                .isInstanceOf(ForbiddenException.class);

        verify(securityUtil).getUserIdFromSecurityContext();
        verify(userRoleExtractor).extractCurrentUserRole();
        verifyNoInteractions(passengerRateRepository, passengerRateMapper);
    }

    @Test
    void getPassengerRate_ShouldThrowResourceNotFoundException_WhenPassengerRateNotFound() {
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        UUID passengerId = passengerRate.getPassengerId();
        ObjectId rideId = passengerRate.getRideId();

        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(passengerId);
        when(userRoleExtractor.extractCurrentUserRole())
                .thenReturn(UserRole.DRIVER);
        when(passengerRateRepository.findByPassengerIdAndRideId(passengerId, rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> passengerRateService.getPassengerRate(passengerId, rideId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(securityUtil).getUserIdFromSecurityContext();
        verify(userRoleExtractor).extractCurrentUserRole();
        verify(passengerRateRepository).findByPassengerIdAndRideId(passengerId, rideId);
        verifyNoInteractions(passengerRateMapper);
    }

    @Test
    void savePassengerRate_ShouldReturnPassengerRate_WhenPassengerRateIsValid() {
        PassengerRateAddingDto passengerRateAddingDto = PassengerRateTestUtil.buildPassengerRateAddingDto();
        UUID passengerId = passengerRateAddingDto.passengerId();
        ObjectId rideId = passengerRateAddingDto.rideId();
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        PassengerRateDto passengerRateDto = PassengerRateTestUtil.buildPassengerRateDto();

        doNothing().when(passengerRateValidator).validatePassengerRateUniqueness(passengerId, rideId);
        when(passengerRateMapper.dtoToEntity(passengerRateAddingDto))
                .thenReturn(passengerRate);
        when(passengerRateRepository.save(passengerRate))
                .thenReturn(passengerRate);
        when(passengerRateMapper.entityToDto(passengerRate))
                .thenReturn(passengerRateDto);

        PassengerRateDto actual = passengerRateService.savePassengerRate(passengerRateAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(passengerRateDto);

        verify(passengerRateValidator).validatePassengerRateUniqueness(passengerId, rideId);
        verify(passengerRateMapper).dtoToEntity(passengerRateAddingDto);
        verify(passengerRateRepository).save(passengerRate);
        verify(passengerRateMapper).entityToDto(passengerRate);
    }

    @Test
    void savePassengerRate_ShouldThrowDataUniquenessException_WhenPassengerRateIsNotUnique() {
        PassengerRateAddingDto passengerRateAddingDto = PassengerRateTestUtil.buildPassengerRateAddingDto();
        UUID passengerId = passengerRateAddingDto.passengerId();
        ObjectId rideId = passengerRateAddingDto.rideId();

        doThrow(new DataUniquenessConflictException("error"))
                .when(passengerRateValidator).validatePassengerRateUniqueness(passengerId, rideId);

        assertThatThrownBy(
                () -> passengerRateService.savePassengerRate(passengerRateAddingDto))
                .isInstanceOf(DataUniquenessConflictException.class)
                .hasMessage("error");

        verify(passengerRateValidator).validatePassengerRateUniqueness(passengerId, rideId);
        verifyNoInteractions(passengerRateRepository, passengerRateMapper);
        verifyNoMoreInteractions(passengerRateValidator);
    }

    @Test
    void setPassengerRate_ShouldReturnPassengerRate_WhenPassengerRateFoundAndIsNotSet() {
        PassengerRateSettingDto passengerRateSettingDto = PassengerRateTestUtil.buildPassengerRateSettingDto();
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        UUID passengerId = passengerRate.getPassengerId();
        ObjectId rideId = passengerRate.getRideId();
        PassengerRateDto passengerRateDto = PassengerRateTestUtil.buildPassengerRateDto();

        when(passengerRateRepository.findByPassengerIdAndRideId(passengerId, rideId))
                .thenReturn(Optional.of(passengerRate));
        doNothing().when(passengerRateValidator).validatePassengerRateSetting(passengerRate);
        doNothing().when(passengerRateMapper).updateEntityFromDto(passengerRateSettingDto, passengerRate);
        when(passengerRateRepository.save(passengerRate))
                .thenReturn(passengerRate);
        when(passengerRateMapper.entityToDto(passengerRate))
                .thenReturn(passengerRateDto);

        PassengerRateDto actual = passengerRateService.setPassengerRate(passengerId, rideId, passengerRateSettingDto);

        assertThat(actual).isNotNull().isEqualTo(passengerRateDto);

        verify(passengerRateRepository).findByPassengerIdAndRideId(passengerId, rideId);
        verify(passengerRateValidator).validatePassengerRateSetting(passengerRate);
        verify(passengerRateMapper).updateEntityFromDto(passengerRateSettingDto, passengerRate);
        verify(passengerRateRepository).save(passengerRate);
        verify(passengerRateMapper).entityToDto(passengerRate);
    }

    @Test
    void setPassengerRate_ShouldThrowResourceNotFoundException_WhenPassengerRateNotFound() {
        PassengerRateSettingDto passengerRateSettingDto = PassengerRateTestUtil.buildPassengerRateSettingDto();
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        UUID passengerId = passengerRate.getPassengerId();
        ObjectId rideId = passengerRate.getRideId();

        when(passengerRateRepository.findByPassengerIdAndRideId(passengerId, rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> passengerRateService.setPassengerRate(passengerId, rideId, passengerRateSettingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(passengerRateRepository).findByPassengerIdAndRideId(passengerId, rideId);
        verifyNoMoreInteractions(passengerRateRepository);
        verifyNoInteractions(passengerRateValidator, passengerRateMapper);
    }

    @Test
    void setPassengerRate_ShouldThrowForbiddenException_WhenUserIsNotRideParticipant() {
        PassengerRateSettingDto passengerRateSettingDto = PassengerRateTestUtil.buildPassengerRateSettingDto();
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        UUID passengerId = passengerRate.getPassengerId();
        UUID userId = PassengerRateTestUtil.OTHER_DRIVER_ID;
        ObjectId rideId = passengerRate.getRideId();

        when(passengerRateRepository.findByPassengerIdAndRideId(passengerId, rideId))
                .thenReturn(Optional.of(passengerRate));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doThrow(new ForbiddenException("error"))
                .when(passengerRateValidator).validateDriverParticipation(passengerRate, userId);

        assertThatThrownBy(
                () -> passengerRateService.setPassengerRate(passengerId, rideId, passengerRateSettingDto))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage("error");

        verify(passengerRateRepository).findByPassengerIdAndRideId(passengerId, rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(passengerRateValidator).validateDriverParticipation(passengerRate, userId);
        verifyNoMoreInteractions(passengerRateRepository, passengerRateValidator);
        verifyNoInteractions(passengerRateMapper);
    }

    @Test
    void setPassengerRate_ShouldThrowBadRequestException_WhenPassengerRateAlreadySet() {
        PassengerRateSettingDto passengerRateSettingDto = PassengerRateTestUtil.buildPassengerRateSettingDto();
        PassengerRate passengerRate = PassengerRateTestUtil.buildDefaultPassengerRate();
        UUID passengerId = passengerRate.getPassengerId();
        UUID userId = passengerRate.getDriverId();
        ObjectId rideId = passengerRate.getRideId();

        when(passengerRateRepository.findByPassengerIdAndRideId(passengerId, rideId))
                .thenReturn(Optional.of(passengerRate));
        when(securityUtil.getUserIdFromSecurityContext())
                .thenReturn(userId);
        doNothing().when(passengerRateValidator).validateDriverParticipation(passengerRate, userId);
        doThrow(new BadRequestException("error"))
                .when(passengerRateValidator).validatePassengerRateSetting(passengerRate);

        assertThatThrownBy(
                () -> passengerRateService.setPassengerRate(passengerId, rideId, passengerRateSettingDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("error");

        verify(passengerRateRepository).findByPassengerIdAndRideId(passengerId, rideId);
        verify(securityUtil).getUserIdFromSecurityContext();
        verify(passengerRateValidator).validateDriverParticipation(passengerRate, userId);
        verify(passengerRateValidator).validatePassengerRateSetting(passengerRate);
        verifyNoMoreInteractions(passengerRateRepository, passengerRateValidator);
        verifyNoInteractions(passengerRateMapper);
    }
}
