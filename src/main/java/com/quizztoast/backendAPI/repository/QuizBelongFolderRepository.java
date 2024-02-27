package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.folder.Folder;
import com.quizztoast.backendAPI.model.entity.folder.QuizBelongFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.quizztoast.backendAPI.model.entity.folder.QuizBelongFolder.QuizBelongFolderId;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface QuizBelongFolderRepository extends JpaRepository<QuizBelongFolder, QuizBelongFolderId> {
    Long countByIdFolder(Folder folder);
    List<QuizBelongFolder> findByIdFolder(Folder folder);
    @Modifying
    @Query("DELETE FROM QuizBelongFolder q WHERE q.id.folder.folderId = :folderId AND q.id.quiz.quizId = :quizId")
    void deleteQuizBelongFolder(Long folderId, Long quizId);
    @Query("SELECT q FROM QuizBelongFolder q WHERE q.id.folder.folderId = :folderId AND q.id.quiz.quizId = :quizId")
    QuizBelongFolder findQuizBelongFolderById(Long folderId, Long quizId);
}
