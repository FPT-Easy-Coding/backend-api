package com.quizztoast.backendAPI.service;

import com.quizztoast.backendAPI.model.quiz.Quiz;
import com.quizztoast.backendAPI.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private final QuizRepository repository;

    public QuizService(QuizRepository repository) {
        this.repository = repository;
    }

    public List<Quiz> getAllQuiz() {
        return repository.findAll();
    }
}
