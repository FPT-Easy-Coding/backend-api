package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.classroom.Comment;
import com.quiztoast.backend_api.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT c FROM Comment c WHERE c.classroomQuestion.classQuestionId = :questionId")
    public List<Comment> findAllCommentsByQuestion(int questionId);

    @Query(value = "DELETE FROM Comment c WHERE c.commentId = :commentId")
    public void deleteComment(Long commentId);

    @Query(value = "DELETE FROM Comment c WHERE c.classroomQuestion.classQuestionId = :questionId")
    public void deleteCommentByQuestionId(int questionId);

    @Query("SELECT DISTINCT c.user FROM Comment c WHERE c.classroomQuestion.classQuestionId = :questionId")
    List<User> findUsersWhoCommentedOnPost(int questionId);

    @Query(value = "UPDATE Comment c SET c.content = :content WHERE c.commentId = :commentId")
    void updateComment(Long commentId, String content);

    Comment findByCommentId(Long commentId);
}
