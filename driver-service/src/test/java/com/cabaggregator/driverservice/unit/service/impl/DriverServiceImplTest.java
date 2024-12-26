package com.cabaggregator.driverservice.unit.service.impl;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PageDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSortField;
import com.cabaggregator.driverservice.core.mapper.DriverMapper;
import com.cabaggregator.driverservice.core.mapper.PageMapper;
import com.cabaggregator.driverservice.entity.Car;
import com.cabaggregator.driverservice.entity.Driver;
import com.cabaggregator.driverservice.exception.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.repository.CarRepository;
import com.cabaggregator.driverservice.repository.DriverRepository;
import com.cabaggregator.driverservice.service.impl.DriverServiceImpl;
import com.cabaggregator.driverservice.util.CarTestUtil;
import com.cabaggregator.driverservice.util.DriverTestUtil;
import com.cabaggregator.driverservice.util.PageRequestBuilder;
import com.cabaggregator.driverservice.util.PaginationTestUtil;
import com.cabaggregator.driverservice.validator.DriverValidator;
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
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {

    @InjectMocks
    private DriverServiceImpl driverService;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private DriverMapper driverMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private DriverValidator driverValidator;

    @Test
    void getPageOfDrivers_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        DriverSortField sortBy = DriverSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        Driver driver = DriverTestUtil.buildDefaultDriver();
        DriverDto driverDto = DriverTestUtil.buildDriverDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Driver> driverPage = PaginationTestUtil.buildPageFromSingleElement(driver);
        Page<DriverDto> driverDtoPage = PaginationTestUtil.buildPageFromSingleElement(driverDto);
        PageDto<DriverDto> pageDto = PaginationTestUtil.buildPageDto(driverDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(driverRepository.findAll(pageRequest))
                    .thenReturn(driverPage);
            when(driverMapper.entityPageToDtoPage(driverPage))
                    .thenReturn(driverDtoPage);
            when(pageMapper.pageToPageDto(driverDtoPage))
                    .thenReturn(pageDto);

            PageDto<DriverDto> actual = driverService.getPageOfDrivers(offset, limit, sortBy, sortOrder);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(driverRepository).findAll(pageRequest);
            verify(driverMapper).entityPageToDtoPage(driverPage);
            verify(pageMapper).pageToPageDto(driverDtoPage);
        }
    }

    @Test
    void getDriverById_ShouldReturnDriver_WhenDriverFound() {
        Driver driver = DriverTestUtil.buildDefaultDriver();
        DriverDto driverDto = DriverTestUtil.buildDriverDto();

        when(driverRepository.findById(driver.getId()))
                .thenReturn(Optional.of(driver));
        when(driverMapper.entityToDto(driver))
                .thenReturn(driverDto);

        DriverDto actual = driverService.getDriverById(driver.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(driverDto);

        verify(driverRepository).findById(driver.getId());
        verify(driverMapper).entityToDto(driver);
    }

    @Test
    void getDriverById_ShouldThrowResourceNotFoundException_WhenDriverNotFound() {
        Driver driver = DriverTestUtil.buildDefaultDriver();

        when(driverRepository.findById(driver.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> driverService.getDriverById(driver.getId()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(driverRepository).findById(driver.getId());
        verifyNoInteractions(driverMapper);
    }

    @Test
    void saveDriver_ShouldThrowDataUniquenessException_WhenDriverIdNotUnique() {
        DriverAddingDto driverAddingDto = DriverTestUtil.buildDriverAddingDto();

        doThrow(new DataUniquenessConflictException("error"))
                .when(driverValidator).validateIdUniqueness(driverAddingDto.id());

        assertThatThrownBy(
                () -> driverService.saveDriver(driverAddingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(driverValidator).validateIdUniqueness(driverAddingDto.id());
        verifyNoMoreInteractions(driverValidator);
        verifyNoInteractions(driverRepository, driverMapper, pageMapper);
    }

    @Test
    void saveDriver_ShouldThrowDataUniquenessException_WhenDriverPhoneNotUnique() {
        DriverAddingDto driverAddingDto = DriverTestUtil.buildDriverAddingDto();

        doNothing().when(driverValidator).validateIdUniqueness(driverAddingDto.id());
        doThrow(new DataUniquenessConflictException("error"))
                .when(driverValidator).validatePhoneUniqueness(driverAddingDto.phoneNumber());

        assertThatThrownBy(
                () -> driverService.saveDriver(driverAddingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(driverValidator).validateIdUniqueness(driverAddingDto.id());
        verify(driverValidator).validatePhoneUniqueness(driverAddingDto.phoneNumber());
        verifyNoMoreInteractions(driverValidator);
        verifyNoInteractions(driverRepository, driverMapper, pageMapper);
    }

    @Test
    void saveDriver_ShouldThrowDataUniquenessException_WhenDriverEmailNotUnique() {
        DriverAddingDto driverAddingDto = DriverTestUtil.buildDriverAddingDto();

        doNothing().when(driverValidator).validateIdUniqueness(driverAddingDto.id());
        doNothing().when(driverValidator).validatePhoneUniqueness(driverAddingDto.phoneNumber());
        doThrow(new DataUniquenessConflictException("error"))
                .when(driverValidator).validateEmailUniqueness(driverAddingDto.email());

        assertThatThrownBy(
                () -> driverService.saveDriver(driverAddingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(driverValidator).validateIdUniqueness(driverAddingDto.id());
        verify(driverValidator).validatePhoneUniqueness(driverAddingDto.phoneNumber());
        verify(driverValidator).validateEmailUniqueness(driverAddingDto.email());
        verifyNoInteractions(driverRepository, driverMapper, pageMapper);
    }

    @Test
    void saveDriver_ShouldReturnDriver_WhenDriverIsValid() {
        DriverAddingDto driverAddingDto = DriverTestUtil.buildDriverAddingDto();
        Driver driver = DriverTestUtil.buildDefaultDriver();
        DriverDto driverDto = DriverTestUtil.buildDriverDto();

        doNothing().when(driverValidator).validateIdUniqueness(driverAddingDto.id());
        doNothing().when(driverValidator).validatePhoneUniqueness(driverAddingDto.phoneNumber());
        doNothing().when(driverValidator).validateEmailUniqueness(driverAddingDto.email());
        when(driverMapper.dtoToEntity(driverAddingDto))
                .thenReturn(driver);
        when(driverRepository.save(driver))
                .thenReturn(driver);
        when(driverMapper.entityToDto(driver))
                .thenReturn(driverDto);

        DriverDto actual = driverService.saveDriver(driverAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(driverDto);

        verify(driverValidator).validateIdUniqueness(driverAddingDto.id());
        verify(driverValidator).validatePhoneUniqueness(driverAddingDto.phoneNumber());
        verify(driverValidator).validateEmailUniqueness(driverAddingDto.email());
        verify(driverMapper).dtoToEntity(driverAddingDto);
        verify(driverRepository).save(driver);
        verify(driverMapper).entityToDto(driver);
    }

    @Test
    void updateDriver_ShouldReturnDriver_WhenDriverFound() {
        Driver driver = DriverTestUtil.buildDefaultDriver();
        DriverDto driverDto = DriverTestUtil.buildDriverDto();
        DriverUpdatingDto driverUpdatingDto = DriverTestUtil.buildDriverUpdatingDto();

        when(driverRepository.findById(driver.getId()))
                .thenReturn(Optional.of(driver));
        doNothing().when(driverMapper).updateEntityFromDto(driverUpdatingDto, driver);
        doNothing().when(driverValidator).validatePhoneUniqueness(driverUpdatingDto.phoneNumber());
        doNothing().when(driverValidator).validateEmailUniqueness(driverUpdatingDto.email());
        when(driverRepository.save(driver))
                .thenReturn(driver);
        when(driverMapper.entityToDto(driver))
                .thenReturn(driverDto);

        DriverDto result = driverService.updateDriver(driver.getId(), driverUpdatingDto);

        assertThat(result)
                .isNotNull()
                .isEqualTo(driverDto);

        verify(driverRepository).findById(driver.getId());
        verify(driverValidator).validatePhoneUniqueness(driverUpdatingDto.phoneNumber());
        verify(driverValidator).validateEmailUniqueness(driverUpdatingDto.email());
        verify(driverMapper).updateEntityFromDto(driverUpdatingDto, driver);
        verify(driverRepository).save(driver);
        verify(driverMapper).entityToDto(driver);
        verifyNoMoreInteractions(driverValidator);
    }

    @Test
    void updateDriver_ShouldThrowResourceNotFoundException_WhenDriverNotFound() {
        DriverUpdatingDto driverUpdatingDto = DriverTestUtil.buildDriverUpdatingDto();

        when(driverRepository.findById(DriverTestUtil.NOT_EXISTING_ID))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> driverService.updateDriver(DriverTestUtil.NOT_EXISTING_ID, driverUpdatingDto));

        verify(driverRepository).findById(DriverTestUtil.NOT_EXISTING_ID);
        verifyNoMoreInteractions(driverRepository);
        verifyNoInteractions(driverValidator, driverMapper);
    }

    @Test
    void updateDriverRating_ShouldThrowResourceNotFoundException_WhenDriverNotFound() {
        UUID driverId = DriverTestUtil.NOT_EXISTING_ID;
        Double rating = DriverTestUtil.UPDATED_RATING;

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> driverService.updateDriverRating(driverId, rating))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(driverRepository).findById(driverId);
        verifyNoMoreInteractions(driverRepository);
        verifyNoInteractions(driverMapper);
    }

    @Test
    void updateDriverRating_ShouldUpdateDriverRating_WhenDriverFound() {
        Driver driver = DriverTestUtil.buildDefaultDriver();
        UUID driverId = driver.getId();
        Double newRating = DriverTestUtil.UPDATED_RATING;
        DriverDto driverDto = DriverTestUtil.buildDriverDto();

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.of(driver));
        when(driverRepository.save(any(Driver.class)))
                .thenReturn(driver);
        when(driverMapper.entityToDto(any(Driver.class)))
                .thenReturn(driverDto);

        DriverDto actual = driverService.updateDriverRating(driverId, newRating);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(driverDto);

        verify(driverRepository).findById(driverId);
        verify(driverRepository).save(any(Driver.class));
        verify(driverMapper).entityToDto(any(Driver.class));
    }

    @Test
    void updateDriverCarId_ShouldThrowResourceNotFoundException_WhenDriverNotFound() {
        UUID driverId = DriverTestUtil.NOT_EXISTING_ID;
        Long carId = CarTestUtil.CAR_ID;

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> driverService.updateDriverCarId(driverId, carId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(driverRepository).findById(driverId);
        verifyNoMoreInteractions(driverRepository);
        verifyNoInteractions(driverValidator, carRepository, driverMapper);
    }

    @Test
    void updateDriverCarId_ShouldThrowDataUniquenessException_WhenCarIdNotUnique() {
        UUID driverId = DriverTestUtil.DRIVER_ID;
        Long carId = CarTestUtil.CAR_ID;
        Driver driver = DriverTestUtil.buildDefaultDriver();

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.of(driver));
        doThrow(new DataUniquenessConflictException("error"))
                .when(driverValidator).validateDriverCarUniqueness(carId);

        assertThatThrownBy(
                () -> driverService.updateDriverCarId(driverId, carId))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(driverRepository).findById(driverId);
        verify(driverValidator).validateDriverCarUniqueness(carId);
        verifyNoMoreInteractions(driverRepository);
        verifyNoInteractions(carRepository, driverMapper);
    }

    @Test
    void updateDriverCarId_ShouldThrowResourceNotFoundException_WhenCarNotFound() {
        UUID driverId = DriverTestUtil.DRIVER_ID;
        Long carId = CarTestUtil.NOT_EXISTING_CAR_ID;
        Driver driver = DriverTestUtil.buildDefaultDriver();

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.of(driver));
        doNothing().when(driverValidator).validateDriverCarUniqueness(carId);
        when(carRepository.findById(carId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> driverService.updateDriverCarId(driverId, carId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(driverRepository).findById(driverId);
        verify(driverValidator).validateDriverCarUniqueness(carId);
        verify(carRepository).findById(carId);
        verifyNoMoreInteractions(driverRepository);
        verifyNoInteractions(driverMapper);
    }

    @Test
    void updateDriverCarId_ShouldUpdateCarId_WhenDriverAndCarFound() {
        UUID driverId = DriverTestUtil.DRIVER_ID;
        Long carId = CarTestUtil.CAR_ID;
        Driver driver = DriverTestUtil.buildDefaultDriver();
        Car car = CarTestUtil.buildDefaultCar();
        DriverDto driverDto = DriverTestUtil.buildDriverDto();

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.of(driver));
        doNothing().when(driverValidator).validateDriverCarUniqueness(carId);
        when(carRepository.findById(carId))
                .thenReturn(Optional.of(car));
        when(driverRepository.save(any(Driver.class)))
                .thenReturn(driver);
        when(driverMapper.entityToDto(any(Driver.class)))
                .thenReturn(driverDto);

        DriverDto actual = driverService.updateDriverCarId(driverId, carId);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(driverDto);

        verify(driverRepository).findById(driverId);
        verify(driverValidator).validateDriverCarUniqueness(carId);
        verify(carRepository).findById(carId);
        verify(driverRepository).save(any(Driver.class));
        verify(driverMapper).entityToDto(any(Driver.class));
    }

    @Test
    void deleteDriverById_ShouldDeleteDriver_WhenDriverFound() {
        UUID driverId = DriverTestUtil.DRIVER_ID;

        doNothing().when(driverValidator).validateExistenceOfDriverWithId(driverId);
        doNothing().when(driverRepository).deleteById(driverId);

        assertThatCode(
                () -> driverService.deleteDriverById(driverId))
                .doesNotThrowAnyException();

        verify(driverValidator).validateExistenceOfDriverWithId(driverId);
        verify(driverRepository).deleteById(driverId);
    }

    @Test
    void deleteDriverById_ShouldThrowResourceNotFoundException_WhenDriverNotFound() {
        UUID driverId = DriverTestUtil.DRIVER_ID;

        doThrow(new ResourceNotFoundException("error"))
                .when(driverValidator).validateExistenceOfDriverWithId(driverId);

        assertThatThrownBy(
                () -> driverService.deleteDriverById(driverId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(driverValidator).validateExistenceOfDriverWithId(driverId);
        verify(driverRepository, never()).deleteById(driverId);
    }
}
