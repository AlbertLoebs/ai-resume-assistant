package com.albert.resumeAi.resume.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Incoming JSON shape for POST /api/v1/resumes.
 *
 * Fields a client is allowed to set when creating a resume:
 *   - title    1–200 chars, non-blank
 *   - content  non-blank (no upper bound for now; tighten later if needed)
 *
 * Things NOT to do:
 *   - Don't include id, userId, createdAt, or updatedAt. All server-controlled.
 *   - Don't reuse this DTO for update — UpdateResumeRequest is separate so
 *     each endpoint can evolve its validation rules independently.
 */
public record CreateResumeRequest(

        @NotBlank
        @Size(min = 1, max = 200)
        String title,

        @NotBlank
        String content
) {}
