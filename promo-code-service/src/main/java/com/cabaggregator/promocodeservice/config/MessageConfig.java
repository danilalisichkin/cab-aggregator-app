package com.cabaggregator.promocodeservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.messages.basename")
public class MessageConfig {
    private final String errorCauses;
    private final String messages;
    private final String encoding;
}
