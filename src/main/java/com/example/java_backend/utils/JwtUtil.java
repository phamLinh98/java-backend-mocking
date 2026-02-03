package com.example.java_backend.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate Access Token
     */
    public String generateAccessToken(Long userId, String username) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("username", username)
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Generate Refresh Token
     */
    public String generateRefreshToken(Long userId, String username) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("username", username)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Validate token and return Claims
     */
    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired");
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token");
        }
    }

    /**
     * Extract user ID from token
     */
    public Long extractUserId(String token) {
        Claims claims = validateToken(token);
        return Long.parseLong(claims.getSubject());
    }

    /**
     * Extract username from token
     */
    public String extractUsername(String token) {
        Claims claims = validateToken(token);
        return claims.get("username", String.class);
    }

    /**
     * Check if token is expired
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = validateToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Verify token type (access or refresh)
     */
    public boolean isTokenType(String token, String type) {
        try {
            Claims claims = validateToken(token);
            return type.equals(claims.get("type", String.class));
        } catch (Exception e) {
            return false;
        }
    }
}
