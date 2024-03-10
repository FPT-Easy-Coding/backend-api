package com.quiztoast.backend_api.service.quiz;

import com.quiztoast.backend_api.model.entity.quiz.QuizAnswer;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import com.quiztoast.backend_api.model.mapper.QuizAnswerMapper;
import com.quiztoast.backend_api.model.payload.request.CreateQuizAnswerRequest;
import com.quiztoast.backend_api.repository.QuizAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class QuizAnswerServiceImpl implements QuizAnswerService {
    private final QuizAnswerRepository quizAnswerRepository;

    @Override
    public void createQuizAnswer(CreateQuizAnswerRequest createQuizRequest, QuizQuestion quizQuestion) {
         quizAnswerRepository.save(
                QuizAnswerMapper.mapCreateRequestToQuizAnswer(createQuizRequest, quizQuestion)
        );

    }
}
