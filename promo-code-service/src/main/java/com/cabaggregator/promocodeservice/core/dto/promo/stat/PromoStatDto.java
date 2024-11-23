package com.cabaggregator.promocodeservice.core.dto.promo.stat;

import java.util.UUID;

public record PromoStatDto(
        Long id,
        UUID userId,
        String promoCode
) {
}
