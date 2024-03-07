package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.classroom.Comment;
import com.quizztoast.backendAPI.model.entity.classroom.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {

    @Query(value = "SELECT rc FROM ReplyComment rc WHERE rc.comment.commentId = :commentId")
    List<ReplyComment> findByCommentId(int commentId);

    List<ReplyComment> findAllByComment(Comment comment);
}
