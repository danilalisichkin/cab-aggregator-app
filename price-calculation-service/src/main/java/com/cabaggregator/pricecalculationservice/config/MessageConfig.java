package com.cabaggregator.pricecalculationservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.messages.basename")
public class MessageConfig {
    private final String errorCauses;
    private final String validationErrors;
    private final String encoding;
}
