package com.cabaggregator.promocodeservice.util;

import com.cabaggregator.promocodeservice.entity.PromoStat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PromoStatTestUtil {
    public static final Long ID = 1L;
    public static final UUID USER_ID = UUID.fromString("4665e57c-884a-433d-8fd2-55078f29eab9");

    public static PromoStat buildPromoStat() {
        return PromoStat.builder()
                .id(ID)
                .userId(USER_ID)
                .promoCode(PromoCodeTestUtil.buildPromoCode())
                .build();
    }
}
