package com.projeto.api.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
    private static final String SECRET_KEY = "your-secret-key"; // Replace with a secure key

    @Bean
    public Algorithm jwtAlgorithm() {
        return Algorithm.HMAC256(SECRET_KEY);
    }
}
