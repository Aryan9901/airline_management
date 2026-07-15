package com.aryan.model;

import com.aryan.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing an application user.
 *
 * Stores user profile information, authentication credentials,
 * authorization role, and audit metadata.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    /**
     * Role assigned to the user for authorization.
     */
    @Column(nullable = false)
    private UserRole role;

    /**
     * BCrypt hashed password used for authentication.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Timestamp when the user account was created.
     * Automatically populated by Hibernate.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Timestamp of the most recent update to the user record.
     * Automatically maintained by Hibernate.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Timestamp of the user's most recent successful login.
     */
    private LocalDateTime lastLogin;
}
