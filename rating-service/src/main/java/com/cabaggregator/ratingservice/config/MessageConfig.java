package com.cabaggregator.ratingservice.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ConfigurationProperties(prefix = "spring.messages.basename")
public class MessageConfig {
    String errorCauses;
    String messages;
    String encoding;
}
