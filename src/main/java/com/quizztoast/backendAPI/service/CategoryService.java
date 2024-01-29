package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.dto.UserDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public Category saveCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public ResponseEntity<String> existsCategoryById(int categoryId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found for ID: " + categoryId);
        }
    }

    public static class UserMapper {
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
}
