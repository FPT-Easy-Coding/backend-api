package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.entity.classroom.ReplyComment;
import com.quiztoast.backend_api.model.payload.response.ReplyCommentResponse;

public class ReplyCommentMapper {
    public static ReplyCommentResponse mapReplyCommentToResponse(ReplyComment replyComment) {
        return ReplyCommentResponse.builder()
                .replyCommentId(replyComment.getReplyCommentId())
                .userName(replyComment.getUser().getUserName())
                .userId(replyComment.getUser().getUserId())
                .content(replyComment.getContent())
                .commentId(replyComment.getComment().getCommentId())
                .build();
    }
}
