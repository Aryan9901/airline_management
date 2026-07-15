package com.aryan.mapper;

import com.aryan.dto.UserDTO;
import com.aryan.model.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class responsible for converting
 * {@link User} entities into {@link UserDTO} objects.
 */
public class UserMapper {

    /**
     * Converts a user entity into a data transfer object.
     *
     * @param user user entity
     * @return mapped user DTO, or {@code null} if the input is {@code null}
     */
    public static UserDTO toDTO(User user) {
        if(user == null) return null;

        return UserDTO
                .builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
                .build();
    }

    /**
     * Converts a list of user entities into DTOs.
     *
     * @param users list of user entities
     * @return list of mapped user DTOs
     */
    public static List<UserDTO> toDTOList(List<User> users){

        if (users == null) {
            return Collections.emptyList();
        }

        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
}
