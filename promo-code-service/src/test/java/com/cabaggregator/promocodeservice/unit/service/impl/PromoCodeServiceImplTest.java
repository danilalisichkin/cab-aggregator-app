package com.cabaggregator.promocodeservice.unit.service.impl;

import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.core.mapper.PromoCodeMapper;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.exception.ResourceNotFoundException;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import com.cabaggregator.promocodeservice.service.impl.PromoCodeServiceImpl;
import com.cabaggregator.promocodeservice.util.PromoCodeTestUtil;
import com.cabaggregator.promocodeservice.validator.PromoCodeValidator;
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
class PromoCodeServiceImplTest {
    @InjectMocks
    private PromoCodeServiceImpl promoCodeService;

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @Mock
    private PromoCodeMapper promoCodeMapper;

    @Mock
    private PromoCodeValidator promoCodeValidator;

    @Test
    void getPromoCode_ShouldReturnPromoCode_WhenPromoCodeFound() {
        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();

        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.of(promoCode));
        when(promoCodeMapper.entityToDto(promoCode))
                .thenReturn(promoCodeDto);

        PromoCodeDto actual = promoCodeService.getPromoCode(promoCode.getValue());

        assertThat(actual)
                .isNotNull()
                .isEqualTo(promoCodeDto);
    }

    @Test
    void getPromoCode_ShouldThrowResourceNotFoundException_WhenPromoCodeNotFound() {
        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();

        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> promoCodeService.getPromoCode(promoCode.getValue()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updatePromoCode_ShouldReturnPromoCode_WhenPromoCodeFound() {
        PromoCode promoCode = PromoCodeTestUtil.getPromoCodeBuilder().build();
        PromoCodeDto promoCodeDto = PromoCodeTestUtil.buildPromoCodeDto();
        PromoCodeUpdatingDto promoCodeUpdatingDto = PromoCodeTestUtil.buildPromoCodeUpdatingDto();

        doNothing().when(promoCodeValidator).validateEndDate(any());

        when(promoCodeRepository.findById(promoCode.getValue()))
                .thenReturn(Optional.of(promoCode));
        when(promoCodeRepository.save(promoCode))
                .thenReturn(promoCode);
        when(promoCodeMapper.entityToDto(promoCode))
                .thenReturn(promoCodeDto);

        PromoCodeDto actual = promoCodeService.updatePromoCode(promoCode.getValue(), promoCodeUpdatingDto);

        assertThat(actual)
                .isNotNull()
                .isEqualTo(promoCodeDto);
    }

    @Test
    void updatePromoCode_ShouldThrowResourceNotFoundException_WhenPromoCodeNotFound() {
        PromoCodeUpdatingDto promoCodeUpdatingDto = PromoCodeTestUtil.buildPromoCodeUpdatingDto();

        String notExistingPromoCode = "FREE TAXI";

        doNothing().when(promoCodeValidator).validateEndDate(any());

        when(promoCodeRepository.findById(notExistingPromoCode))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> promoCodeService.updatePromoCode(notExistingPromoCode, promoCodeUpdatingDto))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
