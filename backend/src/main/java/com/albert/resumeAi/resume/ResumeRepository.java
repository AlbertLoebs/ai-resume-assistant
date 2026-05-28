package com.albert.resumeAi.resume;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Resume persistence layer.
 *
 * Spring Data JPA generates the implementation at runtime.
 *
 * Extends JpaRepository<Resume, UUID> for free CRUD.
 *
 * Custom finders needed for Phase 3:
 *
 *   List<Resume> findAllByUserId(UUID userId)
 *     - Used by "list my resumes" endpoint.
 *
 *   (Optional) Optional<Resume> findByIdAndUserId(UUID id, UUID userId)
 *     - Convenience method: returns the resume only if it belongs to the user.
 *     - Lets the service skip a manual ownership check.
 *     - You can do this in the service layer too; either pattern is fine.
 */
public interface ResumeRepository extends JpaRepository<Resume, UUID> {

    // TODO: List<Resume> findAllByUserId(UUID userId);

    List<Resume> findAllByUserId(UUID id);

    // TODO: (optional) Optional<Resume> findByIdAndUserId(UUID id, UUID userId);

    Optional<Resume> findByIdAndUserId(UUID id, UUID userId);
}
