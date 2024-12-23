package com.cabaggregator.pricecalculationservice.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherApiClientInterceptor implements RequestInterceptor {
    @Value("${app.weather.api-key}")
    private String apiKey;

    @Override
    public void apply(RequestTemplate template) {
        template.query("key", apiKey);
    }
}
