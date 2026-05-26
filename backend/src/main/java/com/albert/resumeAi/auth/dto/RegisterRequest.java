package com.albert.resumeAi.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Incoming JSON shape for POST /api/v1/auth/register.
 *
 * Replaces the old CreateUserRequest. The key difference is the addition of
 * `password`, which AuthService will hash before persisting.
 *
 * Fields:
 *  - email        must be a valid, non-blank e-mail address
 *  - password     non-blank, min 8 chars (enforce a sensible lower bound)
 *  - displayName  non-blank, 1–100 chars
 *
 * Things NOT to do:
 *  - Don't accept a pre-hashed password; the client always sends plain-text.
 *  - Don't reuse this DTO for login — LoginRequest is a separate shape.
 */
public record RegisterRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8, max = 128)
        String password,

        @NotBlank
        @Size(min = 1, max = 100)
        String displayName
) {}
