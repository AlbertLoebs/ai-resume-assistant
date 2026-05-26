package com.albert.resumeAi.user;

import com.albert.resumeAi.user.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * HTTP layer for user endpoints.
 *
 * Phase 2 change: POST /api/v1/users is removed. Registration now goes through
 * AuthController (POST /api/v1/auth/register), which hashes the password before
 * calling UserService.
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST /api/v1/users removed — registration moved to AuthController (Phase 2)

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable UUID id) {
        User user = userService.findById(id);
        return UserResponse.from(user);
    }
}
