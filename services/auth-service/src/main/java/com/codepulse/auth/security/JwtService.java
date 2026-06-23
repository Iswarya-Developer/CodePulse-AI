package com.codepulse.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Generate JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, String username) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extract Username
     */
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    /**
     * Extract Expiration
     */
    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);

    }

    /**
     * Generic Claim Extractor
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);

    }

    /**
     * Validate Token
     */
    public boolean isTokenValid(
            String token,
            String username) {

        return username.equals(extractUsername(token))
                && !isTokenExpired(token);

    }

    /**
     * Check Expiry
     */
    private boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());

    }

    /**
     * Parse Claims
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    /**
     * Signing Key
     */
    private SecretKey getSigningKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);

    }

}