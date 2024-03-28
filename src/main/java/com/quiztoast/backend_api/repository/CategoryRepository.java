package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.quiz.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

   @Query("SELECT c FROM Category c WHERE c.categoryId = :categoryId")
    Category findCategoryById(int categoryId);

    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    Category findByCategoryName(@Param("categoryName") String categoryName);
    @Modifying
    @Query("DELETE FROM Category q WHERE q.categoryId = :id")
    void deleteByCategoryId(int id);
}
