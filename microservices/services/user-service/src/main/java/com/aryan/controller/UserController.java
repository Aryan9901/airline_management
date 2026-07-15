package com.aryan.controller;

import com.aryan.dto.UserDTO;
import com.aryan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for user-related operations.
 *
 * Provides APIs for:
 * - Retrieving the authenticated user's profile
 * - Retrieving user details by identifier
 * - Retrieving all registered users
 *
 * Base URL: /api/users
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Retrieves the profile of the authenticated user.
     *
     * @param email authenticated user's email address
     * @return authenticated user's profile
     */
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("X-User-Email") String email){
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves a user using their unique identifier.
     *
     * @param userId user identifier
     * @return user details
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves all registered users.
     *
     * @return list of registered users
     */
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
