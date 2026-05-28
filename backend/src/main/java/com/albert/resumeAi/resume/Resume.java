package com.albert.resumeAi.resume;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * Resume domain entity.
 *
 * One row in the `resumes` table created by V4__resumes.sql.
 *
 * Requirements:
 *  - Annotate with @Entity and @Table(name = "resumes").
 *
 *  - Fields, one per column:
 *      id         UUID      @Id
 *      userId     UUID      @Column(name = "user_id", nullable = false, updatable = false)
 *      title      String    @Column(nullable = false)
 *      content    String    @Column(nullable = false, columnDefinition = "text")
 *      createdAt  Instant   @Column(name = "created_at", nullable = false, updatable = false)
 *      updatedAt  Instant   @Column(name = "updated_at", nullable = false)
 *
 *  - No-arg constructor for JPA.
 *  - Getters / setters.
 *  - id generated in the service via UUID.randomUUID() (same pattern as User).
 *
 * Things NOT to do:
 *  - No @ManyToOne to User yet. Just store the userId as a plain UUID. Keeps the
 *    entity simple and decouples the resume feature from the User entity.
 *  - No business logic — entities are dumb data holders.
 *  - No web annotations — DTOs handle the HTTP boundary.
 */
@Entity
@Table(name = "resumes")
public class Resume {

    // TODO: fields with proper @Column mappings
    // TODO: no-arg constructor (for JPA)
    // TODO: getters / setters

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    Resume() {}

    Resume(UUID id, UUID userId, String title, String content, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
