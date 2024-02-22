package com.quizztoast.backendAPI.model.payload.response;

import com.quizztoast.backendAPI.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizSetResponse {
    private int quizId;
    private String quizName;

    private String author;
    private String authorFirstName;
    private String authorLastName;
    private int numberOfQuestion;

}
