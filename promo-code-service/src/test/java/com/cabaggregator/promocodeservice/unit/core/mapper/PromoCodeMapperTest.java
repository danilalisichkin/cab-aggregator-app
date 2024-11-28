package com.cabaggregator.promocodeservice.unit.core.mapper;

import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.core.mapper.PromoCodeMapper;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.util.PromoCodeTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
        PromoCode promoCode = PromoCodeTestUtil.buildPromoCode();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        PromoCodeDto result = mapper.entityToDto(promoCode);

        assertThat(result)
                .isNotNull()
                .isEqualTo(promoCodeDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void updateEntityFromDto_ShouldUpdateEntity_WhenDtoIsNotNull() {
        PromoCode promoCode = PromoCodeTestUtil.buildPromoCode();
        PromoCodeUpdatingDto promoCodeUpdatingDto = PromoCodeTestUtil.buildPromoCodeUpdatingDto();

        mapper.updateEntityFromDto(promoCodeUpdatingDto, promoCode);

        assertThat(promoCode).isNotNull();
        assertThat(promoCode.getValue()).isEqualTo(PromoCodeTestUtil.VALUE);
        assertThat(promoCode.getDiscountPercentage()).isEqualTo(promoCodeUpdatingDto.discountPercentage());
        assertThat(promoCode.getEndDate()).isEqualTo(promoCodeUpdatingDto.endDate());
        assertThat(promoCode.getLimits()).isEqualTo(promoCodeUpdatingDto.limits());
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

        PromoCode result = mapper.dtoToEntity(promoCodeAddingDto);

        assertThat(result).isNotNull();
        assertThat(result.getValue()).isEqualTo(promoCodeAddingDto.value());
        assertThat(result.getDiscountPercentage()).isEqualTo(promoCodeAddingDto.discountPercentage());
        assertThat(result.getEndDate()).isEqualTo(promoCodeAddingDto.endDate());
        assertThat(result.getLimits()).isEqualTo(promoCodeAddingDto.limits());
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        PromoCode promoCode = PromoCodeTestUtil.buildPromoCode();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        List<PromoCode> entityList = Arrays.asList(promoCode, promoCode);
        List<PromoCodeDto> expectedDtoList = Arrays.asList(promoCodeDto, promoCodeDto);

        List<PromoCodeDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<PromoCode> entityList = Collections.emptyList();

        List<PromoCodeDto> result = mapper.entityListToDtoList(entityList);

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
        PromoCode promoCode = PromoCodeTestUtil.buildPromoCode();
        List<PromoCode> entityList = Arrays.asList(promoCode, promoCode);
        Page<PromoCode> entityPage = new PageImpl<>(entityList);

        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();
        List<PromoCodeDto> dtoList = Arrays.asList(promoCodeDto, promoCodeDto);
        Page<PromoCodeDto> expectedDtoPage = new PageImpl<>(dtoList);

        Page<PromoCodeDto> result = mapper.entityPageToDtoPage(entityPage);

        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}
