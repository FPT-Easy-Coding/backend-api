package com.quiztoast.backend_api.service.category;

import com.quiztoast.backend_api.model.entity.quiz.Category;
import com.quiztoast.backend_api.model.payload.request.CategoryRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<String> findCategoryById(int categoryId);

    List<Category> getAllCategory();

    Category updateCategory(int id, CategoryRequest categoryRequest);
    ResponseEntity<?> deleteCategory(int id);
    ResponseEntity<?> getCategoryBycategoryId(int categoryId);
}
