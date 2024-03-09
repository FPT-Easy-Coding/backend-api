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
public class RateQuizResponse {
    long userId;
    int quizId;
    float rate;
    LocalDateTime createAt;
    boolean IsRated;
}
