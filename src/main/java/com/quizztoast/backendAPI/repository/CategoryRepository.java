package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.quiz.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

   @Query("SELECT c FROM Category c WHERE c.category_id = :categoryId")
    Category findCategoryById(int categoryId);

    @Query("SELECT c FROM Category c WHERE c.category_name = :categoryName")
    Category findByCategoryName(@Param("categoryName") String categoryName);
}
