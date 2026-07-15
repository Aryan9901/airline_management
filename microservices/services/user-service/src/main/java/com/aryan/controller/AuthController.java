package com.aryan.controller;

import com.aryan.dto.UserDTO;
import com.aryan.payload.request.LoginRequest;
import com.aryan.payload.response.AuthResponse;
import com.aryan.service.AuthService;
import jakarta.validation.Valid;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for authentication operations.
 *
 * Provides APIs for:
 * - User registration
 * - User authentication
 *
 * Base URL: /auth
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * Registers a new user account.
     *
     * @param userRequest user registration details
     * @return authentication response containing the
     *         generated JWT and registered user details
     * @throws Exception if registration fails
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody @Valid UserDTO userRequest) throws Exception {
        AuthResponse response = authService.signup(userRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Authenticates a user using email and password.
     *
     * @param request login credentials
     * @return authentication response containing the
     *         generated JWT and authenticated user details
     * @throws Exception if authentication fails
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) throws Exception {
        AuthResponse response = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }
}
