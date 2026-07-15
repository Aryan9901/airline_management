package com.aryan.service;

import com.aryan.dto.UserDTO;
import com.aryan.payload.response.AuthResponse;

/**
 * Defines authentication-related operations.
 *
 * Provides functionality for user registration
 * and authentication.
 */
public interface AuthService {
    /**
     * Authenticates a user using email and password.
     *
     * @param email user's registered email address
     * @param password user's password
     * @return authentication response containing user details
     *         and authentication token
     */
    AuthResponse login(String email, String password) throws Exception;

    /**
     * Registers a new user account.
     *
     * @param request user registration details
     * @return authentication response for the newly registered user
     * @throws Exception if registration fails or the user already exists
     */
    AuthResponse signup(UserDTO request) throws Exception;
}
