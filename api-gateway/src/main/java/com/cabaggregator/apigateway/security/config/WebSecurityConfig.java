package com.cabaggregator.apigateway.security.config;

import com.cabaggregator.apigateway.security.util.AuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex
                        // Actuator & metrics
                        .pathMatchers("/actuator/prometheus/**")
                        .permitAll()

                        // User API in auth-service
                        .pathMatchers("/api/v1/users/**")
                        .hasRole("ADMIN")

                        // Auth API in auth-service
                        .pathMatchers("/api/v1/auth/**")
                        .permitAll()

                        // Passenger API in passenger-service
                        .pathMatchers("/api/v1/passengers")
                        .hasAnyRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/passengers/{id}")
                        .hasAnyRole("PASSENGER", "ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/passengers/{id}")
                        .hasAnyRole("PASSENGER", "ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/passengers/{id}")
                        .hasAnyRole("ADMIN")

                        // Driver API in driver-service
                        .pathMatchers("/api/v1/drivers")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/drivers/{id}")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/drivers/{id}")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/api/v1/drivers/{id}/car")
                        .hasAnyRole("DRIVER", "ADMIN")

                        // Car API in car-service
                        .pathMatchers(HttpMethod.GET, "/api/v1/cars")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.POST, "/api/v1/cars")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/cars/{id}")
                        .authenticated()
                        .pathMatchers(HttpMethod.PUT, "/api/v1/cars/{id}")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/api/v1/cars/{id}")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/cars/{id}/full")
                        .authenticated()
                        .pathMatchers(HttpMethod.PUT, "/api/v1/cars/{id}/details")
                        .hasAnyRole("DRIVER", "ADMIN")

                        // Ride General API in ride-service
                        .pathMatchers(HttpMethod.GET, "/api/v1/rides")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.POST, "/api/v1/rides")
                        .hasRole("PASSENGER")
                        .pathMatchers(HttpMethod.GET, "/api/v1/rides/{id}")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/rides/requests")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/rides/requests/{id}")
                        .hasRole("DRIVER")

                        // Driver Ride API in ride-service
                        .pathMatchers(HttpMethod.GET, "/api/v1/drivers/{driverId}/rides")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/drivers/{driverId}/rides/{id}")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/api/v1/drivers/{driverId}/rides/{id}/payment-status")
                        .hasRole("DRIVER")
                        .pathMatchers(HttpMethod.PATCH, "/api/v1/drivers/{driverId}/rides/{id}/status")
                        .hasAnyRole("DRIVER")

                        // Passenger Ride API in ride-service
                        .pathMatchers(HttpMethod.GET, "/api/v1/passengers/{passengerId}/rides")
                        .hasAnyRole("PASSENGER", "ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/passengers/{passengerId}/rides/{id}")
                        .hasAnyRole("PASSENGER", "ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/api/v1/passengers/{passengerId}/rides/{id}/status")
                        .hasAnyRole("PASSENGER")

                        // Promo Code API in promo-code-service
                        .pathMatchers("/api/v1/promo-codes/**")
                        .hasRole("ADMIN")

                        // Promo Stat API in promo-code-service
                        .pathMatchers("/api/v1/promo-stats/**")
                        .hasRole("ADMIN")

                        // Driver Rate API in rating-service
                        .pathMatchers(HttpMethod.GET, "/api/v1/rates/driver/{driverId}")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.POST, "/api/v1/rates/driver/{driverId}")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/rates/driver/{driverId}/average")
                        .authenticated()
                        .pathMatchers(HttpMethod.GET, "/api/v1/rates/driver/{driverId}/ride/{rideId}")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/rates/driver/{driverId}/ride/{rideId}")
                        .hasRole("PASSENGER")

                        // Passenger Rate API in rating-service
                        .pathMatchers(HttpMethod.GET, "/api/v1/rates/passenger/{passengerId}")
                        .hasAnyRole("PASSENGER", "ADMIN")
                        .pathMatchers(HttpMethod.POST, "/api/v1/rates/passenger/{passengerId}")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/rates/passenger/{passengerId}/average")
                        .authenticated()
                        .pathMatchers(HttpMethod.GET, "/api/v1/rates/passenger/{passengerId}/ride/{rideId}")
                        .hasAnyRole("PASSENGER", "ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/rates/passenger/{passengerId}/ride/{rideId}")
                        .hasRole("DRIVER")

                        // Pricing API in price-calculation-service
                        .pathMatchers(HttpMethod.POST, "/api/v1/pricing")
                        .hasRole("ADMIN")

                        // Payment Account API in payment-service
                        .pathMatchers(HttpMethod.GET, "/api/v1/payment-accounts")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.POST, "/api/v1/payment-accounts")
                        .hasRole("PASSENGER")
                        .pathMatchers(HttpMethod.GET, "/api/v1/payment-accounts/{id}")
                        .hasAnyRole("PASSENGER", "ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/payment-accounts/{id}")
                        .hasRole("PASSENGER")
                        .pathMatchers(HttpMethod.GET, "/api/v1/payment-accounts/{id}/payment-cards")
                        .hasAnyRole("PASSENGER", "ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/payment-accounts/{id}/payment-cards/default")
                        .hasAnyRole("PASSENGER", "ADMIN")
                        .pathMatchers(HttpMethod.POST, "/api/v1/payment-accounts/{id}/payment-cards/default")
                        .hasRole("PASSENGER")

                        // Payment API in payment-service
                        .pathMatchers(HttpMethod.POST, "/api/v1/payments")
                        .hasRole("PASSENGER")
                        .pathMatchers(HttpMethod.GET, "/api/v1/payments/ride/{rideId}")
                        .hasAnyRole("DRIVER", "ADMIN")

                        // Payout Account API in payout-service
                        .pathMatchers(HttpMethod.GET, "/api/v1/payout-accounts")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/payout-accounts/{id}")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/v1/payout-accounts/{id}")
                        .hasRole("DRIVER")
                        .pathMatchers(HttpMethod.GET, "/api/v1/payout-accounts/{id}/balance")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/api/v1/payout-accounts/{id}/activate")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PATCH, "/api/v1/payout-accounts/{id}/deactivate")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/v1/payout-accounts/{id}/balance-operations")
                        .hasAnyRole("DRIVER", "ADMIN")
                        .pathMatchers(HttpMethod.POST, "/api/v1/payout-accounts")
                        .hasRole("DRIVER")
                        .pathMatchers(
                                HttpMethod.POST,
                                "/api/v1/payout-accounts/{id}/balance-operations/deposit",
                                "/api/v1/payout-accounts/{id}/balance-operations/bonus",
                                "/api/v1/payout-accounts/{id}/balance-operations/fine")
                        .hasRole("ADMIN")
                        .pathMatchers(
                                HttpMethod.POST,
                                "/api/v1/payout-accounts/{id}/balance-operations/withdrawal")
                        .hasRole("DRIVER")

                        .anyExchange()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .build();
    }

    private ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
        var authConverter = new AuthenticationConverter();

        return new ReactiveJwtAuthenticationConverterAdapter(authConverter);
    }
}