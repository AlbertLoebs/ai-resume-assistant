package com.albert.resumeAi.user.dto;

// Add imports as you implement:
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Incoming JSON shape for POST /api/v1/users.
 *
 * Defines exactly what fields a client is allowed to send when creating
 * a user. Anything server-controlled (id, createdAt, updatedAt) MUST NOT
 * appear here — otherwise clients could set them.
 *
 * Requirements:
 *  - Fields to define:
 *      email        String
 *      displayName  String
 *
 *  - Validation annotations (so Spring auto-rejects bad payloads when the
 *    controller uses @Valid):
 *      email        @NotBlank, @Email
 *      displayName  @Size(min = 1, max = 100)   // adjust limits to taste
 *
 *  - Implement as a Java `record` (recommended — immutable, single line)
 *    or as a regular class with getters/setters. Records are ideal for DTOs:
 *    no boilerplate, no accidental mutation, free equals/hashCode/toString.
 *
 * Things NOT to do:
 *  - Don't include id, createdAt, or any field a client shouldn't set.
 *  - Don't reuse the User entity for incoming JSON.
 *  - Don't add business logic methods — DTOs are pure data shapes.
 */
public record CreateUserRequest(
        // TODO: email field annotated with @NotBlank and @Email
        // TODO: displayName field annotated with @Size

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min=1, max = 20)
        String displayName
) {
}
