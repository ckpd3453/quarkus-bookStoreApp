package org.raku.utility;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;

@ApplicationScoped
public class JwtService {

    public String generateToken(String email, String role) {

        return Jwt
                .issuer("quarkus-app")
                .subject(email)
                .groups(role)
                .expiresIn(Duration.ofMinutes(1))
                .sign();
    }
}