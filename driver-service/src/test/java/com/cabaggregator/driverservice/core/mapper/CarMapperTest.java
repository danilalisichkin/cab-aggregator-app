package com.cabaggregator.driverservice.core.mapper;

import com.cabaggregator.driverservice.CarDetailsTestUtil;
import com.cabaggregator.driverservice.CarTestUtil;
import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.entity.Car;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CarMapperTest {
    @Mock
    private CarDetailsMapper carDetailsMapper;

    @InjectMocks
    private CarMapperImpl mapper;

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        Car car = CarTestUtil.buildCar();
        CarDto carDto = CarTestUtil.buildCarDto();

        CarDto convertedDto = mapper.entityToDto(car);

        assertThat(convertedDto)
                .isNotNull()
                .isEqualTo(carDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void entityToFullDto_ShouldConvertEntityToFullDto_WhenEntityIsNotNull() {
        Car car = CarTestUtil.buildCar();
        car.setCarDetails(CarDetailsTestUtil.buildCarDetails());
        CarFullDto carFullDto = CarTestUtil.buildCarFullDto();
        Mockito.when(carDetailsMapper.entityToDto(car.getCarDetails()))
                .thenReturn(CarDetailsTestUtil.buildCarDetailsDto());

        CarFullDto convertedDto = mapper.entityToFullDto(car);

        assertThat(convertedDto).isEqualTo(carFullDto);
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        Car car = CarTestUtil.buildCar();
        CarUpdatingDto carUpdatingDto = CarTestUtil.buildCarUpdatingDto();

        mapper.updateEntityFromDto(carUpdatingDto, car);

        assertThat(car).isNotNull();
        assertThat(car.getId()).isEqualTo(CarTestUtil.CAR_ID);
        assertThat(car.getLicensePlate()).isEqualTo(CarTestUtil.UPDATED_LICENSE_PLATE);
        assertThat(car.getMake()).isEqualTo(CarTestUtil.UPDATED_MAKE);
        assertThat(car.getModel()).isEqualTo(CarTestUtil.UPDATED_MODEL);
        assertThat(car.getColor()).isEqualTo(CarTestUtil.UPDATED_COLOR);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        CarUpdatingDto carUpdatingDto = CarTestUtil.buildCarUpdatingDto();

        assertThatThrownBy(() -> mapper.updateEntityFromDto(carUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        Car car = CarTestUtil.buildCar();
        CarAddingDto carAddingDto = CarTestUtil.buildCarAddingDto();

        Car convertedEntity = mapper.dtoToEntity(carAddingDto);

        assertThat(convertedEntity).isNotNull();
        assertThat(convertedEntity.getId()).isNull();
        assertThat(convertedEntity.getLicensePlate()).isEqualTo(car.getLicensePlate());
        assertThat(convertedEntity.getMake()).isEqualTo(car.getMake());
        assertThat(convertedEntity.getModel()).isEqualTo(car.getModel());
        assertThat(convertedEntity.getColor()).isEqualTo(car.getColor());
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        Car car = CarTestUtil.buildCar();
        CarDto carDto = CarTestUtil.buildCarDto();

        List<Car> entityList = Arrays.asList(car, car);
        List<CarDto> expectedDtoList = Arrays.asList(carDto, carDto);

        List<CarDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Car> entityList = Collections.emptyList();

        List<CarDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void entityListToDtoList_ShouldReturnNull_WhenEntityListIsNull() {
        assertThat(mapper.entityListToDtoList(null)).isNull();
    }

    @Test
    void entityPageToDtoPage_ShouldConvertEntityPageToDtoPage_WhenPageIsNotNull() {
        Car car = CarTestUtil.buildCar();

        List<Car> entityList = Arrays.asList(car, car);
        Page<Car> entityPage = new PageImpl<>(entityList);

        final int expectedPage = 0;
        final int expectedTotalPages = 1;
        final long expectedTotalItems = 2;
        final int expectedItemsOnPage = 2;
        final int expectedPassengersListSize = 2;

        Page<CarDto> result = mapper.entityPageToDtoPage(entityPage);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(expectedPage);
        assertThat(result.getTotalPages()).isEqualTo(expectedTotalPages);
        assertThat(result.getTotalElements()).isEqualTo(expectedTotalItems);
        assertThat(result.getNumberOfElements()).isEqualTo(expectedItemsOnPage);
        assertThat(result.getContent()).hasSize(expectedPassengersListSize);
    }

    @Test
    void entityPageToDtoPage_ShouldReturnNull_WhenPageIsNull() {
        Page<CarDto> result = mapper.entityPageToDtoPage(null);

        assertThat(result).isNull();
    }
}
