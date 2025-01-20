package com.cabaggregator.rideservice.kafka.util;

import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import com.cabaggregator.rideservice.kafka.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusConverter {

    public RidePaymentStatus convert(PaymentStatus paymentStatus) {
        return switch (paymentStatus) {
            case PROCESSING -> RidePaymentStatus.PENDING;
            case SUCCEEDED -> RidePaymentStatus.PAID;
            case CANCELED, FAILED -> RidePaymentStatus.DECLINED;
        };
    }
}
