package com.aryan.service.impl;

import com.aryan.config.JwtProvider;
import com.aryan.dto.UserDTO;
import com.aryan.enums.UserRole;
import com.aryan.mapper.UserMapper;
import com.aryan.model.User;
import com.aryan.payload.response.AuthResponse;
import com.aryan.repository.UserRepository;
import com.aryan.service.AuthService;
import com.aryan.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service implementation responsible for user authentication
 * and registration.
 *
 * Handles:
 * - User registration
 * - User authentication
 * - JWT generation
 * - Password encryption
 * - Last login tracking
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    /**
     * Password encoder used for hashing and verifying passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Utility responsible for JWT generation.
     */
    private final JwtProvider jwtProvider;

    /**
     * Loads user details required during authentication.
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Registers a new user account.
     *
     * Business Rules:
     * - Email address must be unique
     * - System administrator accounts cannot be created through signup
     * - Password is securely encrypted before persistence
     *
     * @param request user registration details
     * @return authentication response containing the JWT and user information
     * @throws Exception if the email is already registered or the role is not permitted
     */
    @Override
    public AuthResponse signup(UserDTO request) throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if(existingUser.isPresent()){
            throw new Exception("Email already registered.");
        }

        if(request.getRole() == UserRole.ROLE_SYSTEM_ADMIN){
            throw new Exception("You cannot sign up for system admins!");
        }

        User userToCreate = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole())
                .fullName(request.getFullName())
                .lastLogin(LocalDateTime.now())
                .build();

        User createdUser = userRepository.save(userToCreate);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                createdUser.getEmail(), createdUser.getPassword()
        );

        String jwt = jwtProvider.generateToken(authentication,createdUser.getId());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDTO(createdUser));
        authResponse.setTitle("Welcome " + createdUser.getFullName());
        authResponse.setMessage("Registered Successfully!");
        return authResponse;
    }

    /**
     * Authenticates a user using email and password.
     *
     * Updates the user's last login timestamp after
     * successful authentication and generates a JWT.
     *
     * @param email user's registered email address
     * @param password user's password
     * @return authentication response containing the JWT and user details
     * @throws Exception if authentication fails
     */
    @Override
    public AuthResponse login(String email, String password) throws Exception{
        Authentication authentication = authenticate(email, password);

        Optional<User> existingUser = userRepository.findByEmail(email);
        if(existingUser.isPresent()){
            User user = existingUser.get();
            user.setLastLogin(LocalDateTime.now());
            User loggedInUser = userRepository.save(user);

            String jwt = jwtProvider.generateToken(authentication,user.getId());

            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jwt);
            authResponse.setUser(UserMapper.toDTO(loggedInUser));
            authResponse.setTitle("Welcome " + loggedInUser.getFullName());
            authResponse.setMessage("Logged in Successfully!");

            return authResponse;
        } else{
            throw new Exception("Invalid login Details!");
        }
    }

    /**
     * Authenticates the supplied user credentials.
     *
     * Loads the user from the database and validates
     * the provided password against the stored hash.
     *
     * @param email user's registered email address
     * @param password user's password
     * @return authenticated security principal
     * @throws Exception if the supplied credentials are invalid
     */
    private Authentication authenticate(String email, String password) throws Exception{
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if(!passwordEncoder.matches(
                password,userDetails.getPassword()
        )){
            throw new Exception("Invalid Credentials!");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}
