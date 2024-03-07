package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.entity.classroom.Comment;
import com.quizztoast.backendAPI.model.entity.classroom.ReplyComment;
import com.quizztoast.backendAPI.model.payload.response.CommentResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {
    public static CommentResponse mapCommentToCommentResponse(Comment comment, List<ReplyComment> replyComments) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .userName(comment.getUser().getUserName())
                .content(comment.getContent())
                .replyComments(
                        replyComments.stream()
                                .map(ReplyCommentMapper::mapReplyCommentToResponse)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
