package com.albert.resumeAi.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Incoming JSON shape for POST /api/v1/auth/login.
 *
 * Fields:
 *  - email     the user's registered e-mail address
 *  - password  plain-text password; AuthService compares it against the stored hash
 *
 * Things NOT to do:
 *  - Don't include displayName or any field that isn't used for credential verification.
 */
public record LoginRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) {}
