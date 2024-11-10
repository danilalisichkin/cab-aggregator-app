package com.cabaggregator.rideservice;

import com.cabaggregator.rideservice.core.dto.promo.PromoCodeAddingDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.promo.RidePromoCodeDto;
import com.cabaggregator.rideservice.entity.PromoCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PromoCodeTestUtil {
    public static final ObjectId PROMO_CODE_ID = new ObjectId();
    public static final String VALUE = "PROMO2024";
    public static final Integer DISCOUNT = 5;
    public static final LocalDateTime START_DATE = LocalDateTime.of(2024, 1, 1, 0, 0);
    public static final LocalDateTime END_DATE = LocalDateTime.of(2024, 12, 31, 23, 59);

    public static final Integer UPDATED_DISCOUNT = 10;
    public static final LocalDateTime UPDATED_START_DATE = LocalDateTime.of(2025, 1, 1, 0, 0);
    public static final LocalDateTime UPDATED_END_DATE = LocalDateTime.of(2025, 12, 31, 23, 59);

    public static PromoCode buildPromoCode() {
        return PromoCode.builder()
                .id(PROMO_CODE_ID)
                .value(VALUE)
                .discount(DISCOUNT)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();
    }

    public static PromoCodeDto buildPromoCodeDto() {
        return new PromoCodeDto(
                PROMO_CODE_ID,
                VALUE,
                DISCOUNT,
                START_DATE,
                END_DATE);
    }

    public static PromoCodeUpdatingDto buildPromoCodeUpdatingDto() {
        return new PromoCodeUpdatingDto(
                UPDATED_DISCOUNT,
                UPDATED_START_DATE,
                UPDATED_END_DATE);
    }

    public static PromoCodeAddingDto buildPromoCodeAddingDto() {
        return new PromoCodeAddingDto(
                VALUE,
                DISCOUNT,
                START_DATE,
                END_DATE);
    }

    public static RidePromoCodeDto buildRidePromoCodeDto() {
        return new RidePromoCodeDto(
                VALUE,
                DISCOUNT
        );
    }
}
