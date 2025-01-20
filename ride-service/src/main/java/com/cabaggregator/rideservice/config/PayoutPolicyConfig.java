package com.cabaggregator.rideservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.payout-policies")
public class PayoutPolicyConfig {
    private final Policy economy = new Policy();
    private final Policy comfort = new Policy();
    private final Policy business = new Policy();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Policy {
        private int percentage;
    }
}
