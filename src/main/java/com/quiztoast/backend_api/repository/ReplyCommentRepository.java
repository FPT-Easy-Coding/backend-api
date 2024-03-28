package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.classroom.Comment;
import com.quiztoast.backend_api.model.entity.classroom.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {

    @Query(value = "SELECT rc FROM ReplyComment rc WHERE rc.comment.commentId = :commentId")
    List<ReplyComment> findByCommentId(int commentId);

    List<ReplyComment> findAllByComment(Comment comment);

    @Query(value = "DELETE FROM ReplyComment rc WHERE rc.replyCommentId = :replyId")
    @Modifying
    void deleteReply(Long replyId);
    @Query(value = "SELECT rc FROM ReplyComment rc WHERE rc.replyCommentId = :replyId")
    ReplyComment findByReplyId(Long replyId);
}
