package com.albert.resumeAi.user;

import com.albert.resumeAi.user.dto.CreateUserRequest;
import com.albert.resumeAi.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

// Add imports as you implement:
// import jakarta.validation.Valid;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.UUID;

/**
 * HTTP layer for user endpoints.
 *
 * The controller's only job is HTTP ↔ Java translation:
 *   - Parse incoming requests (path variables, JSON body, query params).
 *   - Call into the service.
 *   - Wrap the service's return value in a response DTO.
 *   - Return the right HTTP status code.
 *
 * Requirements:
 *  - Annotate with @RestController and @RequestMapping("/api/v1/users").
 *
 *  - Constructor-inject UserService.
 *
 *  - Endpoints for Phase 1:
 *
 *      POST /api/v1/users
 *        - Method signature accepts @RequestBody @Valid CreateUserRequest.
 *        - Calls userService.createUser(request).
 *        - Converts the returned User to a UserResponse.
 *        - Returns ResponseEntity<UserResponse> with HTTP 201 (Created).
 *        - Bonus: set a Location header pointing at the new resource:
 *            URI.create("/api/v1/users/" + user.getId())
 *
 *      GET /api/v1/users/{id}
 *        - Method signature accepts @PathVariable UUID id.
 *        - Calls userService.findById(id).
 *        - Converts the returned User to a UserResponse.
 *        - Returns it directly — Spring will send HTTP 200 automatically.
 *
 *  - Entity → DTO mapping: do it through UserResponse.from(user) (the static
 *    factory on the DTO). Keeps the controller a single line per endpoint.
 *
 * Things NOT to do:
 *  - Don't add business logic. If you find yourself writing an `if` that
 *    isn't input validation, it belongs in the service.
 *  - Don't accept the User entity as a @RequestBody. Always go through a
 *    request DTO so clients can't set server-controlled fields.
 *  - Don't return the User entity. Always wrap in a response DTO so internal
 *    fields stay internal.
 *  - Don't try/catch exceptions here just to set status codes. For now, let
 *    them bubble up; you'll add a @ControllerAdvice global handler later.
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    // TODO: private final UserService userService;
    // TODO: constructor that takes UserService
    // TODO: POST /api/v1/users  → createUser
    // TODO: GET  /api/v1/users/{id} → getUser

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        User user = userService.createUser(request);
        UserResponse body = UserResponse.from(user);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + user.getId()))
                .body(body);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable UUID id) {
        User user = userService.findById(id);
        return UserResponse.from(user);
    }

}
