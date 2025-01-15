package com.cabaggregator.promocodeservice.util;

import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatDto;
import com.cabaggregator.promocodeservice.entity.PromoStat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PromoStatTestUtil {
    public static final Long ID = 1L;
    public static final UUID USER_ID = UUID.fromString("1000e57c-114a-433d-6ac2-55048a29eab9");

    public static final UUID OTHER_USER_ID = UUID.fromString("d3b2f3c0-1a1e-4a3f-b6dc-6c5a7f8e0f3a");

    public static PromoStat buildDefaultPromoStat() {
        return PromoStat.builder()
                .id(ID)
                .userId(USER_ID)
                .promoCode(PromoCodeTestUtil.buildDefaultPromoCode())
                .build();
    }

    public static PromoStatDto buildPromoStatDto() {
        return new PromoStatDto(
                ID,
                USER_ID,
                PromoCodeTestUtil.VALUE);
    }

    public static PromoStatAddingDto buildPromoStatAddingDto() {
        return new PromoStatAddingDto(
                USER_ID,
                PromoCodeTestUtil.VALUE);
    }
}
