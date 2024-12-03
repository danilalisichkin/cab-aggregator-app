package com.cabaggregator.ratingservice.mapper;

import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateSettingDto;
import com.cabaggregator.ratingservice.core.mapper.PassengerRateMapper;
import com.cabaggregator.ratingservice.entity.PassengerRate;
import com.cabaggregator.ratingservice.util.PassengerRateTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
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
class PassengerRateMapperTest {
    private final PassengerRateMapper mapper = Mappers.getMapper(PassengerRateMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();
        PassengerRateDto passengerRateDto = PassengerRateTestUtil.buildPassengerRateDto();

        PassengerRateDto actual = mapper.entityToDto(passengerRate);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(passengerRateDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        PassengerRate actual = PassengerRateTestUtil.getPassengerRateBuilder().build();
        PassengerRateSettingDto passengerRateSettingDto = PassengerRateTestUtil.buildPassengerRateSettingDto();

        mapper.updateEntityFromDto(passengerRateSettingDto, actual);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(PassengerRateTestUtil.ID);
        assertThat(actual.getPassengerId()).isEqualTo(PassengerRateTestUtil.PASSENGER_ID);
        assertThat(actual.getDriverId()).isEqualTo(PassengerRateTestUtil.DRIVER_ID);
        assertThat(actual.getRate()).isEqualTo(passengerRateSettingDto.rate());
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        PassengerRateSettingDto passengerRateSettingDto = PassengerRateTestUtil.buildPassengerRateSettingDto();

        assertThatThrownBy(() -> mapper.updateEntityFromDto(passengerRateSettingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        PassengerRateAddingDto passengerRateAddingDto = PassengerRateTestUtil.buildPassengerRateAddingDto();

        PassengerRate actual = mapper.dtoToEntity(passengerRateAddingDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNull();
        assertThat(actual.getPassengerId()).isEqualTo(passengerRateAddingDto.passengerId());
        assertThat(actual.getDriverId()).isEqualTo(passengerRateAddingDto.driverId());
        assertThat(actual.getRate()).isNull();
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();
        PassengerRateDto passengerRateDto = PassengerRateTestUtil.buildPassengerRateDto();

        List<PassengerRate> entityList = Arrays.asList(passengerRate, passengerRate);
        List<PassengerRateDto> expectedDtoList = Arrays.asList(passengerRateDto, passengerRateDto);

        List<PassengerRateDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<PassengerRate> entityList = Collections.emptyList();

        List<PassengerRateDto> actual = mapper.entityListToDtoList(entityList);

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
        PassengerRate passengerRate = PassengerRateTestUtil.getPassengerRateBuilder().build();
        List<PassengerRate> entityList = Arrays.asList(passengerRate, passengerRate);
        Page<PassengerRate> entityPage = new PageImpl<>(entityList);

        PassengerRateDto passengerRateDto = PassengerRateTestUtil.buildPassengerRateDto();
        List<PassengerRateDto> dtoList = Arrays.asList(passengerRateDto, passengerRateDto);
        Page<PassengerRateDto> expectedDtoPage = new PageImpl<>(dtoList);

        Page<PassengerRateDto> actual = mapper.entityPageToDtoPage(entityPage);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}
