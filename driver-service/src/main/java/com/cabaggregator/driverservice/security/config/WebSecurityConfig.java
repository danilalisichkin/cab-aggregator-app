package com.cabaggregator.driverservice.security.config;

import com.cabaggregator.driverservice.security.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    /**
     * Configures the security settings for the application, including the JWT-based authentication filter.
     *
     * <p>This configuration disables CSRF protection and configures the application to be stateless by setting the
     * session creation policy to {@code SessionCreationPolicy.STATELESS}. It also specifies which HTTP endpoints
     * should be publicly accessible (e.g., Swagger UI, API documentation), while requiring authentication for all
     * other endpoints.</p>
     *
     * <p>The method adds a custom {@link JwtFilter} before the {@link UsernamePasswordAuthenticationFilter}
     * to handle JWT token extraction and user authentication. Additionally, it sets up a security filter chain
     * to control access to the application.</p>
     *
     * @param http The {@link HttpSecurity} object used to configure the security settings for the application.
     * @return A {@link SecurityFilterChain} that defines the security rules for the application.
     * @throws Exception If any error occurs while configuring HTTP security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(c -> c
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/webjars/**",
                                "/actuator/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}
