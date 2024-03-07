package com.quizztoast.backendAPI.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateQuizRequest {
    private int quizId;
    private long userId;
    private float rate;
}
