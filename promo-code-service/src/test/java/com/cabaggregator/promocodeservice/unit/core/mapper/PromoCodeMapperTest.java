package com.cabaggregator.promocodeservice.unit.core.mapper;

import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.core.mapper.PromoCodeMapper;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.util.PaginationTestUtil;
import com.cabaggregator.promocodeservice.util.PromoCodeTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Tag("unit")
class PromoCodeMapperTest {
    private final PromoCodeMapper mapper = Mappers.getMapper(PromoCodeMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        PromoCodeDto actual = mapper.entityToDto(promoCode);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(promoCodeDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        PromoCode actual = PromoCodeTestUtil.getPromoCodeBuilder().build();
        PromoCodeUpdatingDto promoCodeUpdatingDto = PromoCodeTestUtil.buildPromoCodeUpdatingDto();

        mapper.updateEntityFromDto(promoCodeUpdatingDto, actual);

        assertThat(actual).isNotNull();
        assertThat(actual.getValue()).isEqualTo(PromoCodeTestUtil.VALUE);
        assertThat(actual.getDiscountPercentage()).isEqualTo(promoCodeUpdatingDto.discountPercentage());
        assertThat(actual.getEndDate()).isEqualTo(promoCodeUpdatingDto.endDate());
        assertThat(actual.getLimit()).isEqualTo(promoCodeUpdatingDto.limit());
    }

    @Test
    void updateEntityFromDto_ShouldThrowNullPointerException_WhenDtoIsNull() {
        PromoCodeUpdatingDto promoCodeUpdatingDto = PromoCodeTestUtil.buildPromoCodeUpdatingDto();

        assertThatThrownBy(() -> mapper.updateEntityFromDto(promoCodeUpdatingDto, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        PromoCodeAddingDto promoCodeAddingDto = PromoCodeTestUtil.buildPromoCodeAddingDto();

        PromoCode actual = mapper.dtoToEntity(promoCodeAddingDto);

        assertThat(actual).isNotNull();
        assertThat(actual.getValue()).isEqualTo(promoCodeAddingDto.value());
        assertThat(actual.getDiscountPercentage()).isEqualTo(promoCodeAddingDto.discountPercentage());
        assertThat(actual.getEndDate()).isEqualTo(promoCodeAddingDto.endDate());
        assertThat(actual.getLimit()).isEqualTo(promoCodeAddingDto.limit());
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        List<PromoCode> entityList = Arrays.asList(promoCode, promoCode);
        List<PromoCodeDto> expectedDtoList = Arrays.asList(promoCodeDto, promoCodeDto);

        List<PromoCodeDto> actual = mapper.entityListToDtoList(entityList);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<PromoCode> entityList = Collections.emptyList();

        List<PromoCodeDto> actual = mapper.entityListToDtoList(entityList);

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
        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();
        List<PromoCode> entityList = Arrays.asList(promoCode, promoCode);
        Page<PromoCode> entityPage = PaginationTestUtil.buildPageFromList(entityList);

        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();
        List<PromoCodeDto> dtoList = Arrays.asList(promoCodeDto, promoCodeDto);
        Page<PromoCodeDto> expectedDtoPage = PaginationTestUtil.buildPageFromList(dtoList);

        Page<PromoCodeDto> actual = mapper.entityPageToDtoPage(entityPage);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}
