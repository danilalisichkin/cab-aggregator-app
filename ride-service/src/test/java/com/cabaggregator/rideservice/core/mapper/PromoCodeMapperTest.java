package com.cabaggregator.rideservice.core.mapper;

import com.cabaggregator.rideservice.PromoCodeTestUtil;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeAddingDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.promo.RidePromoCodeDto;
import com.cabaggregator.rideservice.entity.PromoCode;
import org.junit.jupiter.api.BeforeEach;
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
public class PromoCodeMapperTest {
    private final PromoCodeMapper mapper = Mappers.getMapper(PromoCodeMapper.class);

    private PromoCode promoCode;
    private PromoCodeDto promoCodeDto;
    private PromoCodeAddingDto promoCodeAddingDto;
    private PromoCodeUpdatingDto promoCodeUpdatingDto;
    private RidePromoCodeDto ridePromoCodeDto;

    @BeforeEach
    void setUp() {
        promoCode = PromoCodeTestUtil.buildPromoCode();
        promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();
        promoCodeAddingDto = PromoCodeTestUtil.buildPromoCodeAddingDto();
        promoCodeUpdatingDto = PromoCodeTestUtil.buildPromoCodeUpdatingDto();
        ridePromoCodeDto = PromoCodeTestUtil.buildRidePromoCodeDto();
    }

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        PromoCodeDto result = mapper.entityToDto(promoCode);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(promoCodeDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void entityToRideDto_ShouldConvertEntityToRideDto_WhenEntityIsNotNull() {
        RidePromoCodeDto result = mapper.entityToRideDto(promoCode);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(ridePromoCodeDto);
    }

    @Test
    void entityToRideDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToRideDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        mapper.updateEntityFromDto(promoCodeUpdatingDto, promoCode);

        assertThat(promoCode).isNotNull();
        assertThat(promoCode.getId()).isEqualTo(PromoCodeTestUtil.PROMO_CODE_ID);
        assertThat(promoCode.getValue()).isEqualTo(PromoCodeTestUtil.VALUE);
        assertThat(promoCode.getDiscount()).isEqualTo(PromoCodeTestUtil.UPDATED_DISCOUNT);
        assertThat(promoCode.getStartDate()).isEqualTo(PromoCodeTestUtil.UPDATED_START_DATE);
        assertThat(promoCode.getEndDate()).isEqualTo(PromoCodeTestUtil.UPDATED_END_DATE);
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        assertThatThrownBy(() -> mapper.updateEntityFromDto(promoCodeUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        PromoCode result = mapper.dtoToEntity(promoCodeAddingDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getValue()).isEqualTo(promoCode.getValue());
        assertThat(result.getDiscount()).isEqualTo(promoCode.getDiscount());
        assertThat(result.getStartDate()).isEqualTo(promoCode.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(promoCode.getEndDate());
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        List<PromoCode> entityList = Arrays.asList(promoCode, promoCode);
        List<PromoCodeDto> expectedDtoList = Arrays.asList(promoCodeDto, promoCodeDto);

        List<PromoCodeDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<PromoCode> entityList = Collections.emptyList();

        List<PromoCodeDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void entityListToDtoList_ShouldReturnNull_WhenEntityListIsNull() {
        assertThat(mapper.entityListToDtoList(null)).isNull();
    }

    @Test
    void entityPageToDtoPage_ShouldConvertEntityPageToDtoPage_WhenPageIsNotNull() {
        List<PromoCode> entityList = Arrays.asList(promoCode, promoCode);
        Page<PromoCode> entityPage = new PageImpl<>(entityList);

        final int expectedPage = 0;
        final int expectedTotalPages = 1;
        final long expectedTotalItems = 2;
        final int expectedItemsOnPage = 2;
        final int expectedPassengersListSize = 2;

        Page<PromoCodeDto> result = mapper.entityPageToDtoPage(entityPage);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(expectedPage);
        assertThat(result.getTotalPages()).isEqualTo(expectedTotalPages);
        assertThat(result.getTotalElements()).isEqualTo(expectedTotalItems);
        assertThat(result.getNumberOfElements()).isEqualTo(expectedItemsOnPage);
        assertThat(result.getContent()).hasSize(expectedPassengersListSize);
    }

    @Test
    void entityPageToDtoPage_ShouldReturnNull_WhenPageIsNull() {
        Page<PromoCodeDto> result = mapper.entityPageToDtoPage(null);

        assertThat(result).isNull();
    }
}
