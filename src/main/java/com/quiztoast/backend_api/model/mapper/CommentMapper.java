package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.entity.classroom.Comment;
import com.quiztoast.backend_api.model.entity.classroom.ReplyComment;
import com.quiztoast.backend_api.model.payload.response.CommentResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    public static CommentResponse mapCommentToCommentResponse(Comment comment, List<ReplyComment> replyComments) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .userName(comment.getUser().getUserName())
                .userId(comment.getUser().getUserId())
                .content(comment.getContent())
                .questionId(comment.getClassroomQuestion().getClassQuestionId())
                .replyComments(
                        replyComments.stream()
                                .map(ReplyCommentMapper::mapReplyCommentToResponse)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
