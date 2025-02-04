package com.cabaggregator.rideservice.client.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

public class OpenRouteApiClientInterceptor implements RequestInterceptor {
    @Value("${app.open-route.api-key}")
    private String apiKey;

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", apiKey);
    }
}
