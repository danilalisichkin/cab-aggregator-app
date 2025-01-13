package com.cabaggregator.promocodeservice.util;

import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.entity.PromoCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PromoCodeTestUtil {
    public static final String VALUE = "PROMO2024";
    public static final Integer DISCOUNT_PERCENTAGE = 5;
    public static final LocalDate END_DATE = LocalDate.of(2024, 12, 31);
    public static final Long LIMIT = 1000L;

    public static final Integer UPDATED_DISCOUNT_PERCENTAGE = 10;
    public static final LocalDate UPDATED_END_DATE = LocalDate.of(2025, 12, 31);
    public static final Long UPDATED_LIMITS = 100L;

    public static final String NOT_EXISTING_CODE = "NOT_EXISTING_CODE";

    public static PromoCode buildDefaultPromoCode() {
        return PromoCode.builder()
                .value(VALUE)
                .discountPercentage(DISCOUNT_PERCENTAGE)
                .endDate(END_DATE)
                .limit(LIMIT)
                .build();
    }

    public static PromoCodeDto buildPromoCodeDto() {
        return new PromoCodeDto(
                VALUE,
                DISCOUNT_PERCENTAGE,
                END_DATE,
                LIMIT);
    }

    public static PromoCodeUpdatingDto buildPromoCodeUpdatingDto() {
        return new PromoCodeUpdatingDto(
                UPDATED_DISCOUNT_PERCENTAGE,
                UPDATED_END_DATE,
                UPDATED_LIMITS);
    }

    public static PromoCodeAddingDto buildPromoCodeAddingDto() {
        return new PromoCodeAddingDto(
                VALUE,
                DISCOUNT_PERCENTAGE,
                END_DATE,
                LIMIT);
    }
}

