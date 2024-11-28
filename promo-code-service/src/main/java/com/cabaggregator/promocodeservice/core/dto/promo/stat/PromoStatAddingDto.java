package com.cabaggregator.promocodeservice.core.dto.promo.stat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PromoStatAddingDto(
        @NotNull
        UUID userId,

        @NotEmpty
        @Size(min = 2, max = 20)
        String promoCode
) {
}
