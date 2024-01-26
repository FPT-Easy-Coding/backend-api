package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.dto.CategoryDTO;
import com.quizztoast.backendAPI.model.quiz.Category;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public Category saveCategory(CategoryDTO categoryDTO) {
        // Convert CategoryDTO to Category entity
        Category category = convertToEntity(categoryDTO);

        // Save the entity using the repository
        categoryRepository.save(category);

        // Return the saved entity
        return category;
    }
    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategory_name(categoryDTO.getCategoryName());
        // Map other properties from DTO to entity as needed

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
