package com.albert.resumeAi.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

/**
 * Utility to pull the authenticated user's UUID out of the SecurityContext.
 *
 * Background:
 *   JwtAuthFilter parses the JWT on every request and sets a
 *   UsernamePasswordAuthenticationToken on SecurityContextHolder where:
 *      principal = the user's UUID as a String (the JWT subject)
 *
 *   Controllers can read it via:
 *      SecurityContextHolder.getContext().getAuthentication().getPrincipal()
 *
 *   This helper wraps that ceremony into a single static call and parses the
 *   String back to UUID — so callers just do:
 *      UUID userId = CurrentUser.id();
 *
 * Method to implement:
 *
 *   static UUID id()
 *     - Read the Authentication from SecurityContextHolder.
 *     - If null → throw IllegalStateException("No authenticated user").
 *       (Should never happen on a protected endpoint — Spring Security
 *       would have rejected the request before reaching the controller.)
 *     - Cast principal to String, parse with UUID.fromString().
 *     - Return the UUID.
 *
 * Things NOT to do:
 *   - Don't make this a Spring @Component. Static utility is fine — no state.
 *   - Don't return null. A controller calling this is already inside a
 *     protected endpoint, so an authenticated user is guaranteed to exist.
 */
public final class CurrentUser {

    private CurrentUser() {} // no instances

    public static UUID id() {
        // TODO: pull Authentication from SecurityContextHolder
        // TODO: parse principal (String) → UUID
        // TODO: throw IllegalStateException if missing

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new IllegalStateException("No authentication provided");
        }

        String principal = (String) auth.getPrincipal();

        return UUID.fromString(principal);

    }
}
