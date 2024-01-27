package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.UserDTO;

import com.quizztoast.backendAPI.model.entity.user.User;

public class UserMapper {
    // Convert User JPA Entity into UserDto
    public static UserDTO mapToUserDto(User user){
        return new UserDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getTelephone()
        );
    }
}
