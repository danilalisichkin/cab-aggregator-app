package com.cabaggregator.authservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {
    private final String errorCausesMessageSourcePath;
    private final String validationErrorsMessageSourcePath;
    private final String applicationMessagesSourcePath;
    private final String messageEncoding;

    public LocaleConfig(
            @Value("${spring.messages.basename.error-causes}")
            String errorCausesMessageSourcePath,
            @Value("${spring.messages.basename.validation-errors}")
            String validationErrorsMessageSourcePath,
            @Value("${spring.messages.basename.messages}")
            String applicationMessagesSourcePath,
            @Value("${spring.messages.encoding}")
            String messageEncoding) {
        this.errorCausesMessageSourcePath = errorCausesMessageSourcePath;
        this.validationErrorsMessageSourcePath = validationErrorsMessageSourcePath;
        this.applicationMessagesSourcePath = applicationMessagesSourcePath;
        this.messageEncoding = messageEncoding;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames(
                errorCausesMessageSourcePath,
                validationErrorsMessageSourcePath,
                applicationMessagesSourcePath);
        messageSource.setDefaultEncoding(messageEncoding);
        return messageSource;
    }

    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver ahlr = new AcceptHeaderLocaleResolver();
        ahlr.setDefaultLocale(Locale.ENGLISH);
        return ahlr;
    }
}
