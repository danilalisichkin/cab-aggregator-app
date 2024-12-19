package com.cabaggregator.promocodeservice.core.dto.promo.stat;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Server response with promo code stat data")
public record PromoStatDto(
        Long id,
        UUID userId,
        String promoCode
) {
}
