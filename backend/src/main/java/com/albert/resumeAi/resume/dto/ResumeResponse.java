package com.albert.resumeAi.resume.dto;

import com.albert.resumeAi.resume.Resume;

import java.time.Instant;
import java.util.UUID;

/**
 * Outgoing JSON shape for resume-related responses.
 *
 * Fields exposed:
 *   - id         UUID
 *   - userId     UUID (owner — handy for the frontend to confirm)
 *   - title      String
 *   - content    String
 *   - createdAt  Instant
 *   - updatedAt  Instant
 *
 * Static factory: `from(Resume)` to keep controllers single-line.
 *
 * Things NOT to do:
 *   - Don't add validation annotations — those belong on request DTOs.
 *   - Don't expose internal-only fields if you add any later.
 */
public record ResumeResponse(
        UUID id,
        UUID userId,
        String title,
        String content,
        Instant createdAt,
        Instant updatedAt
) {

    public static ResumeResponse from(Resume resume) {
        // TODO: return new ResumeResponse(resume.getId(), resume.getUserId(), ...)
        return new ResumeResponse(resume.getId(), resume.getUserId(),
                    resume.getTitle(), resume.getContent(),
                    resume.getCreatedAt(), resume.getUpdatedAt());
    }
}
