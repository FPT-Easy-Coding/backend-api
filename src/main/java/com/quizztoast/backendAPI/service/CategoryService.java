package com.quizztoast.backendAPI.service;

import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<String> findCategoryById(int categoryId);
}
