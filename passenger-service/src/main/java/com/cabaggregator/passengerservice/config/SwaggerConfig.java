package com.cabaggregator.passengerservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Passengers API documentation",
                description = "API responsible for interaction with passengers", version = "1.0.0",
                contact = @Contact(
                        name = "Danila Lisichkin",
                        email = "lisichkindanila@gmail.com"
                )
        )
)
@Configuration
public class SwaggerConfig {
}
