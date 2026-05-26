package com.albert.resumeAi.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

/**
 * Encapsulates all JWT operations so no other class touches the JWT library directly.
 *
 * Implementation notes (JJWT 0.12.x):
 *  - Load the secret key from application.properties (app.jwt.secret).
 *    Use @Value("${app.jwt.secret}") to inject it as a String, then convert
 *    to a SecretKey via Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)).
 *  - Load the expiry from application.properties (app.jwt.expiry-ms, e.g. 86400000 for 24 h).
 *
 * Methods to implement:
 *
 *   String generateToken(String subject)
 *     - subject is the user's UUID as a string.
 *     - Set iat (issued-at) = now, exp = now + expiryMs.
 *     - Sign with HS256 and the SecretKey.
 *     - Return the compact JWT string.
 *
 *   String extractSubject(String token)
 *     - Parse and validate the token.
 *     - Return the subject claim (user UUID string).
 *     - JJWT throws JwtException subtypes on invalid/expired tokens —
 *       let them propagate; JwtAuthFilter will catch them.
 *
 *   boolean isTokenValid(String token)
 *     - Try extractSubject(); return true if no exception is thrown.
 *     - Useful for a quick validity check in JwtAuthFilter.
 *
 * Things NOT to do:
 *   - Don't hard-code the secret in this file. It must come from config.
 *   - Don't store tokens in the DB — JWTs are stateless.
 *   - Don't add non-standard claims unless you need them (keep the payload small).
 */
@Service
public class JwtService {

    // TODO: @Value("${app.jwt.secret}") private String secret;
    // TODO: @Value("${app.jwt.expiry-ms}") private long expiryMs;
    // TODO: derive SecretKey from secret (in @PostConstruct or lazily)

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiry-ms}")
    private long expiryMs;

    private SecretKey signingKey;

    @PostConstruct
    private void init() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secret); // deocodes secret
        signingKey = Keys.hmacShaKeyFor(keyBytes); // stores
    }


    public String generateToken(String subject) {
        // TODO: build and sign a JWT using JJWT Jwts.builder()
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(subject)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expiryMs)))
                .signWith(signingKey)
                .compact();
    }

        // TODO: parse the token and return the subject claim
        public String extractSubject(String token) {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }

    public boolean isTokenValid(String token) {
        // TODO: return true if extractSubject() succeeds without throwing
        try {
            extractSubject(token);
            return true;
        } catch (JwtException e){
            return false;
        }
    }
}
