package com.cabaggregator.paymentservice.stripe.exception;

import com.cabaggregator.paymentservice.core.constant.ApplicationMessages;
import com.cabaggregator.paymentservice.exception.BadRequestException;
import com.cabaggregator.paymentservice.exception.InternalErrorException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StripeExceptionHandler {
    public static void handle(StripeException exception) {
        if (exception instanceof CardException) {
            throw new BadRequestException(ApplicationMessages.CARD_PAYMENT_ERROR);
        } else if (exception instanceof InvalidRequestException) {
            throw new BadRequestException(ApplicationMessages.INVALID_REQUEST);
        } else {
            throw new InternalErrorException(exception);
        }
    }
}
