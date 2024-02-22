package com.quizztoast.backendAPI.service.category;

import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.model.payload.request.CategoryRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<String> findCategoryById(int categoryId);

    List<Category> getAllCategory();
    Category updateCategory(int id, CategoryRequest categoryRequest);
    ResponseEntity<?> deleteCategory(int id);
}
