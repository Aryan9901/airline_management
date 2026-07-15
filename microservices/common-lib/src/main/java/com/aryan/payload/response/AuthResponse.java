package com.aryan.payload.response;

import com.aryan.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response returned after authentication or
 * user registration operations.
 *
 * Contains the authentication token,
 * operation status, and authenticated user details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    /**
     * JWT issued after successful authentication.
     */
    private String jwt;
    private String message;
    private String title;

    /**
     * Authenticated user's information.
     */
    private UserDTO user;
}
