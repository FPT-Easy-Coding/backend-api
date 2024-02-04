package com.quizztoast.backendAPI.service.impl;

import com.quizztoast.backendAPI.exception.FormatException;
import com.quizztoast.backendAPI.model.dto.CategoryDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Category;
import com.quizztoast.backendAPI.model.payload.Request.CategoryRequest;
import com.quizztoast.backendAPI.repository.CategoryRepository;
import com.quizztoast.backendAPI.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.quizztoast.backendAPI.model.mapper.CategoryMapper.MapCategoryReqToCategory;
import static com.quizztoast.backendAPI.model.mapper.CategoryMapper.MapCategoryReqToDTO;

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

    public CategoryDTO saveCategory(@Valid CategoryRequest categoryRequest) {

        //check duplicate category_name
       if(categoryRepository.findByCategoryName(categoryRequest.getCategoryName()) != null )
       {
           throw new FormatException( "CategoryName","CategoryName is exist");
       }
        // Convert CategoryDTO to Category entity
      CategoryDTO categoryDTO = MapCategoryReqToDTO(categoryRequest);

        // Save the entity using the repository
        categoryRepository.save(MapCategoryReqToCategory(categoryRequest));
        return categoryDTO;
    }
    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        // Map other properties from DTO to entity as needed

        return category;
    }

    @Override
    public List<Category> getAllCategory() {
       return categoryRepository.findAll();
    }
}
