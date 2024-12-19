package com.cabaggregator.pricecalculationservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.cache.ttl")
public class CacheTtlConfig {
    private Integer fareCache;
    private Integer weatherCache;
}
