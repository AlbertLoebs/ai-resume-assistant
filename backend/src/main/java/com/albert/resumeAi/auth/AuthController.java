package com.albert.resumeAi.auth;

import com.albert.resumeAi.auth.dto.AuthResponse;
import com.albert.resumeAi.auth.dto.LoginRequest;
import com.albert.resumeAi.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * HTTP layer for auth endpoints.
 *
 * The controller's only job is HTTP ↔ Java translation. All business logic
 * (hashing, credential verification, token generation) lives in AuthService.
 *
 * Endpoints:
 *
 *   POST /api/v1/auth/register
 *     - Accepts RegisterRequest (email, password, displayName).
 *     - Calls authService.register(request).
 *     - Returns 201 Created with Location header and AuthResponse body.
 *
 *   POST /api/v1/auth/login
 *     - Accepts LoginRequest (email, password).
 *     - Calls authService.login(request).
 *     - Returns 200 OK with AuthResponse body.
 *     - If credentials are wrong, let the exception bubble up to GlobalExceptionHandler.
 *
 * Things NOT to do:
 *   - No try/catch for auth failures — GlobalExceptionHandler maps exceptions to HTTP codes.
 *   - No password handling, no JWT creation — both belong in their respective services.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) {
        // TODO: call authService.register(request)
        // TODO: return 201 Created with Location: /api/v1/users/{userId} and AuthResponse body
        AuthResponse body = authService.register(request);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + body.userId()))
                .body(body);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        // TODO: call authService.login(request)
        // TODO: return 200 OK with AuthResponse body
        AuthResponse body = authService.login(request);
        return ResponseEntity.ok(body);
    }
}
