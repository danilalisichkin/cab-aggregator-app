package com.cabaggregator.rideservice.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {
    @Bean
    public MessageSource exceptionCausesMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:error_causes");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver ahlr = new AcceptHeaderLocaleResolver();
        ahlr.setDefaultLocale(Locale.ENGLISH);
        return ahlr;
    }
}
