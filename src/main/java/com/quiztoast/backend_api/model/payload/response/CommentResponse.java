package com.quiztoast.backend_api.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long commentId;
    private int questionId;
    private String userName;
    private Long userId;
    private LocalDateTime createAt;
    private String content;
    private List<ReplyCommentResponse> replyComments;
}
