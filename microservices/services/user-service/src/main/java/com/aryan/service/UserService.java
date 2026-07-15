package com.aryan.service;

import com.aryan.dto.UserDTO;

import java.util.List;

/**
 * Defines user management operations.
 *
 * Provides methods for retrieving user information
 * using various search criteria.
 */
public interface UserService {

    /**
     * Retrieves a user using their registered email address.
     *
     * @param email user's email address
     * @return matching userDTO
     */
    UserDTO getUserByEmail(String email);


    /**
     * Retrieves a user using their unique identifier.
     *
     * @param id user identifier
     * @return matching userDTO
     */
    UserDTO getUserById(Long id);

    /**
     * Retrieves all registered users.
     *
     * @return list of userDTO
     */
    List<UserDTO> getAllUsers();
}
