package com.aryan.dto;

import com.aryan.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing user information.
 *
 * Used to exchange user data between application layers
 * without exposing the persistence entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;

    /**
     * User password.
     *
     * Used only during authentication or registration requests.
     * This field should never be returned in API responses.
     */
    private String password;
    private String phone;

    /**
     * Role assigned to the user.
     */
    private UserRole role;

    /**
     * Timestamp of the user's most recent successful login.
     */
    private LocalDateTime lastLogin;
}
