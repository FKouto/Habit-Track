package com.projeto.api.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.projeto.api.model.Users;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "PLACEHOLDER"; // Replace with a secure key
    private final Algorithm algorithm;

    public JwtUtil() {
        this.algorithm = Algorithm.HMAC256(SECRET_KEY);
    }

    public String generateToken(Users user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .sign(algorithm);
    }
}
