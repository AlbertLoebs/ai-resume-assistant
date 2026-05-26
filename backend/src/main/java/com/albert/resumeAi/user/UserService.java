package com.albert.resumeAi.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

// Add imports as you implement:
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import java.time.Instant;
// import java.util.NoSuchElementException;
// import java.util.UUID;

/**
 * User business logic.
 *
 * The middle layer between the controller (HTTP concerns) and the repository
 * (database concerns). This is where you decide *what it means* to create
 * a user — generating IDs, setting timestamps, checking for duplicate emails,
 * etc.
 *
 * Requirements:
 *  - Annotate the class with @Service so Spring picks it up.
 *
 *  - Inject UserRepository via constructor (NOT @Autowired on a field).
 *    Constructor injection makes the dependency explicit, immutable, and
 *    easy to test.
 *
 *  - Methods to implement for Phase 1:
 *
 *      User createUser(CreateUserRequest request)
 *        - Optional but recommended: check userRepository.existsByEmail
 *          first; if true, throw an exception (IllegalStateException is fine
 *          for now — you'll add a custom EmailAlreadyExistsException later).
 *        - Build a new User entity.
 *        - Set id = UUID.randomUUID().
 *        - Set createdAt = updatedAt = Instant.now().
 *        - Copy email and displayName from the request.
 *        - Call userRepository.save(user) and return the saved entity.
 *        - Annotate the method with @Transactional so the existence check
 *          and the insert run in a single transaction.
 *
 *      User findById(UUID id)
 *        - Call userRepository.findById(id).
 *        - If empty, throw a runtime exception (NoSuchElementException for
 *          now; you'll swap in a custom NotFoundException later).
 *        - Otherwise return the User.
 *        - @Transactional(readOnly = true) is a nice-to-have.
 *
 * Things NOT to do:
 *  - Don't return DTOs from the service. Return entities. Controller does
 *    the entity → DTO mapping.
 *  - Don't touch JPA's EntityManager directly. Go through the repository.
 *  - Don't reach into HTTP concerns (no HttpServletRequest, no ResponseEntity).
 *  - Don't catch and swallow exceptions silently. Let them bubble up; the
 *    controller (or a global handler later) decides the HTTP response.
 */

@Service
public class UserService {

    // TODO: private final UserRepository userRepository;
    // TODO: constructor that takes UserRepository
    // TODO: createUser(CreateUserRequest)
    // TODO: findById(UUID)

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Phase 2 signature: AuthService hashes the password and passes it here.
     * This method no longer accepts a DTO — it takes the three raw values so
     * the service layer stays free of auth-specific types.
     *
     * TODO (when implementing):
     *  - Check existsByEmail; throw IllegalArgumentException on duplicate.
     *  - Build User, set id/timestamps/passwordHash, save and return.
     */
    @Transactional
    public User createUser(String email, String displayName, String passwordHash) {
        // TODO: check existsByEmail, throw on duplicate
        // TODO: build User, set all fields including passwordHash
        // TODO: return userRepository.save(user)

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("User already exists");
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setDisplayName(displayName);
        user.setPasswordHash(passwordHash);
        Instant now = Instant.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }

    /** Used by AuthService.login to look up the user by email for credential verification. */
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        // TODO: return userRepository.findByEmail(email)
        //       .orElseThrow(() -> new NoSuchElementException("User not found"));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Transactional
    public User findById(UUID id){
        Optional<User> maybeUser = userRepository.findById(id);

        if (maybeUser.isEmpty()) {
            throw new NoSuchElementException("User not found: " + id);
        }

        return maybeUser.get();
    }
}
