package com.cabaggregator.driverservice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Driver service documentation",
                description = "API responsible for interaction with drivers, cars and it details", version = "1.0.0",
                contact = @Contact(
                        name = "Danila Lisichkin",
                        email = "lisichkindanila@gmail.com"
                )
        )
)
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi carApi() {
        return GroupedOpenApi.builder()
                .group("car")
                .pathsToMatch("/api/v1/cars/**")
                .build();
    }

    @Bean
    public GroupedOpenApi driverApi() {
        return GroupedOpenApi.builder()
                .group("driver")
                .pathsToMatch("/api/v1/drivers/**")
                .build();
    }
}
