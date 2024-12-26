package com.cabaggregator.paymentservice.util;

import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;
import com.cabaggregator.paymentservice.stripe.enums.StripePaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusMapper {
    public PaymentStatus fromStripeToBusiness(final StripePaymentStatus stripePaymentStatus) {
        return switch (stripePaymentStatus) {
            case StripePaymentStatus.SUCCEEDED -> PaymentStatus.SUCCEEDED;
            case StripePaymentStatus.CANCELED -> PaymentStatus.CANCELED;
            case StripePaymentStatus.PROCESSING, StripePaymentStatus.CREATED ->
                    PaymentStatus.PROCESSING;
            default -> PaymentStatus.FAILED;
        };
    }
}
