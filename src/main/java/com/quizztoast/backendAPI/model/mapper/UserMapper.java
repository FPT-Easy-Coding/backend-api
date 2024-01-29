package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.UserDTO;

import com.quizztoast.backendAPI.model.entity.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    // Convert User JPA Entity into UserDto
    public static UserDTO mapToUserDto(User user){
        return UserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .isBanned(user.isBanned())
                .isPremium(user.isPremium())
                .build();
    }
    public static UserDTO mapUserDtoToAdmin(User user){
        return UserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .Role(user.getRole())
                .isBanned(user.isBanned())
                .isPremium(user.isPremium())
                .createdAt(user.getCreatedAt())
                .build();
    }
    public static List<UserDTO> usersToUserDTOs(List<User> users) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : users) {
            userDTOList.add(mapUserDtoToAdmin(user));
        }
        return userDTOList;
    }
}
