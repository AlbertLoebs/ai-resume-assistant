package com.albert.resumeAi.user;

import com.albert.resumeAi.user.dto.CreateUserRequest;
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

    @Transactional
    public User createUser(CreateUserRequest request){
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(request.email());
        // System.out.println("DEBUG createUser → email=[" + request.email() + "] displayName=[" + request.displayName() + "]");
        user.setDisplayName(request.displayName());
        Instant now = Instant.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        return userRepository.save(user);
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
