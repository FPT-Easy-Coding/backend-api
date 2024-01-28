package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.dto.CategoryDTO;
import com.quizztoast.backendAPI.exception.EmailOrUsernameAlreadyTakenException;
import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category saveCategory(@Valid CategoryDTO categoryDTO) {
//        if (categoryDTO.getCategoryId() == 0) {
//            throw new EmailOrUsernameAlreadyTakenException("CategoryId", "CategoryId must not null");
//        }
        validateCategoryDTO(categoryDTO);
        // Convert CategoryDTO to Category entity
        Category category = convertToEntity(categoryDTO);

        // Save the entity using the repository
        categoryRepository.save(category);
        return category;
    }

    private void validateCategoryDTO(CategoryDTO categoryDTO) {
        if (categoryDTO.getCategoryId() == null) {
            throw new EmailOrUsernameAlreadyTakenException("categoryId", "CategoryId must not be null");
        }

        if (categoryDTO.getCategoryId() == 0) {
            throw new EmailOrUsernameAlreadyTakenException("categoryId", "CategoryId must not be zero");
        }

        if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().trim().isEmpty()) {
            throw new EmailOrUsernameAlreadyTakenException("categoryName", "CategoryName must not be blank");
        }
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
            throw new EmailOrUsernameAlreadyTakenException("CategoryId", "CategoryId" + " Not exist");
        }
    }

}
