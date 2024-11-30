package com.cabaggregator.promocodeservice.unit.validator;

import com.cabaggregator.promocodeservice.exception.BadRequestException;
import com.cabaggregator.promocodeservice.exception.DataUniquenessConflictException;
import com.cabaggregator.promocodeservice.exception.ValidationErrorException;
import com.cabaggregator.promocodeservice.repository.PromoCodeRepository;
import com.cabaggregator.promocodeservice.util.PromoCodeTestUtil;
import com.cabaggregator.promocodeservice.validator.PromoCodeValidator;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PromoCodeValidatorTest {
    @Mock
    private PromoCodeRepository promoCodeRepository;

    @InjectMocks
    private PromoCodeValidator promoCodeValidator;

    @Test
    void validatePromoCodeUniqueness_ShouldThrowDataUniquenessException_WhenPromoCodeAlreadyExists() {
        String promoCode = PromoCodeTestUtil.VALUE;

        when(promoCodeRepository.existsById(promoCode)).thenReturn(true);

        assertThatThrownBy(() -> promoCodeValidator.validatePromoCodeUniqueness(promoCode))
                .isInstanceOf(DataUniquenessConflictException.class);

        verify(promoCodeRepository, times(1)).existsById(promoCode);
    }

    @Test
    void validatePromoCodeUniqueness_ShouldNotThrowException_WhenPromoCodeDoesNotExist() {
        String promoCode = PromoCodeTestUtil.VALUE;

        when(promoCodeRepository.existsById(promoCode)).thenReturn(false);

        assertThatCode(() -> promoCodeValidator.validatePromoCodeUniqueness(promoCode))
                .doesNotThrowAnyException();

        verify(promoCodeRepository, times(1)).existsById(promoCode);
    }

    @Test
    void validateEndDate_ShouldThrowValidationErrorException_WhenEndDateInPast() {
        LocalDate dateInPast = LocalDate.now().minusDays(1);

        assertThatThrownBy(() -> promoCodeValidator.validateEndDate(dateInPast))
                .isInstanceOf(ValidationErrorException.class);

        verifyNoInteractions(promoCodeRepository);
    }

    @Test
    void validateEndDate_ShouldNotThrowException_WhenEndDateIsCorrect() {
        LocalDate dateInFuture = LocalDate.now().plusDays(1);

        assertThatCode(() -> promoCodeValidator.validateEndDate(dateInFuture))
                .doesNotThrowAnyException();

        verifyNoInteractions(promoCodeRepository);
    }

    @Test
    void validatePromoCodeExpiration_ShouldThrowBadRequestException_WhenPromoCodeExpired() {
        LocalDate expirationDateInPast = LocalDate.now().minusDays(1);

        assertThatThrownBy(() -> promoCodeValidator.validatePromoCodeExpiration(expirationDateInPast))
                .isInstanceOf(BadRequestException.class);

        verifyNoInteractions(promoCodeRepository);
    }

    @Test
    void validatePromoCodeExpiration_ShouldNotThrowException_WhenEndDateIsCorrect() {
        LocalDate expirationDateInFuture = LocalDate.now().plusDays(1);

        assertThatCode(() -> promoCodeValidator.validatePromoCodeExpiration(expirationDateInFuture))
                .doesNotThrowAnyException();

        verifyNoInteractions(promoCodeRepository);
    }

    @Test
    void validatePromoCodeApplicationLimit_ShouldThrowBadRequestException_WhenPromoCodeApplicationLimitReached() {
        Long currentApplicationLimit = 0L;

        assertThatThrownBy(() -> promoCodeValidator.validatePromoCodeApplicationLimit(currentApplicationLimit))
                .isInstanceOf(BadRequestException.class);

        verifyNoInteractions(promoCodeRepository);
    }

    @Test
    void validatePromoCodeApplicationLimit_ShouldNotThrowException_WhenPromoCodeApplicationLimitNotReached() {
        Long currentApplicationLimit = 100L;

        assertThatCode(() -> promoCodeValidator.validatePromoCodeApplicationLimit(currentApplicationLimit))
                .doesNotThrowAnyException();

        verifyNoInteractions(promoCodeRepository);
    }
}
