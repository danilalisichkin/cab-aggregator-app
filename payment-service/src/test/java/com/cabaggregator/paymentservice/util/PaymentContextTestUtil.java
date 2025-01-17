package com.cabaggregator.paymentservice.util;

import com.cabaggregator.paymentservice.entity.PaymentContext;
import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentContextTestUtil {
    public static final Long ID = 1L;
    public static final String CONTEXT_ID = "507f1f77bcf86cd799439011";
    public static final PaymentContextType TYPE = PaymentContextType.RIDE;

    public static final String NOT_EXISTING_CONTEXT_ID = "000000000000000000000000";

    public static PaymentContext buildDefaultPaymentContext() {
        return PaymentContext.builder()
                .id(ID)
                .contextId(CONTEXT_ID)
                .payment(PaymentTestUtil.buildDefaultPayment())
                .type(TYPE)
                .build();
    }
}
