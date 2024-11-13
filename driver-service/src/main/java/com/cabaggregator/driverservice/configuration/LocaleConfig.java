package com.cabaggregator.driverservice.configuration;

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

    @Value("${spring.messages.basename.error-causes}")
    private String errorCausesMessageSourcePath;

    @Value("${spring.messages.basename.validation-errors}")
    private String validationErrorsMessageSourcePath;

    @Value("${spring.messages.basename.messages}")
    private String applicationMessagesSourcePath;

    @Value("${spring.messages.encoding}")
    private String messageEncoding;


    @Bean
    public MessageSource errorCausesMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(errorCausesMessageSourcePath);
        messageSource.setDefaultEncoding(messageEncoding);
        return messageSource;
    }

    @Bean
    public MessageSource validationErrorMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(validationErrorsMessageSourcePath);
        messageSource.setDefaultEncoding(messageEncoding);
        return messageSource;
    }

    @Bean
    public MessageSource applicationMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(applicationMessagesSourcePath);
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
