package com.cabaggregator.driverservice.unit.service.impl;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsDto;
import com.cabaggregator.driverservice.core.dto.car.details.CarDetailsSettingDto;
import com.cabaggregator.driverservice.core.dto.page.PageDto;
import com.cabaggregator.driverservice.core.enums.sort.CarSortField;
import com.cabaggregator.driverservice.core.mapper.CarDetailsMapper;
import com.cabaggregator.driverservice.core.mapper.CarMapper;
import com.cabaggregator.driverservice.core.mapper.PageMapper;
import com.cabaggregator.driverservice.entity.Car;
import com.cabaggregator.driverservice.exception.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exception.ResourceNotFoundException;
import com.cabaggregator.driverservice.exception.ValidationErrorException;
import com.cabaggregator.driverservice.repository.CarRepository;
import com.cabaggregator.driverservice.service.CarDetailsService;
import com.cabaggregator.driverservice.service.impl.CarServiceImpl;
import com.cabaggregator.driverservice.util.CarDetailsTestUtil;
import com.cabaggregator.driverservice.util.CarTestUtil;
import com.cabaggregator.driverservice.util.PageRequestBuilder;
import com.cabaggregator.driverservice.util.PaginationTestUtil;
import com.cabaggregator.driverservice.validator.CarDetailsValidator;
import com.cabaggregator.driverservice.validator.CarValidator;
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
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarDetailsService carDetailsService;

    @Mock
    private CarMapper carMapper;

    @Mock
    private CarDetailsMapper carDetailsMapper;

    @Mock
    private PageMapper pageMapper;

    @Mock
    private CarValidator carValidator;

    @Mock
    private CarDetailsValidator carDetailsValidator;

    @Test
    void getPageOfCars_ShouldReturnPageDto_WhenCalledWithValidParameters() {
        Integer offset = 0;
        Integer limit = 10;
        CarSortField sortBy = CarSortField.ID;
        Sort.Direction sortOrder = Sort.Direction.ASC;

        Car car = CarTestUtil.buildDefaultCar();
        CarDto carDto = CarTestUtil.buildCarDto();

        PageRequest pageRequest = PaginationTestUtil.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder);
        Page<Car> carPage = PaginationTestUtil.buildPageFromSingleElement(car);
        Page<CarDto> carDtoPage = PaginationTestUtil.buildPageFromSingleElement(carDto);
        PageDto<CarDto> pageDto = PaginationTestUtil.buildPageDto(carDtoPage);

        try (MockedStatic<PageRequestBuilder> mockedStatic = mockStatic(PageRequestBuilder.class)) {
            mockedStatic.when(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder))
                    .thenReturn(pageRequest);
            when(carRepository.findAll(pageRequest))
                    .thenReturn(carPage);
            when(carMapper.entityPageToDtoPage(carPage))
                    .thenReturn(carDtoPage);
            when(pageMapper.pageToPageDto(carDtoPage))
                    .thenReturn(pageDto);

            PageDto<CarDto> actual = carService.getPageOfCars(offset, limit, sortBy, sortOrder);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(pageDto);

            mockedStatic.verify(() -> PageRequestBuilder.buildPageRequest(offset, limit, sortBy.getValue(), sortOrder));
            verify(carRepository).findAll(pageRequest);
            verify(carMapper).entityPageToDtoPage(carPage);
            verify(pageMapper).pageToPageDto(carDtoPage);
        }
    }

    @Test
    void getCarById_ShouldReturnCar_WhenCarFound() {
        Car car = CarTestUtil.buildDefaultCar();
        CarDto carDto = CarTestUtil.buildCarDto();

        when(carRepository.findById(car.getId()))
                .thenReturn(Optional.of(car));
        when(carMapper.entityToDto(car))
                .thenReturn(carDto);

        CarDto actual = carService.getCarById(car.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(carDto);

        verify(carRepository).findById(car.getId());
        verify(carMapper).entityToDto(car);
    }

    @Test
    void getCarById_ShouldThrowResourceNotFoundException_WhenCarNotFound() {
        Car car = CarTestUtil.buildDefaultCar();

        when(carRepository.findById(car.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> carService.getCarById(car.getId()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(carRepository).findById(car.getId());
        verifyNoInteractions(carMapper);
    }

    @Test
    void getFullCarById_ShouldReturnFullCar_WhenCarFound() {
        Car car = CarTestUtil.buildDefaultCar();
        CarFullDto carFullDto = CarTestUtil.buildCarFullDto();

        when(carRepository.findById(car.getId()))
                .thenReturn(Optional.of(car));
        when(carMapper.entityToFullDto(car))
                .thenReturn(carFullDto);

        CarFullDto actual = carService.getFullCarById(car.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(carFullDto);

        verify(carRepository).findById(car.getId());
        verify(carMapper).entityToFullDto(car);
    }

    @Test
    void getFullCarById_ShouldThrowResourceNotFoundException_WhenCarNotFound() {
        Car car = CarTestUtil.buildDefaultCar();

        when(carRepository.findById(car.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> carService.getFullCarById(car.getId()))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(carRepository).findById(car.getId());
        verifyNoInteractions(carMapper);
    }

    @Test
    void saveCar_ShouldThrowValidationErrorException_WhenCarReleaseDateNotValid() {
        CarAddingDto carAddingDto = CarTestUtil.buildCarAddingDto();

        doThrow(new ValidationErrorException("error"))
                .when(carDetailsValidator).validateReleaseDate(carAddingDto.details().releaseDate());

        assertThatThrownBy(
                () -> carService.saveCar(carAddingDto))
                .isInstanceOf(ValidationErrorException.class);

        verify(carDetailsValidator).validateReleaseDate(carAddingDto.details().releaseDate());
        verifyNoInteractions(carValidator, carMapper, carDetailsMapper, carRepository);
    }

    @Test
    void saveCar_ShouldThrowDataUniquenessException_WhenCarLicensePlateNotUnique() {
        CarAddingDto carAddingDto = CarTestUtil.buildCarAddingDto();

        doNothing().when(carDetailsValidator).validateReleaseDate(carAddingDto.details().releaseDate());
        doThrow(new DataUniquenessConflictException("error"))
                .when(carValidator).validateLicencePlateUniqueness(carAddingDto.licensePlate());

        assertThatThrownBy(
                () -> carService.saveCar(carAddingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(carDetailsValidator).validateReleaseDate(carAddingDto.details().releaseDate());
        verify(carValidator).validateLicencePlateUniqueness(carAddingDto.licensePlate());
        verifyNoInteractions(carMapper, carDetailsMapper, carRepository);
    }

    @Test
    void saveCar_ShouldSaveCar_WhenCarDetailsAreValid() {
        CarAddingDto carAddingDto = CarTestUtil.buildCarAddingDto();
        CarDto carDto = CarTestUtil.buildCarDto();
        Car car = CarTestUtil.buildDefaultCar();

        doNothing().when(carDetailsValidator).validateReleaseDate(carAddingDto.details().releaseDate());
        doNothing().when(carValidator).validateLicencePlateUniqueness(carAddingDto.licensePlate());
        when(carMapper.dtoToEntity(carAddingDto))
                .thenReturn(car);
        when(carRepository.save(any(Car.class)))
                .thenReturn(car);
        when(carMapper.entityToDto(any(Car.class)))
                .thenReturn(carDto);

        CarDto actual = carService.saveCar(carAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(carDto);

        verify(carDetailsValidator).validateReleaseDate(carAddingDto.details().releaseDate());
        verify(carValidator).validateLicencePlateUniqueness(carAddingDto.licensePlate());
        verify(carMapper).dtoToEntity(carAddingDto);
        verify(carRepository).save(any(Car.class));
        verify(carMapper).entityToDto(any(Car.class));
    }

    @Test
    void updateCar_ShouldThrowResourceNotFoundException_WhenCarNotFound() {
        CarUpdatingDto carUpdatingDto = CarTestUtil.buildCarUpdatingDto();
        Long carId = CarTestUtil.NOT_EXISTING_CAR_ID;

        when(carRepository.findById(carId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> carService.updateCar(carId, carUpdatingDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(carRepository).findById(carId);
        verifyNoMoreInteractions(carRepository);
        verifyNoInteractions(carValidator, carMapper);
    }

    @Test
    void updateCar_ShouldThrowDataUniquenessException_WhenNewCarLicensePlateNotUnique() {
        CarUpdatingDto carUpdatingDto = CarTestUtil.buildCarUpdatingDto();
        Car car = CarTestUtil.buildDefaultCar()
                .toBuilder()
                .licensePlate(CarTestUtil.OTHER_LICENSE_PLATE)
                .build();
        Long carId = car.getId();

        when(carRepository.findById(car.getId()))
                .thenReturn(Optional.of(car));
        doThrow(new DataUniquenessConflictException("error"))
                .when(carValidator).validateLicencePlateUniqueness(carUpdatingDto.licensePlate());

        assertThatThrownBy(
                () -> carService.updateCar(carId, carUpdatingDto))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(carRepository).findById(carId);
        verify(carValidator).validateLicencePlateUniqueness(carUpdatingDto.licensePlate());
        verifyNoMoreInteractions(carRepository);
        verifyNoInteractions(carMapper);
    }

    @Test
    void updateCar_ShouldUpdateCar_WhenCarFound() {
        CarUpdatingDto carUpdatingDto = CarTestUtil.buildCarUpdatingDto();
        Car car = CarTestUtil.buildDefaultCar()
                .toBuilder()
                .licensePlate(CarTestUtil.OTHER_LICENSE_PLATE)
                .build();
        Long carId = car.getId();
        CarDto carDto = CarTestUtil.buildCarDto();

        when(carRepository.findById(car.getId()))
                .thenReturn(Optional.of(car));
        doNothing().when(carValidator).validateLicencePlateUniqueness(carUpdatingDto.licensePlate());
        doNothing().when(carMapper).updateEntityFromDto(carUpdatingDto, car);
        when(carRepository.save(any(Car.class)))
                .thenReturn(car);
        when(carMapper.entityToDto(any(Car.class)))
                .thenReturn(carDto);

        CarDto actual = carService.updateCar(carId, carUpdatingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(carDto);

        verify(carRepository).findById(carId);
        verify(carValidator).validateLicencePlateUniqueness(carUpdatingDto.licensePlate());
        verify(carMapper).updateEntityFromDto(carUpdatingDto, car);
        verify(carRepository).save(any(Car.class));
        verify(carMapper).entityToDto(any(Car.class));
    }

    @Test
    void updateCarDetails_ShouldThrowValidationErrorException_WhenCarReleaseDateNotValid() {
        Long carId = CarTestUtil.CAR_ID;
        CarDetailsSettingDto carDetailsDto = CarDetailsTestUtil.buildCarDetailsSettingDto();

        doThrow(new ValidationErrorException("error"))
                .when(carDetailsValidator).validateReleaseDate(carDetailsDto.releaseDate());

        assertThatThrownBy(
                () -> carService.updateCarDetails(carId, carDetailsDto))
                .isInstanceOf(ValidationErrorException.class);

        verify(carDetailsValidator).validateReleaseDate(carDetailsDto.releaseDate());
        verifyNoInteractions(carRepository, carMapper, carDetailsService);
    }

    @Test
    void updateCarDetails_ShouldThrowResourceNotFoundException_WhenCarNotFound() {
        Long carId = CarTestUtil.NOT_EXISTING_CAR_ID;
        CarDetailsSettingDto carDetailsDto = CarDetailsTestUtil.buildCarDetailsSettingDto();

        doNothing().when(carDetailsValidator).validateReleaseDate(carDetailsDto.releaseDate());
        when(carRepository.findById(carId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> carService.updateCarDetails(carId, carDetailsDto))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(carDetailsValidator).validateReleaseDate(carDetailsDto.releaseDate());
        verify(carRepository).findById(carId);
        verifyNoInteractions(carMapper, carDetailsService);
    }

    @Test
    void updateCarDetails_ShouldUpdateCarDetails_WhenCarFoundAndReleaseDateIsValid() {
        Long carId = CarTestUtil.CAR_ID;
        CarDetailsSettingDto carDetailsSettingDto = CarDetailsTestUtil.buildCarDetailsSettingDto();
        Car car = CarTestUtil.buildDefaultCar();
        CarDetailsDto carDetailsDto = CarDetailsTestUtil.buildCarDetailsDto();
        CarDto carDto = CarTestUtil.buildCarDto();
        CarFullDto carFullDto = new CarFullDto(carDto, carDetailsDto);

        doNothing().when(carDetailsValidator).validateReleaseDate(carDetailsSettingDto.releaseDate());
        when(carRepository.findById(carId))
                .thenReturn(Optional.of(car));
        when(carDetailsService.updateCarDetails(carId, carDetailsSettingDto))
                .thenReturn(carDetailsDto);
        when(carMapper.entityToDto(car))
                .thenReturn(carDto);

        CarFullDto actual = carService.updateCarDetails(carId, carDetailsSettingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(carFullDto);

        verify(carDetailsValidator).validateReleaseDate(carDetailsSettingDto.releaseDate());
        verify(carRepository).findById(carId);
        verify(carDetailsService).updateCarDetails(carId, carDetailsSettingDto);
        verify(carMapper).entityToDto(car);
    }

    @Test
    void deleteCarById_ShouldThrowResourceNotFoundException_WhenCarNotFound() {
        Long carId = CarTestUtil.NOT_EXISTING_CAR_ID;

        doThrow(new ResourceNotFoundException("error")).when(carValidator).validateExistenceOfCarWithId(carId);

        assertThatThrownBy(
                () -> carService.deleteCarById(carId))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(carValidator).validateExistenceOfCarWithId(carId);
        verifyNoInteractions(carRepository);
    }

    @Test
    void deleteCarById_ShouldNotThrowException_WhenCarFound() {
        Long carId = CarTestUtil.CAR_ID;

        doNothing().when(carValidator).validateExistenceOfCarWithId(carId);

        assertThatCode(
                () -> carService.deleteCarById(carId))
                .doesNotThrowAnyException();

        verify(carValidator).validateExistenceOfCarWithId(carId);
        verify(carRepository).deleteById(carId);
    }
}
