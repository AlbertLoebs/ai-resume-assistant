package com.albert.resumeAi.resume.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Incoming JSON shape for PUT /api/v1/resumes/{id}.
 *
 * Same fields as CreateResumeRequest for now. Kept as a separate type so that
 * if update rules diverge later (e.g. allow partial updates, optional fields,
 * different size limits) you can change one without affecting the other.
 *
 * Things NOT to do:
 *   - Don't accept the resume id in the body — it comes from the URL path.
 *   - Don't accept userId — it comes from the JWT.
 */
public record UpdateResumeRequest(

        @NotBlank
        @Size(min = 1, max = 200)
        String title,

        @NotBlank
        String content
) {}
