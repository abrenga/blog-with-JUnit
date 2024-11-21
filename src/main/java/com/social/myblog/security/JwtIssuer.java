package com.social.myblog.security;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.auth0.jwt.JWT;

@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties properties;



    public String issue(int userId, String username, List<String> roles) {

        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("email", username)
                .withClaim("roles", roles)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
}

