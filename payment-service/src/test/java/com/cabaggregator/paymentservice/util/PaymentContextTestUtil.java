package com.cabaggregator.paymentservice.util;

import com.cabaggregator.paymentservice.entity.PaymentContext;
import com.cabaggregator.paymentservice.entity.enums.PaymentContextType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaymentContextTestUtil {
    public static final Long ID = 1L;
    public static final String CONTEXT_ID = "60c72b2f9b1e8c001c8d5a3f";
    public static final PaymentContextType TYPE = PaymentContextType.RIDE;

    public static final String NOT_EXISTING_CONTEXT_ID = "not_existing_id";

    public static PaymentContext buildDefaultPaymentContext() {
        return PaymentContext.builder()
                .id(ID)
                .contextId(CONTEXT_ID)
                .payment(PaymentTestUtil.getPaymentBuilder().build())
                .type(TYPE)
                .build();
    }
}
