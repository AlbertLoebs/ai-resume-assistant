package com.albert.resumeAi.user.dto;

import com.albert.resumeAi.user.User;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

import java.time.Instant;
import java.util.UUID;

/**
 * Outgoing JSON shape for user-related responses.
 *
 * Defines exactly what fields are exposed to API consumers. Anything you
 * don't want clients to see (later: password hashes, internal flags, soft-delete
 * markers) MUST NOT appear here.
 *
 * Requirements:
 *  - Fields to define:
 *      id           UUID
 *      email        String
 *      displayName  String
 *      createdAt    Instant
 *
 *  - Implement as a `record` (recommended) or a regular class with getters.
 *
 *  - Add a static factory method that converts a User entity into this DTO:
 *
 *      public static UserResponse from(User user) {
 *          return new UserResponse(
 *              user.getId(),
 *              user.getEmail(),
 *              user.getDisplayName(),
 *              user.getCreatedAt()
 *          );
 *      }
 *
 *    Keeping the mapping next to the DTO keeps controllers clean — they just
 *    call UserResponse.from(user) and return it.
 *
 * Things NOT to do:
 *  - Don't expose updatedAt unless you actually want clients to see it.
 *  - Don't include any field you'd hesitate to put in a public API.
 *  - Don't add validation annotations — those belong on request DTOs, not
 *    response DTOs. Responses are server-built and trusted by definition.
 */
public record UserResponse(
        // TODO: id (UUID)
        // TODO: email (String)
        // TODO: displayName (String)
        // TODO: createdAt (Instant)

        UUID id,
        String email,
        String displayName,
        Instant createdAt
) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getCreatedAt()
        );
    }
}