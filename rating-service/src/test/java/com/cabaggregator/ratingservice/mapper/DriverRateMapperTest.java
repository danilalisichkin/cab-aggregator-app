package com.cabaggregator.ratingservice.mapper;

import com.cabaggregator.ratingservice.core.dto.driver.DriverRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateSettingDto;
import com.cabaggregator.ratingservice.core.mapper.DriverRateMapper;
import com.cabaggregator.ratingservice.entity.DriverRate;
import com.cabaggregator.ratingservice.util.DriverRateTestUtil;
import com.cabaggregator.ratingservice.util.PaginationTestUtil;
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
class DriverRateMapperTest {
    private final DriverRateMapper mapper = Mappers.getMapper(DriverRateMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        DriverRateDto driverRateDto = DriverRateTestUtil.buildDriverRateDto();

        DriverRateDto actual = mapper.entityToDto(driverRate);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(driverRateDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        DriverRate actual =
                DriverRateTestUtil.getDriverRateBuilder()
                        .rate(null)
                        .feedbackOptions(null)
                        .build();
        DriverRateSettingDto driverRateSettingDto = DriverRateTestUtil.buildDriverRateSettingDto();

        mapper.updateEntityFromDto(driverRateSettingDto, actual);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(DriverRateTestUtil.ID);
        assertThat(actual.getPassengerId()).isEqualTo(DriverRateTestUtil.PASSENGER_ID);
        assertThat(actual.getDriverId()).isEqualTo(DriverRateTestUtil.DRIVER_ID);
        assertThat(actual.getRate()).isEqualTo(driverRateSettingDto.rate());
        assertThat(actual.getFeedbackOptions()).isEqualTo(driverRateSettingDto.feedbackOptions());
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        DriverRateSettingDto driverRateSettingDto = DriverRateTestUtil.buildDriverRateSettingDto();

        assertThatThrownBy(() -> mapper.updateEntityFromDto(driverRateSettingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        DriverRateAddingDto driverRateAddingDto = DriverRateTestUtil.buildDriverRateAddingDto();

        DriverRate actual = mapper.dtoToEntity(driverRateAddingDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getPassengerId()).isEqualTo(driverRateAddingDto.passengerId());
        assertThat(actual.getDriverId()).isEqualTo(driverRateAddingDto.driverId());
        assertThat(actual.getRate()).isNull();
        assertThat(actual.getFeedbackOptions()).isNull();
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        List<DriverRate> entityList = Arrays.asList(driverRate, driverRate);
        DriverRateDto driverRateDto = DriverRateTestUtil.buildDriverRateDto();
        List<DriverRateDto> dtoList = Arrays.asList(driverRateDto, driverRateDto);

        List<DriverRateDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(dtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<DriverRate> entityList = Collections.emptyList();

        List<DriverRateDto> actual = mapper.entityListToDtoList(entityList);

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
        DriverRate driverRate = DriverRateTestUtil.getDriverRateBuilder().build();
        List<DriverRate> entityList = Arrays.asList(driverRate, driverRate);
        Page<DriverRate> entityPage = PaginationTestUtil.buildPageFromList(entityList);
        DriverRateDto driverRateDto = DriverRateTestUtil.buildDriverRateDto();
        List<DriverRateDto> dtoList = Arrays.asList(driverRateDto, driverRateDto);
        Page<DriverRateDto> expectedDtoPage = PaginationTestUtil.buildPageFromList(dtoList);

        Page<DriverRateDto> actual = mapper.entityPageToDtoPage(entityPage);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}
