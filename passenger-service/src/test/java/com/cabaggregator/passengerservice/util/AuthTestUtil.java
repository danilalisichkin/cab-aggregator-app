package com.cabaggregator.passengerservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthTestUtil {
    @Value("${auth.passenger-token}")
    private String passengerToken;

    @Value("${auth.admin-token}")
    private String adminToken;

    public String getPassengerBearerToken() {
        return "Bearer " + passengerToken;
    }

    public String getAdminBearerToken() {
        return "Bearer " + adminToken;
    }
}
