package com.cabaggregator.promocodeservice.unit.service.impl;

import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatDto;
import com.cabaggregator.promocodeservice.core.mapper.PromoStatMapper;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import com.cabaggregator.promocodeservice.exception.ResourceNotFoundException;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import com.cabaggregator.promocodeservice.repository.PromoStatRepository;
import com.cabaggregator.promocodeservice.service.impl.PromoStatServiceImpl;
import com.cabaggregator.promocodeservice.util.PromoStatTestUtil;
import com.cabaggregator.promocodeservice.validator.PromoCodeValidator;
import com.cabaggregator.promocodeservice.validator.PromoStatValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PromoStatServiceImplTest {
    @InjectMocks
    private PromoStatServiceImpl promoStatService;

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @Mock
    private PromoStatRepository promoStatRepository;

    @Mock
    private PromoStatMapper promoStatMapper;

    @Mock
    private PromoCodeValidator promoCodeValidator;

    @Mock
    private PromoStatValidator promoStatValidator;

    @Test
    void getPromoStat_ShouldReturnPromoStat_WhenPromoStatFound() {
        PromoStat promoStat = PromoStatTestUtil.getPromoStatBuilder().build();
        PromoStatDto promoStatDto = PromoStatTestUtil.buildPromoStatDto();

        when(promoStatRepository.findById(promoStat.getId()))
                .thenReturn(Optional.of(promoStat));
        when(promoStatMapper.entityToDto(promoStat))
                .thenReturn(promoStatDto);

        PromoStatDto actual = promoStatService.getPromoStat(promoStat.getId());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(promoStatDto);
    }

    @Test
    void getPromoStat_ShouldThrowResourceNotFoundException_WhenPromoStatNotFound() {
        PromoStat promoStat = PromoStatTestUtil.getPromoStatBuilder().build();

        when(promoStatRepository.findById(promoStat.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> promoStatService.getPromoStat(promoStat.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void savePromoStat_ShouldReturnPromoStat_WhenPromoCodeExists() {
        PromoStat promoStat = PromoStatTestUtil.getPromoStatBuilder().build();
        PromoCode promoCode = promoStat.getPromoCode();
        PromoStatDto promoStatDto = PromoStatTestUtil.buildPromoStatDto();
        PromoStatAddingDto promoStatAddingDto = PromoStatTestUtil.buildPromoStatAddingDto();

        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.of(promoCode));

        doNothing().when(promoStatValidator).validatePromoCodeApplication(any(), any());
        doNothing().when(promoCodeValidator).validatePromoCodeExpiration(any());
        doNothing().when(promoCodeValidator).validatePromoCodeApplicationLimit(any());

        when(promoStatMapper.dtoToEntity(promoStatAddingDto))
                .thenReturn(promoStat);
        when(promoCodeRepository.save(promoCode))
                .thenReturn(promoCode);
        when(promoStatRepository.save(promoStat))
                .thenReturn(promoStat);
        when(promoStatMapper.entityToDto(promoStat))
                .thenReturn(promoStatDto);

        PromoStatDto actual = promoStatService.savePromoStat(promoStatAddingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(promoStatDto);
    }

    @Test
    void savePromoStat_ShouldThrowResourceNotFoundException_WhenPromoCodeDoesNotExist() {
        PromoStatAddingDto promoStatAddingDto = PromoStatTestUtil.buildPromoStatAddingDto();

        when(promoCodeRepository.findById(promoStatAddingDto.promoCode()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> promoStatService.savePromoStat(promoStatAddingDto))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}

