package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.folder.Folder;
import com.quiztoast.backend_api.model.entity.folder.QuizBelongFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.quiztoast.backend_api.model.entity.folder.QuizBelongFolder.QuizBelongFolderId;

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
    @Modifying
    @Query("SELECT q FROM QuizBelongFolder q WHERE  q.id.quiz.quizId = :quizId")
    List<QuizBelongFolder> findByQuizId(int quizId);

    @Modifying
    @Query("DELETE FROM QuizBelongFolder q WHERE q.id.quiz.quizId = :quizId")
    void deleteQuizBelongFolderByQuizID( int quizId);
}
