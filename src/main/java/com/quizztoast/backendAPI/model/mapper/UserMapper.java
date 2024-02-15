package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.UserDTO;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    private static QuizRepository quizRepository;

    @Autowired
    public void setQuizRepository(QuizRepository quizRepository) {
        UserMapper.quizRepository = quizRepository;
    }
    // Convert User JPA Entity into UserDto
    public static UserDTO mapToUserDto(User user) {
        return UserDTO.builder()
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .isBanned(user.isBanned())
                .isPremium(user.isPremium())
                .Role(user.getRole())
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
                .isPremium(user.isPremium())
                .createdAt(user.getCreatedAt())
               .numberOfQuizSet(quizRepository.countQuizbyUserId(user.getUserId()))
                .view(quizRepository.countViewByUserId(user.getUserId()))
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
 