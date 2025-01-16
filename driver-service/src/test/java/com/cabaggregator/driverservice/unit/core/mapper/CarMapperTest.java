package com.cabaggregator.driverservice.unit.core.mapper;

import com.cabaggregator.driverservice.core.dto.car.CarAddingDto;
import com.cabaggregator.driverservice.core.dto.car.CarDto;
import com.cabaggregator.driverservice.core.dto.car.CarFullDto;
import com.cabaggregator.driverservice.core.dto.car.CarUpdatingDto;
import com.cabaggregator.driverservice.core.mapper.CarDetailsMapper;
import com.cabaggregator.driverservice.core.mapper.CarMapperImpl;
import com.cabaggregator.driverservice.entity.Car;
import com.cabaggregator.driverservice.util.CarDetailsTestUtil;
import com.cabaggregator.driverservice.util.CarTestUtil;
import com.cabaggregator.driverservice.util.PaginationTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CarMapperTest {
    @InjectMocks
    private CarMapperImpl mapper;

    @Mock
    private CarDetailsMapper carDetailsMapper;

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        Car car = CarTestUtil.buildDefaultCar();
        CarDto carDto = CarTestUtil.buildCarDto();

        CarDto actual = mapper.entityToDto(car);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(carDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void entityToFullDto_ShouldConvertEntityToFullDto_WhenEntityIsNotNull() {
        Car car = CarTestUtil.buildDefaultCar()
                .toBuilder()
                .carDetails(CarDetailsTestUtil.buildDefaultCarDetails())
                .build();
        CarFullDto carFullDto = CarTestUtil.buildCarFullDto();
        Mockito.when(carDetailsMapper.entityToDto(car.getCarDetails()))
                .thenReturn(CarDetailsTestUtil.buildCarDetailsDto());

        CarFullDto actual = mapper.entityToFullDto(car);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(carFullDto);
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        Car actual = CarTestUtil.buildDefaultCar();
        CarUpdatingDto carUpdatingDto = CarTestUtil.buildCarUpdatingDto();

        mapper.updateEntityFromDto(carUpdatingDto, actual);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(CarTestUtil.ID);
        assertThat(actual.getLicensePlate()).isEqualTo(CarTestUtil.UPDATED_LICENSE_PLATE);
        assertThat(actual.getMake()).isEqualTo(CarTestUtil.UPDATED_MAKE);
        assertThat(actual.getModel()).isEqualTo(CarTestUtil.UPDATED_MODEL);
        assertThat(actual.getColor()).isEqualTo(CarTestUtil.UPDATED_COLOR);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        CarUpdatingDto carUpdatingDto = CarTestUtil.buildCarUpdatingDto();

        assertThatThrownBy(
                () -> mapper.updateEntityFromDto(carUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        Car car = CarTestUtil.buildDefaultCar();
        CarAddingDto carAddingDto = CarTestUtil.buildCarAddingDto();

        Car actual = mapper.dtoToEntity(carAddingDto);

        assertThat(actual.getLicensePlate()).isEqualTo(car.getLicensePlate());
        assertThat(actual.getMake()).isEqualTo(car.getMake());
        assertThat(actual.getModel()).isEqualTo(car.getModel());
        assertThat(actual.getColor()).isEqualTo(car.getColor());
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        Car car = CarTestUtil.buildDefaultCar();
        List<Car> entityList = Arrays.asList(car, car);
        CarDto carDto = CarTestUtil.buildCarDto();
        List<CarDto> dtoList = Arrays.asList(carDto, carDto);

        List<CarDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(dtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Car> entityList = Collections.emptyList();

        List<CarDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void entityListToDtoList_ShouldReturnNull_WhenEntityListIsNull() {
        assertThat(mapper.entityListToDtoList(null)).isNull();
    }

    @Test
    void entityPageToDtoPage_ShouldConvertEntityPageToDtoPage_WhenPageIsNotNull() {
        Car car = CarTestUtil.buildDefaultCar();
        List<Car> entityList = Arrays.asList(car, car);
        Page<Car> entityPage = PaginationTestUtil.buildPageFromList(entityList);
        CarDto carDto = CarTestUtil.buildCarDto();
        List<CarDto> dtoList = Arrays.asList(carDto, carDto);
        Page<CarDto> expectedDtoPage = PaginationTestUtil.buildPageFromList(dtoList);

        Page<CarDto> actual = mapper.entityPageToDtoPage(entityPage);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}
