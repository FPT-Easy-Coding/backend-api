package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.CategoryDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.model.payload.request.CategoryRequest;
import com.quizztoast.backendAPI.repository.QuizRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CategoryMapper {

    public static CategoryDTO MapCategoryReqToDTO(CategoryRequest categoryRequest){
        return CategoryDTO.builder()
                .categoryName(categoryRequest.getCategoryName())
                .createAt(LocalDateTime.now())
                .build();
    }
    public static Category MapCategoryReqToCategory(CategoryRequest categoryRequest){
        return Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .createAt(LocalDateTime.now())
                .build();
    }
}
