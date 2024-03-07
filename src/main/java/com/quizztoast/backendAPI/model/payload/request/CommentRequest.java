package com.quizztoast.backendAPI.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    private Long userId;
    private int questionId;
    private String content;
}
