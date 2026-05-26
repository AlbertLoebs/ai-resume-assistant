package com.albert.resumeAi.user;

// Add JPA + Java time imports as you implement fields:
// import jakarta.persistence.*;
// import java.time.Instant;
// import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

/**
 * User domain entity.
 *
 * One instance of this class = one row in the `users` table created by V2__users.sql.
 *
 * Requirements:
 *  - Annotate the class with @Entity and @Table(name = "users").
 *
 *  - Fields, one per column:
 *      id           UUID                @Id
 *      email        String              @Column(nullable = false, unique = true)
 *      displayName  String              @Column(name = "display_name")
 *      createdAt    Instant             @Column(name = "created_at", nullable = false, updatable = false)
 *      updatedAt    Instant             @Column(name = "updated_at", nullable = false)
 *
 *  - JPA needs a no-arg constructor (protected is conventional so app code
 *    can't accidentally create blank Users). Either write one explicitly
 *    or use Lombok's @NoArgsConstructor(access = AccessLevel.PROTECTED).
 *
 *  - Add getters/setters by hand or with Lombok (@Getter @Setter).
 *
 *  - Generate the id in the service (UUID.randomUUID()) before save() —
 *    don't rely on @GeneratedValue. Keeping ID generation in Java keeps
 *    your entity in sync with the DB row immediately, no flush needed.
 *
 * Things NOT to do:
 *  - No business logic here (no validation, no side effects). Keep it dumb.
 *  - No web annotations (@JsonIgnore, etc.). This class never crosses the
 *    HTTP boundary — DTOs handle that.
 *  - No relationships yet (no @OneToMany resumes). Add those in the
 *    resume feature later.
 */
@Entity
@Table(name = "users")
public class User {

    // TODO: fields
    // TODO: protected no-arg constructor (for JPA)
    // TODO: getters / setters

    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Phase 2: stored BCrypt hash — plain-text password never persisted
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    public User(){}

    public User(UUID id, String email, String displayName, Instant createdAt, Instant updatedAt, String passwordHash) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.passwordHash = passwordHash;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
