package com.cabaggregator.driverservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageBuilder {
    private final MessageSource messageSource;

    @Autowired
    public MessageBuilder(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String buildLocalizedMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}
