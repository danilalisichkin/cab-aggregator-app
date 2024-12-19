package com.cabaggregator.promocodeservice.core.dto.promo.stat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Entry to add new promo code stat")
public record PromoStatAddingDto(
        @NotNull
        UUID userId,

        @NotEmpty
        @Size(min = 2, max = 20)
        String promoCode
) {
}
