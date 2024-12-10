package com.cabaggregator.paymentservice.core.dto.payment;

import com.cabaggregator.paymentservice.core.constant.ValidationErrors;
import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PaymentRequest(
        @NotNull
        UUID paymentAccountId,

        @NotNull
        @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
        Long unitAmount,

        @NotEmpty
        @Size(min = 1, max = 40, message = ValidationErrors.INVALID_STRING_LENGTH)
        String paymentMethodId,

        @NotNull
        PaymentContextType context,

        @NotEmpty
        @Size(min = 1, max = 50, message = ValidationErrors.INVALID_STRING_LENGTH)
        String contextId
) {
}
