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
                .authorizeExchange(
                        ex -> ex
                                // User API in auth-service
                                .pathMatchers("/api/v1/users/**")
                                .hasAuthority("ADMIN")

                                // Auth API in auth-service
                                .pathMatchers("/api/v1/auth/**")
                                .permitAll()

                                // Passenger API in passenger-service
                                .pathMatchers("/api/v1/passengers")
                                .hasAnyAuthority("ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/passengers/{id}")
                                .hasAnyAuthority("PASSENGER", "ADMIN")
                                .pathMatchers(HttpMethod.PUT, "/api/v1/passengers/{id}")
                                .hasAnyAuthority("PASSENGER", "ADMIN")
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/passengers/{id}")
                                .hasAnyAuthority("ADMIN")

                                // Driver API in driver-service
                                .pathMatchers("/api/v1/drivers")
                                .hasAuthority("ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/drivers/{id}")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.PUT, "/api/v1/drivers/{id}")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.PATCH, "/api/v1/drivers/{id}/car")
                                .hasAnyAuthority("DRIVER", "ADMIN")

                                // Car API in car-service
                                .pathMatchers(HttpMethod.GET, "/api/v1/cars")
                                .hasAuthority("ADMIN")
                                .pathMatchers(HttpMethod.POST, "/api/v1/cars")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/cars/{id}")
                                .authenticated()
                                .pathMatchers(HttpMethod.PUT, "/api/v1/cars/{id}")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/cars/{id}")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/cars/{id}/full")
                                .authenticated()
                                .pathMatchers(HttpMethod.PUT, "/api/v1/cars/{id}/details")
                                .hasAnyAuthority("DRIVER", "ADMIN")

                                // Ride General API in ride-service
                                .pathMatchers(HttpMethod.GET, "/api/v1/rides")
                                .hasAuthority("ADMIN")
                                .pathMatchers(HttpMethod.POST, "/api/v1/rides")
                                .hasAuthority("PASSENGER")
                                .pathMatchers(HttpMethod.GET, "/api/v1/rides/{id}")
                                .hasAuthority("ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/rides/requests")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.PUT, "/api/v1/rides/requests/{id}")
                                .hasAuthority("DRIVER")

                                // Driver Ride API in ride-service
                                .pathMatchers(HttpMethod.GET, "/api/v1/drivers/{driverId}/rides")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/drivers/{driverId}/rides/{id}")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.PATCH, "/api/v1/drivers/{driverId}/rides/{id}/payment-status")
                                .hasAuthority("DRIVER")
                                .pathMatchers(HttpMethod.PATCH, "/api/v1/drivers/{driverId}/rides/{id}/status")
                                .hasAnyAuthority("DRIVER")

                                // Passenger Ride API in ride-service
                                .pathMatchers(HttpMethod.GET, "/api/v1/passengers/{passengerId}/rides")
                                .hasAnyAuthority("PASSENGER", "ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/passengers/{passengerId}/rides/{id}")
                                .hasAnyAuthority("PASSENGER", "ADMIN")
                                .pathMatchers(HttpMethod.PATCH, "/api/v1/passengers/{passengerId}/rides/{id}/status")
                                .hasAnyAuthority("PASSENGER")

                                // Promo Code API in promo-code-service
                                .pathMatchers("/api/v1/promo-codes/**")
                                .hasAuthority("ADMIN")

                                // Promo Stat API in promo-code-service
                                .pathMatchers("/api/v1/promo-stats/**")
                                .hasAuthority("ADMIN")

                                // Driver Rate API in rating-service
                                .pathMatchers(HttpMethod.GET, "/api/v1/rates/driver/{driverId}")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.POST, "/api/v1/rates/driver/{driverId}")
                                .hasAuthority("ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/rates/driver/{driverId}/average")
                                .authenticated()
                                .pathMatchers(HttpMethod.GET, "/api/v1/rates/driver/{driverId}/ride/{rideId}")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.PUT, "/api/v1/rates/driver/{driverId}/ride/{rideId}")
                                .hasAuthority("PASSENGER")

                                // Passenger Rate API in rating-service
                                .pathMatchers(HttpMethod.GET, "/api/v1/rates/passenger/{passengerId}")
                                .hasAnyAuthority("PASSENGER", "ADMIN")
                                .pathMatchers(HttpMethod.POST, "/api/v1/rates/passenger/{passengerId}")
                                .hasAuthority("ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/rates/passenger/{passengerId}/average")
                                .authenticated()
                                .pathMatchers(HttpMethod.GET, "/api/v1/rates/passenger/{passengerId}/ride/{rideId}")
                                .hasAnyAuthority("PASSENGER", "ADMIN")
                                .pathMatchers(HttpMethod.PUT, "/api/v1/rates/passenger/{passengerId}/ride/{rideId}")
                                .hasAuthority("DRIVER")

                                // Pricing API in price-calculation-service
                                .pathMatchers(HttpMethod.POST, "/api/v1/pricing")
                                .hasAuthority("ADMIN")

                                // Payment Account API in payment-service
                                .pathMatchers(HttpMethod.GET, "/api/v1/payment-accounts")
                                .hasAuthority("ADMIN")
                                .pathMatchers(HttpMethod.POST, "/api/v1/payment-accounts")
                                .hasAuthority("PASSENGER")
                                .pathMatchers(HttpMethod.GET, "/api/v1/payment-accounts/{id}")
                                .hasAnyAuthority("PASSENGER", "ADMIN")
                                .pathMatchers(HttpMethod.PUT, "/api/v1/payment-accounts/{id}")
                                .hasAuthority("PASSENGER")
                                .pathMatchers(HttpMethod.GET, "/api/v1/payment-accounts/{id}/payment-cards")
                                .hasAnyAuthority("PASSENGER", "ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/payment-accounts/{id}/payment-cards/default")
                                .hasAnyAuthority("PASSENGER", "ADMIN")
                                .pathMatchers(HttpMethod.POST, "/api/v1/payment-accounts/{id}/payment-cards/default")
                                .hasAuthority("PASSENGER")

                                // Payment API in payment-service
                                .pathMatchers(HttpMethod.POST, "/api/v1/payments")
                                .hasAuthority("PASSENGER")
                                .pathMatchers(HttpMethod.GET, "/api/v1/payments/ride/{rideId}")
                                .hasAnyAuthority("DRIVER", "ADMIN")

                                // Payout Account API in payout-service
                                .pathMatchers(HttpMethod.GET, "/api/v1/payout-accounts")
                                .hasAuthority("ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/payout-accounts/{id}")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.PUT, "/api/v1/payout-accounts/{id}")
                                .hasAuthority("DRIVER")
                                .pathMatchers(HttpMethod.GET, "/api/v1/payout-accounts/{id}/balance")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.PATCH, "/api/v1/payout-accounts/{id}/activate")
                                .hasAuthority("ADMIN")
                                .pathMatchers(HttpMethod.PATCH, "/api/v1/payout-accounts/{id}/deactivate")
                                .hasAuthority("ADMIN")
                                .pathMatchers(HttpMethod.GET, "/api/v1/payout-accounts/{id}/balance-operations")
                                .hasAnyAuthority("DRIVER", "ADMIN")
                                .pathMatchers(HttpMethod.POST, "/api/v1/payout-accounts")
                                .hasAuthority("DRIVER")
                                .pathMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/payout-accounts/{id}/balance-operations/deposit",
                                        "/api/v1/payout-accounts/{id}/balance-operations/bonus",
                                        "/api/v1/payout-accounts/{id}/balance-operations/fine")
                                .hasAuthority("ADMIN")
                                .pathMatchers(
                                        HttpMethod.POST,
                                        "/api/v1/payout-accounts/{id}/balance-operations/withdrawal")
                                .hasAuthority("DRIVER")

                                .anyExchange()
                                .authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .build();
    }

    private ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
        var authConverter = new AuthenticationConverter();

        return new ReactiveJwtAuthenticationConverterAdapter(authConverter);
    }
}