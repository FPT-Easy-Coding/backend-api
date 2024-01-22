package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.quiz.Category;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }
}
