package com.cabaggregator.payoutservice.stripe.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "stripe")
public class StripeConfig {
    private final String secretKey;

    @PostConstruct
    public void setApiKey() {
        Stripe.apiKey = secretKey;
    }
}
