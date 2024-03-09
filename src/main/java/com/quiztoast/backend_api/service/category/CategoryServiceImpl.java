package com.quiztoast.backend_api.service.category;

import com.quiztoast.backend_api.exception.FormatException;
import com.quiztoast.backend_api.model.dto.CategoryDTO;
import com.quiztoast.backend_api.model.entity.quiz.Category;
import com.quiztoast.backend_api.model.payload.request.CategoryRequest;
import com.quiztoast.backend_api.repository.CategoryRepository;
import com.quiztoast.backend_api.repository.QuizAnswerRepository;
import com.quiztoast.backend_api.repository.QuizQuestionRepository;
import com.quiztoast.backend_api.repository.QuizRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.quiztoast.backend_api.model.mapper.CategoryMapper.MapCategoryReqToCategory;
import static com.quiztoast.backend_api.model.mapper.CategoryMapper.MapCategoryReqToDTO;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizRepository quizRepository;
    private final QuizAnswerRepository quizAnswerRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, QuizQuestionRepository quizQuestionRepository, QuizRepository quizRepository, QuizAnswerRepository quizAnswerRepository) {
        this.categoryRepository = categoryRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizRepository = quizRepository;
        this.quizAnswerRepository = quizAnswerRepository;
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
        if (categoryRepository.findByCategoryName(categoryRequest.getCategoryName()) != null) {
            throw new FormatException("CategoryName", "CategoryName is exist");
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
        //update totalquiz
        for (Category category : categoryRepository.findAll()) {
            category.setTotalQuiz(quizRepository.findTotalQuizByCategory(category.getCategoryId()));
        }
        return categoryRepository.findAll();
    }

    public Category updateCategory(int id, CategoryRequest categoryRequest) {
        //check category id
        if (categoryRepository.findCategoryById(id) == null) {
            throw new FormatException("CategoryId", "CategoryId is not exist");
        }
        //check category name
        Category category = categoryRepository.findCategoryById(id);
        if (category.getCategoryName().equals(categoryRequest.getCategoryName())) {
            throw new FormatException("CategoryName", "CategoryName is exist");
        }
        category.setCategoryName(categoryRequest.getCategoryName());
        return category;
    }

    public ResponseEntity<?> deleteCategory(int id) {
        //check category id
        if (categoryRepository.findCategoryById(id) == null) {
            throw new FormatException("CategoryId", "CategoryId is not exist");
        }
        Category category = categoryRepository.findCategoryById(id);
     //check question of categoryid
        if (quizQuestionRepository.findQuizQuesstionByCategoryId(id).length!=0 ) {
            throw new FormatException("CategoryId", "exist quiz have categoryId");
        }
        categoryRepository.deleteByCategoryId(id);
        return ResponseEntity.ok("delete successfull");
    }

    public ResponseEntity<?> getCategoryBycategoryId(int categoryId) {
        if (categoryRepository.findCategoryById(categoryId) == null) {
            throw new FormatException("CategoryId", "CategoryId is not exist");
        }
       Category category= categoryRepository.findCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }
}
