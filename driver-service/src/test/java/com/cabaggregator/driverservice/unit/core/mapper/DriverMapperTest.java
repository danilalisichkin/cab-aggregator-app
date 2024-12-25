package com.cabaggregator.driverservice.unit.core.mapper;

import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.mapper.DriverMapper;
import com.cabaggregator.driverservice.entity.Driver;
import com.cabaggregator.driverservice.util.DriverTestUtil;
import com.cabaggregator.driverservice.util.PaginationTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class DriverMapperTest {
    private final DriverMapper mapper = Mappers.getMapper(DriverMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        Driver driver = DriverTestUtil.getDriverBuilder().build();
        DriverDto driverDto = DriverTestUtil.buildDriverDto();

        DriverDto actual = mapper.entityToDto(driver);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(driverDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        Driver actual = DriverTestUtil.getDriverBuilder().build();
        DriverUpdatingDto driverUpdatingDto = DriverTestUtil.buildDriverUpdatingDto();

        mapper.updateEntityFromDto(driverUpdatingDto, actual);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(DriverTestUtil.DRIVER_ID);
        assertThat(actual.getPhoneNumber()).isEqualTo(DriverTestUtil.UPDATED_PHONE_NUMBER);
        assertThat(actual.getEmail()).isEqualTo(DriverTestUtil.UPDATED_EMAIL);
        assertThat(actual.getFirstName()).isEqualTo(DriverTestUtil.UPDATED_FIRST_NAME);
        assertThat(actual.getLastName()).isEqualTo(DriverTestUtil.UPDATED_LAST_NAME);
        assertThat(actual.getRating()).isEqualTo(DriverTestUtil.UPDATED_RATING);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        DriverUpdatingDto driverUpdatingDto = DriverTestUtil.buildDriverUpdatingDto();

        assertThatThrownBy(
                () -> mapper.updateEntityFromDto(driverUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        Driver driver = DriverTestUtil.getDriverBuilder().build();
        DriverAddingDto driverAddingDto = DriverTestUtil.buildDriverAddingDto();

        Driver actual = mapper.dtoToEntity(driverAddingDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(driverAddingDto.id());
        assertThat(actual.getPhoneNumber()).isEqualTo(driver.getPhoneNumber());
        assertThat(actual.getEmail()).isEqualTo(driver.getEmail());
        assertThat(actual.getFirstName()).isEqualTo(driver.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(driver.getLastName());
        assertThat(actual.getRating()).isNull();
        assertThat(actual.getCar()).isNull();
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        Driver driver = DriverTestUtil.getDriverBuilder().build();
        List<Driver> entityList = Arrays.asList(driver, driver);
        DriverDto driverDto = DriverTestUtil.buildDriverDto();
        List<DriverDto> dtoList = Arrays.asList(driverDto, driverDto);

        List<DriverDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(dtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<Driver> entityList = Collections.emptyList();

        List<DriverDto> actual = mapper.entityListToDtoList(entityList);

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
        Driver driver = DriverTestUtil.getDriverBuilder().build();
        List<Driver> entityList = Arrays.asList(driver, driver);
        Page<Driver> entityPage = PaginationTestUtil.buildPageFromList(entityList);
        DriverDto driverDto = DriverTestUtil.buildDriverDto();
        List<DriverDto> dtoList = Arrays.asList(driverDto, driverDto);
        Page<DriverDto> expectedDtoPage = PaginationTestUtil.buildPageFromList(dtoList);

        Page<DriverDto> actual = mapper.entityPageToDtoPage(entityPage);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}
