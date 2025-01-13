package com.cabaggregator.promocodeservice.unit.validator;

import com.cabaggregator.promocodeservice.entity.PromoCode;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import com.cabaggregator.promocodeservice.exception.BadRequestException;
import com.cabaggregator.promocodeservice.repository.PromoStatRepository;
import com.cabaggregator.promocodeservice.util.PromoCodeTestUtil;
import com.cabaggregator.promocodeservice.util.PromoStatTestUtil;
import com.cabaggregator.promocodeservice.validator.PromoStatValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PromoStatValidatorTest {
    @Mock
    private PromoStatRepository promoStatRepository;

    @InjectMocks
    private PromoStatValidator promoStatValidator;

    @Test
    void validatePromoCodeApplication_ShouldThrowBadRequestException_WhenPromoCodeAlreadyApplied() {
        PromoCode promoCode = PromoCodeTestUtil.buildDefaultPromoCode();
        PromoStat promoStat = PromoStatTestUtil.buildDefaultPromoStat();
        promoStat.setPromoCode(promoCode);

        PromoCode code = promoStat.getPromoCode();
        UUID userId = PromoStatTestUtil.USER_ID;

        when(promoStatRepository.existsByPromoCodeAndUserId(code, userId)).thenReturn(true);

        assertThatThrownBy(() -> promoStatValidator.validatePromoCodeApplication(code, userId))
                .isInstanceOf(BadRequestException.class);

        verify(promoStatRepository).existsByPromoCodeAndUserId(code, userId);
    }

    @Test
    void validatePromoCodeApplication_ShouldNotThrowException_WhenPromoCodeIsNotApplied() {
        PromoCode code = PromoCodeTestUtil.buildDefaultPromoCode();
        UUID userId = PromoStatTestUtil.USER_ID;

        when(promoStatRepository.existsByPromoCodeAndUserId(code, userId)).thenReturn(false);

        assertThatCode(() -> promoStatValidator.validatePromoCodeApplication(code, userId))
                .doesNotThrowAnyException();

        verify(promoStatRepository).existsByPromoCodeAndUserId(code, userId);
    }
}
