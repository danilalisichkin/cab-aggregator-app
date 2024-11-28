package com.cabaggregator.promocodeservice.unit.core.mapper;

import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatDto;
import com.cabaggregator.promocodeservice.core.mapper.PromoStatMapper;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import com.cabaggregator.promocodeservice.util.PromoStatTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class PromoStatMapperTest {
    private final PromoStatMapper mapper = Mappers.getMapper(PromoStatMapper.class);

    @Test
    void entityToDto_ShouldConvertEntityToDto_WhenEntityIsNotNull() {
        PromoStat promoStat = PromoStatTestUtil.buildPromoStat();
        PromoStatDto promoStatDto = PromoStatTestUtil.buildPromoStatDto();

        PromoStatDto result = mapper.entityToDto(promoStat);

        assertThat(result)
                .isNotNull()
                .isEqualTo(promoStatDto);
    }

    @Test
    void entityToDto_ShouldReturnNull_WhenEntityIsNull() {
        assertThat(mapper.entityToDto(null)).isNull();
    }

    @Test
    void dtoToEntity_ShouldConvertDtoToEntity_WhenDtoIsNotNull() {
        PromoStatAddingDto promoStatAddingDto = PromoStatTestUtil.buildPromoStatAddingDto();

        PromoStat result = mapper.dtoToEntity(promoStatAddingDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getPromoCode()).isNull();
        assertThat(result.getUserId()).isEqualTo(promoStatAddingDto.userId());
    }

    @Test
    void dtoToEntity_ShouldReturnNull_WhenDtoIsNull() {
        assertThat(mapper.dtoToEntity(null)).isNull();
    }

    @Test
    void entityListToDtoList_ShouldConvertEntityListToDtoList_WhenEntityListIsNotEmpty() {
        PromoStat promoStat = PromoStatTestUtil.buildPromoStat();
        PromoStatDto promoStatDto = PromoStatTestUtil.buildPromoStatDto();

        List<PromoStat> entityList = Arrays.asList(promoStat, promoStat);
        List<PromoStatDto> expectedDtoList = Arrays.asList(promoStatDto, promoStatDto);

        List<PromoStatDto> result = mapper.entityListToDtoList(entityList);

        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedDtoList);
    }

    @Test
    void entityListToDtoList_ShouldReturnEmptyList_WhenEntityListIsEmpty() {
        List<PromoStat> entityList = Collections.emptyList();

        List<PromoStatDto> result = mapper.entityListToDtoList(entityList);

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
        PromoStat promoStat = PromoStatTestUtil.buildPromoStat();
        List<PromoStat> entityList = Arrays.asList(promoStat, promoStat);
        Page<PromoStat> entityPage = new PageImpl<>(entityList);

        PromoStatDto promoStatDto = PromoStatTestUtil.buildPromoStatDto();
        List<PromoStatDto> dtoList = Arrays.asList(promoStatDto, promoStatDto);
        Page<PromoStatDto> expectedDtoPage = new PageImpl<>(dtoList);

        Page<PromoStatDto> result = mapper.entityPageToDtoPage(entityPage);

        assertThat(result)
                .isNotNull()
                .isEqualTo(expectedDtoPage);
    }
}
