package com.cabaggregator.ratingservice.unit.service.impl;

import com.cabaggregator.ratingservice.core.dto.driver.DriverRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateSettingDto;
import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.enums.sort.DriverRateSortField;
import com.cabaggregator.ratingservice.core.mapper.DriverRateMapper;
import com.cabaggregator.ratingservice.core.mapper.PageMapper;
import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.exception.BadRequestException;
import com.cabaggregator.ratingservice.exception.DataUniquenessConflictException;
import com.cabaggregator.ratingservice.exception.ResourceNotFoundException;
import com.cabaggregator.ratingservice.repository.DriverRateRepository;
import com.cabaggregator.ratingservice.service.impl.DriverRateServiceImpl;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import com.cabaggregator.ratingservice.util.PageRequestBuilder;
import com.cabaggregator.ratingservice.util.PaginationTestUtil;
import com.cabaggregator.ratingservice.validator.DriverRateValidator;
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
class DriverRateServiceImplTest {

    @InjectMocks
    private DriverRateServiceImpl driverRateService;

    @Mock
    private DriverRateRepository driverRateRepository;

    @Mock
    private DriverRateMapper driverRateMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private DriverRateValidator driverRateValidator;

    @Test
    void getPageOfDriverRates_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0, limit = 10;
        DriverRateSortField sortBy = DriverRateSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        UUID driverId = driverRate.getDriverId();
        DriverRateDto driverRateDto = DriverRateTestUtil.buildDriverRateDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<DriverRate> driverRatePage = PaginationTestUtil.buildPageFromSingleElement(driverRate);
        Page<DriverRateDto> driverRateDtoPage = PaginationTestUtil.buildPageFromSingleElement(driverRateDto);
        PageDto<DriverRateDto> pageDto = PaginationTestUtil.buildPageDto(driverRateDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(driverRateRepository.findAllByDriverId(driverId, pageRequest))
                    .thenReturn(driverRatePage);
            when(driverRateMapper.entityPageToDtoPage(driverRatePage))
                    .thenReturn(driverRateDtoPage);
            when(pageMapper.pageToPageDto(driverRateDtoPage))
                    .thenReturn(pageDto);

            PageDto<DriverRateDto> actual =
                    driverRateService.getPageOfDriverRates(driverId, offset, limit, sortBy, sortOrder);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(driverRateRepository).findAllByDriverId(driverRate.getDriverId(), pageRequest);
            verify(driverRateMapper).entityPageToDtoPage(driverRatePage);
            verify(pageMapper).pageToPageDto(driverRateDtoPage);
        }
    }

    @Test
    void getDriverRate_ShouldReturnDriverRate_WhenDriverRateFound() {
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        UUID driverId = driverRate.getDriverId();
        ObjectId rideId = driverRate.getRideId();
        DriverRateDto driverRateDto = DriverRateTestUtil.buildDriverRateDto();

        when(driverRateRepository.findByDriverIdAndRideId(driverId, rideId))
                .thenReturn(Optional.of(driverRate));
        when(driverRateMapper.entityToDto(driverRate))
                .thenReturn(driverRateDto);

        DriverRateDto actual = driverRateService.getDriverRate(driverId, rideId);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(driverRateDto);

        verify(driverRateRepository).findByDriverIdAndRideId(driverId, rideId);
        verify(driverRateMapper).entityToDto(driverRate);
    }

    @Test
    void getDriverRate_ShouldThrowResourceNotFoundException_WhenDriverRateNotFound() {
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        UUID driverId = driverRate.getDriverId();
        ObjectId rideId = driverRate.getRideId();

        when(driverRateRepository.findByDriverIdAndRideId(driverId, rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> driverRateService.getDriverRate(driverId, rideId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(driverRateRepository).findByDriverIdAndRideId(driverId, rideId);
        verifyNoInteractions(driverRateMapper);
    }

    public DriverRateDto saveDriverRate(DriverRateAddingDto addingDto) {
        driverRateValidator.validateDriverRateUniqueness(addingDto.driverId(), addingDto.rideId());

        DriverRate newDriverRate = driverRateMapper.dtoToEntity(addingDto);

        return driverRateMapper.entityToDto(
                driverRateRepository.save(newDriverRate));
    }

    @Test
    void saveDriverRate_ShouldReturnDriverRate_WhenDriverRateIsValid() {
        DriverRateAddingDto driverRateAddingDto = DriverRateTestUtil.buildDriverRateAddingDto();
        UUID driverId = driverRateAddingDto.driverId();
        ObjectId rideId = driverRateAddingDto.rideId();
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        DriverRateDto driverRateDto = DriverRateTestUtil.buildDriverRateDto();

        doNothing().when(driverRateValidator).validateDriverRateUniqueness(driverId, rideId);
        when(driverRateMapper.dtoToEntity(driverRateAddingDto))
                .thenReturn(driverRate);
        when(driverRateRepository.save(driverRate))
                .thenReturn(driverRate);
        when(driverRateMapper.entityToDto(driverRate))
                .thenReturn(driverRateDto);

        DriverRateDto actual = driverRateService.saveDriverRate(driverRateAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(driverRateDto);

        verify(driverRateValidator).validateDriverRateUniqueness(driverId, rideId);
        verify(driverRateMapper).dtoToEntity(driverRateAddingDto);
        verify(driverRateRepository).save(driverRate);
        verify(driverRateMapper).entityToDto(driverRate);
    }

    @Test
    void saveDriverRate_ShouldThrowDataUniquenessException_WhenDriverRateIsNotUnique() {
        DriverRateAddingDto driverRateAddingDto = DriverRateTestUtil.buildDriverRateAddingDto();
        UUID driverId = driverRateAddingDto.driverId();
        ObjectId rideId = driverRateAddingDto.rideId();

        doThrow(new DataUniquenessConflictException("error"))
                .when(driverRateValidator).validateDriverRateUniqueness(driverId, rideId);

        assertThatThrownBy(
                () -> driverRateService.saveDriverRate(driverRateAddingDto))
                .isInstanceOf(DataUniquenessConflictException.class)
                .hasMessage("error");

        verify(driverRateValidator).validateDriverRateUniqueness(driverId, rideId);
        verifyNoInteractions(driverRateRepository, driverRateMapper);
        verifyNoMoreInteractions(driverRateValidator);
    }

    @Test
    void setDriverRate_ShouldReturnDriverRate_WhenDriverRateFoundAndIsNotSet() {
        DriverRateSettingDto driverRateSettingDto = DriverRateTestUtil.buildDriverRateSettingDto();
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        UUID driverId = driverRate.getDriverId();
        ObjectId rideId = driverRate.getRideId();
        DriverRateDto driverRateDto = DriverRateTestUtil.buildDriverRateDto();

        when(driverRateRepository.findByDriverIdAndRideId(driverId, rideId))
                .thenReturn(Optional.of(driverRate));
        doNothing().when(driverRateValidator).validateDriverRateSetting(driverRate);
        doNothing().when(driverRateMapper).updateEntityFromDto(driverRateSettingDto, driverRate);
        when(driverRateRepository.save(driverRate))
                .thenReturn(driverRate);
        when(driverRateMapper.entityToDto(driverRate))
                .thenReturn(driverRateDto);

        DriverRateDto actual = driverRateService.setDriverRate(driverId, rideId, driverRateSettingDto);

        assertThat(actual).isNotNull().isEqualTo(driverRateDto);

        verify(driverRateRepository).findByDriverIdAndRideId(driverId, rideId);
        verify(driverRateValidator).validateDriverRateSetting(driverRate);
        verify(driverRateMapper).updateEntityFromDto(driverRateSettingDto, driverRate);
        verify(driverRateRepository).save(driverRate);
        verify(driverRateMapper).entityToDto(driverRate);
    }

    @Test
    void setDriverRate_ShouldThrowResourceNotFoundException_WhenDriverRateNotFound() {
        DriverRateSettingDto driverRateSettingDto = DriverRateTestUtil.buildDriverRateSettingDto();
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        UUID driverId = driverRate.getDriverId();
        ObjectId rideId = driverRate.getRideId();

        when(driverRateRepository.findByDriverIdAndRideId(driverId, rideId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> driverRateService.setDriverRate(driverId, rideId, driverRateSettingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(driverRateRepository).findByDriverIdAndRideId(driverId, rideId);
        verifyNoMoreInteractions(driverRateRepository);
        verifyNoInteractions(driverRateValidator, driverRateMapper);
    }

    @Test
    void setDriverRate_ShouldThrowBadRequestException_WhenDriverRateAlreadySet() {
        DriverRateSettingDto driverRateSettingDto = DriverRateTestUtil.buildDriverRateSettingDto();
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        UUID driverId = driverRate.getDriverId();
        ObjectId rideId = driverRate.getRideId();

        when(driverRateRepository.findByDriverIdAndRideId(driverId, rideId))
                .thenReturn(Optional.of(driverRate));
        doThrow(new BadRequestException("error"))
                .when(driverRateValidator).validateDriverRateSetting(driverRate);

        assertThatThrownBy(
                () -> driverRateService.setDriverRate(driverId, rideId, driverRateSettingDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("error");

        verify(driverRateRepository).findByDriverIdAndRideId(driverId, rideId);
        verify(driverRateValidator).validateDriverRateSetting(driverRate);
        verifyNoMoreInteractions(driverRateRepository, driverRateValidator);
        verifyNoInteractions(driverRateMapper);
    }
}
