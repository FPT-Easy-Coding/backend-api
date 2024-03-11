package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.dto.UserDTO;
import com.quiztoast.backend_api.model.payload.response.UserProfileResponse;
import com.quiztoast.backend_api.model.entity.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    // Convert User JPA Entity into UserDto
    public static UserProfileResponse mapToUserProfile(User user) {
        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .isBanned(user.isBanned())
                .isPremium(user.isPremium())
                .Role(user.getRole())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    public static UserDTO mapUserDtoToAdmin(User user) {
        System.out.println("Check this:"+user.getUserName());
        return UserDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .Role(user.getRole())
                .isBanned(user.isBanned())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
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
 