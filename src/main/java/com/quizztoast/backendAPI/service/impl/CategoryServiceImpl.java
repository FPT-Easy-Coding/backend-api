package com.quizztoast.backendAPI.service.impl;

import com.quizztoast.backendAPI.exception.FormatException;
import com.quizztoast.backendAPI.model.dto.CategoryDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import com.quizztoast.backendAPI.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<String> findCategoryById(int categoryId) {
        if (categoryRepository.findById(categoryId).isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            throw new FormatException("CategoryId", "CategoryId" + " Not exist");
        }
    }

    public Category saveCategory(@Valid  CategoryDTO categoryDTO) {
//        if (categoryDTO.getCategoryId() == 0) {
//            throw new FormatException("CategoryId", "CategoryId must not null");
//        }

        validateCategoryDTO(categoryDTO);
        // Convert CategoryDTO to Category entity
        Category category = convertToEntity(categoryDTO);

        // Save the entity using the repository
        categoryRepository.save(category);
        return category;
    }
    private void validateCategoryDTO(CategoryDTO categoryDTO) {
        if (categoryDTO.getCategoryId()== null) {
            throw new FormatException("categoryId", "CategoryId must not be null");
        }

        if (categoryDTO.getCategoryId() == 0) {
            throw new FormatException("categoryId", "CategoryId must not be zero");
        }

        if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().trim().isEmpty()) {
            throw new FormatException("categoryName", "CategoryName must not be blank");
        }
    }
    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategory_name(categoryDTO.getCategoryName());
        // Map other properties from DTO to entity as needed

        return category;
    }

}
