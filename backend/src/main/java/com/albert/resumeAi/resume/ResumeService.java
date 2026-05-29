package com.albert.resumeAi.resume;

import com.albert.resumeAi.resume.dto.CreateResumeRequest;
import com.albert.resumeAi.resume.dto.UpdateResumeRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Resume business logic.
 *
 * The key new concept this phase: ownership checks. Every method takes the
 * current user's UUID and verifies they actually own the resource before
 * touching it. Without this, anyone with a valid JWT could read/edit/delete
 * anyone else's resumes by guessing UUIDs.
 *
 * Constructor-inject ResumeRepository.
 *
 * Methods to implement:
 *
 *   Resume create(UUID userId, CreateResumeRequest request)
 *     - Build a new Resume.
 *     - id = UUID.randomUUID().
 *     - userId = the passed-in userId (NOT something from the request — clients
 *       must never specify whose resume this is).
 *     - title / content from the request.
 *     - createdAt = updatedAt = Instant.now().
 *     - Save and return.
 *     - @Transactional.
 *
 *   List<Resume> listForUser(UUID userId)
 *     - Call repository.findAllByUserId(userId).
 *     - @Transactional(readOnly = true).
 *
 *   Resume findOne(UUID userId, UUID resumeId)
 *     - Load by id.
 *     - If not found → NoSuchElementException (→ 404).
 *     - If found but resume.getUserId() != userId → throw an exception that
 *       maps to 403 (e.g. AccessDeniedException from Spring Security, or a
 *       custom one). For now: throw NoSuchElementException too, so we don't
 *       leak the existence of other users' resumes.
 *     - @Transactional(readOnly = true).
 *
 *   Resume update(UUID userId, UUID resumeId, UpdateResumeRequest request)
 *     - Call findOne(userId, resumeId) to load + verify ownership in one step.
 *     - Mutate the loaded entity: set title/content from request.
 *     - Set updatedAt = Instant.now().
 *     - Save and return.
 *     - @Transactional.
 *
 *   void delete(UUID userId, UUID resumeId)
 *     - Call findOne(userId, resumeId) to verify ownership.
 *     - repository.delete(resume).
 *     - @Transactional.
 *
 * Things NOT to do:
 *   - Don't trust any userId from the request body or URL. Always use the
 *     userId from SecurityContext (controller pulls it via CurrentUser helper).
 *   - Don't return DTOs — return entities. Controller maps to DTOs.
 *   - Don't expose "this resume exists but isn't yours" in errors. Treat it
 *     as 404. Otherwise an attacker can enumerate resume IDs.
 */
@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Transactional
    public Resume create(UUID userId, CreateResumeRequest request) {
        // TODO: build Resume, set id/userId/title/content/timestamps, save
        Resume resume = new Resume();
        resume.setId(UUID.randomUUID());
        resume.setUserId(userId);
        resume.setTitle(request.title());
        resume.setContent(request.content());
        Instant now = Instant.now();
        resume.setCreatedAt(now);
        resume.setUpdatedAt(now);

        return resumeRepository.save(resume);
    }

    public List<Resume> listForUser(UUID userId) {
        // TODO: return repository.findAllByUserId(userId)
        return resumeRepository.findAllByUserId(userId);
    }

    public Resume findOne(UUID userId, UUID resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new NoSuchElementException("No resume"));

        if (!resume.getUserId().equals(userId)){
            throw new NoSuchElementException("Resume not found");
        }

        return resume;
    }

    public Resume update(UUID userId, UUID resumeId, UpdateResumeRequest request) {
        // TODO: load via findOne(), mutate title/content, update updatedAt, save
        Resume resume = findOne(userId, resumeId);
        resume.setTitle(request.title());
        resume.setContent(request.content());
        Instant now = Instant.now();
        resume.setUpdatedAt(now);
        return resumeRepository.save(resume);
    }

    public void delete(UUID userId, UUID resumeId) {
        // TODO: load via findOne() to verify ownership, then repository.delete()
        Resume resume = findOne(userId, resumeId);
        resumeRepository.delete(resume);
    }
}
