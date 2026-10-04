package com.aryan.repository;

import com.aryan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for managing {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Retrieves a user by their email address.
     *
     * @param email user's email address
     * @return matching user, if found
     */
    Optional<User> findByEmail(String email);
}
