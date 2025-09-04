package com.Nextera.ProNetService.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {


    // Store a BASE64-encoded secret in application.properties/yaml
    // e.g. jwt.secret=<openssl rand -base64 64>
    @Value("${jwt.secret}")
    private String base64Secret;

    // 1 hour
    @Value("${jwt.expiry-ms:3600000}")
    private long expiryMs;

    private SecretKey key() {
        // base64 → bytes → HMAC key (≥64 bytes for HS512)
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
    }

    public String generateToken(String subjectEmail) {
        final Date now = new Date();
        final Date exp = new Date(now.getTime() + expiryMs);

        return Jwts.builder()
                .setSubject(subjectEmail)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, String expectedEmail) {
        try {
            String email = extractEmail(token);
            return email != null
                    && email.equals(expectedEmail)
                    && !isExpired(token);
        } catch (Exception ex) {
            return false; // signature, malformed, expired, etc.
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolver.apply(claims);
    }

    public boolean isExpired(String token) {
        Date exp = extractClaim(token, Claims::getExpiration);
        return exp != null && exp.before(new Date());
    }
}
