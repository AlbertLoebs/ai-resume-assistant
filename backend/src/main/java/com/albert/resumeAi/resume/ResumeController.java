package com.albert.resumeAi.resume;

import com.albert.resumeAi.common.CurrentUser;
import com.albert.resumeAi.resume.dto.CreateResumeRequest;
import com.albert.resumeAi.resume.dto.ResumeResponse;
import com.albert.resumeAi.resume.dto.UpdateResumeRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * HTTP layer for resume endpoints. All routes require authentication —
 * SecurityConfig's anyRequest().authenticated() rule covers them automatically
 * because /api/v1/resumes/** is NOT in the permitAll list.
 *
 * Constructor-inject ResumeService.
 *
 * Endpoints:
 *
 *   POST   /api/v1/resumes
 *     - @Valid CreateResumeRequest body.
 *     - Pull userId from CurrentUser.id().
 *     - service.create(userId, request) → return 201 Created with ResumeResponse.
 *
 *   GET    /api/v1/resumes
 *     - Pull userId from CurrentUser.id().
 *     - service.listForUser(userId) → return List<ResumeResponse>.
 *     - 200 OK.
 *
 *   GET    /api/v1/resumes/{id}
 *     - @PathVariable UUID id.
 *     - service.findOne(userId, id) → return ResumeResponse.
 *     - 200 OK.
 *
 *   PUT    /api/v1/resumes/{id}
 *     - @PathVariable UUID id + @Valid UpdateResumeRequest body.
 *     - service.update(userId, id, request) → return ResumeResponse.
 *     - 200 OK.
 *
 *   DELETE /api/v1/resumes/{id}
 *     - @PathVariable UUID id.
 *     - service.delete(userId, id).
 *     - Return ResponseEntity.noContent().build() — 204 No Content.
 *
 * Things NOT to do:
 *   - Don't accept userId in the URL or body. It always comes from the JWT.
 *   - Don't return entities — always map to ResumeResponse first.
 *   - Don't try/catch — let exceptions bubble up to GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/api/v1/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<ResumeResponse> create(@RequestBody @Valid CreateResumeRequest request) {
        // TODO: get current userId from CurrentUser
        // TODO: call service.create
        // TODO: map to ResumeResponse, return 201 Created (URI: /api/v1/resumes/{id})
        UUID userId = CurrentUser.id();
        Resume resume = resumeService.create(userId, request);
        ResumeResponse body = ResumeResponse.from(resume);
        return ResponseEntity.created(URI.create("/api/v1/resumes/" + body.id())).body(body);
    }

    @GetMapping
    public List<ResumeResponse> list() {
        // TODO: get current userId
        // TODO: call service.listForUser, map each Resume → ResumeResponse
        UUID userId = CurrentUser.id();
        return resumeService.listForUser(userId).stream().
                map(ResumeResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ResumeResponse get(@PathVariable UUID id) {
        // TODO: get current userId
        // TODO: call service.findOne, map to ResumeResponse
        UUID userId = CurrentUser.id();
        return resumeService.findOne(userId,id).stream().
                map(ResumeResponse::from).toList();
    }

    @PutMapping("/{id}")
    public ResumeResponse update(@PathVariable UUID id, @RequestBody @Valid UpdateResumeRequest request) {
        // TODO: get current userId
        // TODO: call service.update, map to ResumeResponse
        throw new UnsupportedOperationException("TODO");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // TODO: get current userId
        // TODO: call service.delete
        // TODO: return ResponseEntity.noContent().build()
        throw new UnsupportedOperationException("TODO");
    }
}
