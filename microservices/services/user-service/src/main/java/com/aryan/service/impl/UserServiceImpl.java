package com.aryan.service.impl;

import com.aryan.dto.UserDTO;
import com.aryan.mapper.UserMapper;
import com.aryan.model.User;
import com.aryan.repository.UserRepository;
import com.aryan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation responsible for
 * user retrieval operations.
 *
 * Handles fetching users by:
 * - Email
 * - Identifier
 * - Complete user list
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO getUserByEmail(String email) {
        return UserMapper.toDTO(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with email " + email
                )));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return UserMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with id " + id
                )));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return UserMapper.toDTOList(userRepository.findAll());
    }
}
