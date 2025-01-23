package com.cabaggregator.driverservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthTestUtil {
    @Value("${auth.driver-token}")
    private String driverToken;

    @Value("${auth.admin-token}")
    private String adminToken;

    public String getDriverBearerToken() {
        return "Bearer " + driverToken;
    }

    public String getAdminBearerToken() {
        return "Bearer " + adminToken;
    }
}
