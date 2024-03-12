package com.quiztoast.backend_api.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ReplyCommentResponse {

    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private Long replyCommentId;
    private Long userId;
    private Long commentId;

}
