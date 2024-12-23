package com.cabaggregator.pricecalculationservice.config.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.redis.key")
public class RedisKeyConfig {
    private final Integer ttl;
    private final Prefix prefix;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Prefix {
        private String cellDemand;
        private String cell;
        private String ride;
    }
}
