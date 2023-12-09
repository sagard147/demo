package com.crowdfund.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ApiKeyValidationFilter extends OncePerRequestFilter {
    private static final String SECRET_API_KEY = "crowdFundingUser";

    private boolean isValidApiKey(String apiKey) {
        // Your API key validation logic here
        // You can check against a pre-shared key or perform other validation
        return SECRET_API_KEY.equals(apiKey);
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {
        // Extract API key from request headers
        String apiKey = request.getHeader("Api-Key");
        if (isValidApiKey(apiKey)|| Objects.equals(request.getMethod(), "OPTIONS")) {
            // API key is valid, continue with the request
            filterChain.doFilter(request, response);
        } else {
            // Invalid API key, send unauthorized response
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
        }
    }
}
