package com.albert.resumeAi.auth;

import com.albert.resumeAi.auth.dto.AuthResponse;
import com.albert.resumeAi.auth.dto.LoginRequest;
import com.albert.resumeAi.auth.dto.RegisterRequest;
import com.albert.resumeAi.user.User;
import com.albert.resumeAi.user.UserService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Auth business logic — the bridge between HTTP auth endpoints and the
 * user + JWT layers.
 *
 * Constructor-inject:
 *  - UserService      to create/find users
 *  - PasswordEncoder  to hash and verify passwords (Spring Security bean, defined in SecurityConfig)
 *  - JwtService       to generate tokens
 *
 * Methods to implement:
 *
 *   AuthResponse register(RegisterRequest request)
 *     1. Hash the plain-text password: passwordEncoder.encode(request.password()).
 *     2. Call userService.createUser(request.email(), request.displayName(), hash)
 *        to persist the new user.
 *     3. Generate a JWT: jwtService.generateToken(user.getId().toString()).
 *     4. Return a new AuthResponse(token, user.getId(), user.getEmail(), user.getDisplayName()).
 *     - If the email is already taken, UserService throws; let it bubble up.
 *
 *   AuthResponse login(LoginRequest request)
 *     1. Load the user by email: userService.findByEmail(request.email()).
 *        Throw if not found (use the same exception as "bad credentials" to avoid
 *        leaking whether the email exists).
 *     2. Verify: passwordEncoder.matches(request.password(), user.getPasswordHash()).
 *        Throw BadCredentialsException if false.
 *     3. Generate token and return AuthResponse — same as register step 3–4.
 *
 * Things NOT to do:
 *   - Don't return the raw User entity; always wrap in AuthResponse.
 *   - Don't do HTTP concerns (ResponseEntity, status codes) — that's the controller.
 *   - Don't hard-code the hashing algorithm; delegate entirely to PasswordEncoder.
 */
@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        // TODO: hash password
        // TODO: call userService.createUser(email, displayName, hash)
        // TODO: generate token via jwtService
        // TODO: return AuthResponse

        String hash = passwordEncoder.encode(request.password());

        User user = userService.createUser(
                request.email(),
                request.displayName(),
                hash
        );

        String token = jwtService.generateToken(user.getId().toString());

        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getDisplayName()
        );
    }

    public AuthResponse login(LoginRequest request) {
        // TODO: load user by email (throw on not found)
        // TODO: verify password hash (throw BadCredentialsException on mismatch)
        // TODO: generate token via jwtService
        // TODO: return AuthResponse

        // throw same error for non existing user and wrong password
        // attackers cant tell which is wrong

        User user;

        try {
            user = userService.findByEmail(request.email());
        } catch (NoSuchElementException e) {
            throw new BadCredentialsException("invalid Credentials");
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        String token = jwtService.generateToken(user.getId().toString());

        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getDisplayName()
        );

    }
}
