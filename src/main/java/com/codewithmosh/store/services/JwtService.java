package com.codewithmosh.store.services;

import com.codewithmosh.store.configuration.JwtConfig;
import com.codewithmosh.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtConfig config;


    public String generateAccessToken(User user) {
        return generateToken(user, config.getAccessTokenExpiration());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, config.getRefreshTokenExpiration());
    }

    private String generateToken(User user, long timeToLive) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + timeToLive * 1000))
                .signWith(config.getSecretKey())
                .compact();
    }

    public boolean isValidToken(String token) {
        try {
            var claims = getClaims(token);
            return !claims.getExpiration().after(new Date());
        } catch(JwtException e) {
            return true;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(config.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }
    
    
}
