package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.classroom.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT c FROM Comment c WHERE c.classroomQuestion.classQuestionId = :questionId")
    public List<Comment> findAllCommentsByQuestion(int questionId);

    @Query(value = "DELETE FROM Comment c WHERE c.commentId = :commentId")
    public void deleteComment(int commentId);
    @Query(value = "DELETE FROM Comment c WHERE c.classroomQuestion.classQuestionId = :questionId")
    public void deleteCommentByQuestionId(int questionId);
}
