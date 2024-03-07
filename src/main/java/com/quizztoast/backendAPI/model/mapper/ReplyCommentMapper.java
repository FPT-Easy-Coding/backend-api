package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.entity.classroom.ReplyComment;
import com.quizztoast.backendAPI.model.payload.response.ReplyCommentResponse;

public class ReplyCommentMapper {
    public static ReplyCommentResponse mapReplyCommentToResponse(ReplyComment replyComment) {
        return ReplyCommentResponse.builder()
                .replyCommentId(replyComment.getReplyCommentId())
                .userName(replyComment.getUser().getUserName())
                .content(replyComment.getContent())
                .commentId(replyComment.getComment().getCommentId())
                .build();
    }
}
