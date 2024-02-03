package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.dto.CategoryDTO;
import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.model.payload.Request.CategoryRequest;
import com.quizztoast.backendAPI.model.payload.Request.QuizAnswerRequest;

public class CategoryMapper {

    public static CategoryDTO MapCategoryReqToDTO(CategoryRequest categoryRequest){
        return CategoryDTO.builder()
                .categoryName(categoryRequest.getCategoryName())
                .build();
    }
    public static Category MapCategoryReqToCategory(CategoryRequest categoryRequest){
        return Category.builder()
                .categoryName(categoryRequest.getCategoryName())
                .build();
    }
}
