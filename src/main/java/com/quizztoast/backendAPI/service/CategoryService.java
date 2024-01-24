package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

}
