package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.classroom.Comment;
import com.quiztoast.backend_api.model.entity.classroom.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {

    @Query(value = "SELECT rc FROM ReplyComment rc WHERE rc.comment.commentId = :commentId")
    List<ReplyComment> findByCommentId(int commentId);

    List<ReplyComment> findAllByComment(Comment comment);

    @Query(value = "DELETE FROM ReplyComment rc WHERE rc.replyCommentId = :replyId")
    void deleteReply(Long replyId);

    @Query(value = "UPDATE ReplyComment rc SET rc.content = :content WHERE rc.replyCommentId = :replyId")
    void updateReply(Long replyId, String content);
}
