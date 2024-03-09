package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.dto.CategoryDTO;
import com.quiztoast.backend_api.model.entity.quiz.Category;
import com.quiztoast.backend_api.model.payload.request.CategoryRequest;

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
