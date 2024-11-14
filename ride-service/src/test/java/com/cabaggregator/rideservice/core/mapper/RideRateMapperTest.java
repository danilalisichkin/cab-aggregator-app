package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.RideRateTestUtil;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.entity.RideRate;
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

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class RideRateMapperTest {
    private final RideRateMapper mapper = Mappers.getMapper(RideRateMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        RideRate rideRate = RideRateTestUtil.buildRideRate();
        RideRateDto rideRateDto = RideRateTestUtil.buildRideRateDto();

        RideRateDto result = mapper.entityToDto(rideRate);

        assertThat(result)
                .isNotNull()
                .isEqualTo(rideRateDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        RideRate rideRate = RideRateTestUtil.buildRideRate();
        RideRateDto rideRateDto = RideRateTestUtil.buildRideRateDto();

        List<RideRate> entityList = Arrays.asList(rideRate, rideRate);
        List<RideRateDto> expectedDtoList = Arrays.asList(rideRateDto, rideRateDto);

        List<RideRateDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<RideRate> entityList = Collections.emptyList();

        List<RideRateDto> result = mapper.entityListToDtoList(entityList);

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
        RideRate rideRate = RideRateTestUtil.buildRideRate();

        List<RideRate> entityList = Arrays.asList(rideRate, rideRate);
        Page<RideRate> entityPage = new PageImpl<>(entityList);

        final int expectedPage = 0;
        final int expectedTotalPages = 1;
        final long expectedTotalItems = 2;
        final int expectedItemsOnPage = 2;
        final int expectedPassengersListSize = 2;

        Page<RideRateDto> result = mapper.entityPageToDtoPage(entityPage);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(expectedPage);
        assertThat(result.getTotalPages()).isEqualTo(expectedTotalPages);
        assertThat(result.getTotalElements()).isEqualTo(expectedTotalItems);
        assertThat(result.getNumberOfElements()).isEqualTo(expectedItemsOnPage);
        assertThat(result.getContent()).hasSize(expectedPassengersListSize);
    }

    @Test
    void entityPageToDtoPage_ShouldReturnNull_WhenPageIsNull() {
        Page<RideRateDto> result = mapper.entityPageToDtoPage(null);

        assertThat(result).isNull();
    }
}
