package com.aryan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the application.
 *
 * Configures Spring Security components including:
 * - HTTP security rules
 * - Session management
 * - CSRF protection
 * - Password encoding
 *
 * Current configuration:
 * - Disables CSRF protection
 * - Uses stateless session management
 * - Permits access to all endpoints
 *
 * This configuration can be extended in the future
 * to support authentication mechanisms such as
 * JWT, OAuth2, or role-based authorization.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the application's security filter chain.
     *
     * Current security settings:
     * - CSRF protection disabled
     * - Stateless session management
     * - All HTTP requests are permitted
     *
     * @param http Spring Security HTTP configuration
     * @return configured security filter chain
     * @throws Exception if security configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).authorizeHttpRequests(
                        auth -> auth.anyRequest().permitAll()
                );

        return http.build();
    }

    /**
     * Creates the application's password encoder.
     *
     * Uses the BCrypt hashing algorithm for securely
     * encoding user passwords.
     *
     * @return BCrypt password encoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
