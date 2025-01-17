package com.cabaggregator.ratingservice.security.filter;

import com.cabaggregator.ratingservice.security.util.JwtClaimsExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class JwtFilter extends OncePerRequestFilter {

    /**
     * Filters incoming requests to extract JWT token from the Authorization header,
     * parses it to extract user credentials, and sets the authentication context in Spring Security.
     * This allows the application to access user credentials (such as roles) throughout the request lifecycle.
     *
     * @param request The incoming HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain to pass the request and response to the next filter or servlet.
     * @throws ServletException If an error occurs during the filtering process.
     * @throws IOException If an I/O error occurs while processing the request or response.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            List<GrantedAuthority> authorities = JwtClaimsExtractor.extractUserRoles(token);
            UUID userId = UUID.fromString(JwtClaimsExtractor.extractUserId(token));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
