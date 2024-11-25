package com.cabaggregator.promocodeservice.core.dto.promo.stat;

import java.util.UUID;

public record PromoStatAddingDto(
        UUID userId,
        String promoCode
) {
}
