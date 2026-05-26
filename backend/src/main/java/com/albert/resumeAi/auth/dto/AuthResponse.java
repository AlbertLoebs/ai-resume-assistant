package com.albert.resumeAi.auth.dto;

import java.util.UUID;

/**
 * Outgoing JSON shape returned by both /register and /login.
 *
 * Fields:
 *  - token        the signed JWT the client must include in future requests
 *                 as `Authorization: Bearer <token>`
 *  - userId       the user's UUID — handy for the frontend without an extra GET
 *  - email        echoed back so the client can display it immediately
 *  - displayName  echoed back for the same reason
 *
 * Things NOT to do:
 *  - Never include the password hash, internal flags, or updatedAt.
 *  - Don't expose token expiry here unless the frontend needs to schedule a refresh.
 */
public record AuthResponse(
        String token,
        UUID userId,
        String email,
        String displayName
) {}
