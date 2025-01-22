package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.config.PayoutPolicyConfig;
import com.cabaggregator.rideservice.core.constant.OperationTranscriptTemplates;
import com.cabaggregator.rideservice.kafka.dto.BalanceOperationRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PayoutTestUtil {
    public static final int PERCENTAGE = 60;
    public static final long AMOUNT = (PriceTestUtil.PRICE * PERCENTAGE) / 100;
    public static final String TRANSCRIPT = String.format(
            OperationTranscriptTemplates.RIDE_PAYOUT, RideTestUtil.ID, RideTestUtil.ORDER_TIME);

    public static PayoutPolicyConfig.Policy buildPayoutPolicy() {
        PayoutPolicyConfig.Policy policy = new PayoutPolicyConfig.Policy();
        policy.setPercentage(PERCENTAGE);

        return policy;
    }

    public static BalanceOperationRequest buildBalanceOperationRequest() {
        return new BalanceOperationRequest(
                RideTestUtil.DRIVER_ID,
                AMOUNT,
                TRANSCRIPT);
    }
}
