package com.cabaggregator.ratingservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestAssuredObjectMapperConfig {

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void set() {
        RestAssured.config = RestAssuredConfig.config().newConfig()
                .objectMapperConfig(new io.restassured.config.ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((cl, type) -> objectMapper));
    }
}
