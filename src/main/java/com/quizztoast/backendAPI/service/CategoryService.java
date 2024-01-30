package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.entity.quiz.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<String> findCategoryById(int categoryId);

    List<Category> getAllCategory();
}
