package com.quiztoast.backend_api.service.quiz;


import com.quiztoast.backend_api.exception.FormatException;

import com.quiztoast.backend_api.model.dto.QuizAnswerDTO;
import com.quiztoast.backend_api.model.dto.QuizQuestionDTO;
import com.quiztoast.backend_api.model.entity.quiz.Category;
import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.entity.quiz.QuizAnswer;
import com.quiztoast.backend_api.model.entity.quiz.QuizQuestion;
import com.quiztoast.backend_api.model.mapper.QuizAnswerMapper;
import com.quiztoast.backend_api.model.mapper.QuizQuestionMapper;
import com.quiztoast.backend_api.model.payload.request.CreateQuizAnswerRequest;
import com.quiztoast.backend_api.model.payload.request.CreateQuizQuestionRequest;
import com.quiztoast.backend_api.model.payload.request.QuizAnswerRequest;
import com.quiztoast.backend_api.model.payload.request.QuizQuestionRequest;
import com.quiztoast.backend_api.repository.CategoryRepository;
import com.quiztoast.backend_api.repository.QuizAnswerRepository;
import com.quiztoast.backend_api.repository.QuizQuestionMappingRepository;
import com.quiztoast.backend_api.repository.QuizQuestionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class QuizQuestionServiceImpl implements QuizQuestionService {

    private final CategoryRepository categoryRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final QuizAnswerServiceImpl quizAnswerService;
    private final QuizQuestionMappingRepository quizQuestionMappingRepository;


    public ResponseEntity<QuizQuestion> createQuizQuestionAndAnswers(QuizQuestionDTO quizQuestionDTO) {
        if(quizQuestionDTO == null || quizQuestionDTO.getAnswersEntity() == null)
        {
            throw  new FormatException("quizQuestionDTO","quizQuestionDTO not exist");
        }
        try {
            // Find the category by ID
            Category category = categoryRepository.findById(quizQuestionDTO.getCategoryId())
                    .orElseThrow(() -> new FormatException("category_id","Category not found"));

            // Create QuizQuestion
            QuizQuestion quizQuestion = QuizQuestion.builder()
                    .content(quizQuestionDTO.getQuestionContent())
                    .categoryId(category)
                    .createdAt(LocalDateTime.now())
                    .build();

            quizQuestionRepository.save(quizQuestion);

            // Create QuizAnswer list
            List<QuizAnswer> quizAnswers = new ArrayList<>();
            for (QuizAnswerDTO answerRequest : quizQuestionDTO.getAnswersEntity()) {
                QuizAnswer quizAnswer = QuizAnswer.builder()
                        .quizAnswerId(answerRequest.getAnswerId())
                        .content(answerRequest.getContent())
                        .isCorrect(answerRequest.isIsCorrect())
                        .createdAt(LocalDateTime.now())
                        .quizQuestion(quizQuestion)
                        .build();

                quizAnswers.add(quizAnswer);
            }

            // Save QuizAnswer list
            quizAnswerRepository.saveAll(quizAnswers);

            return ResponseEntity.ok(quizQuestion);
        }
        catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public List<QuizQuestion> getAllQuizQuestions() {
        return quizQuestionRepository.findAll();
    }

    @Override
    public List<QuizQuestionDTO> getAllQuizDTO() {
        List<QuizQuestionDTO> questionDTOList = new ArrayList<>();
        List<QuizQuestion> lstQuizQuestions = quizQuestionRepository.findAll();
        lstQuizQuestions.forEach(quiz -> questionDTOList.add(
                QuizQuestionDTO.builder()
                        .questionId(quiz.getQuizQuestionId())
                        .categoryId(quiz.getCategoryId().getCategoryId())
                        .categoryName(categoryRepository.findCategoryById(quiz.getCategoryId().getCategoryId()).getCategoryName())
                        .questionContent(quiz.getContent())
                        .answersEntity(QuizAnswerMapper.mapQuizAnswerEntityToDTO(quizAnswerRepository.findByQuizQuestion(quiz)))
                        .build()));
        return questionDTOList;
    }

    @Override
    public List<QuizQuestion> getByContent(String content) {
        if (quizQuestionRepository.findByContentContaining(content).isEmpty()) {
            throw new FormatException("content", "QuizQuestion_Content not exist");
        }
        return quizQuestionRepository.findByContentContaining(content);
    }

    @Override
    public QuizQuestion createQuizQuestion(CreateQuizQuestionRequest quizQuestionRequest, Category category) {
        QuizQuestion quizQuestion = QuizQuestionMapper.mapCreateRequestToQuizQuestion(quizQuestionRequest,category);
        quizQuestionRepository.save(quizQuestion);
        for (CreateQuizAnswerRequest answerRequest : quizQuestionRequest.getAnswers()) {
            quizAnswerService.createQuizAnswer(answerRequest, quizQuestion);
        }
        return quizQuestion;
    }

    @Override
    public ResponseEntity<QuizQuestion> updateQuizQuestion(int quizQuestionId, @Valid @RequestBody QuizQuestionRequest quizRequest) {
        // Find the quiz question by ID
        Optional<QuizQuestion> optionalQuizQuestion = quizQuestionRepository.findById((long) quizQuestionId);

        if (optionalQuizQuestion.isPresent()) {
            QuizQuestion quizQuestion = optionalQuizQuestion.get();
            //check category_id
            if (categoryRepository.findById(quizRequest.getCategoryId()).isEmpty()) {
                throw new FormatException("CategoryId", "CategoryId not exist");
            }
            Category category = categoryRepository.findCategoryById(quizRequest.getCategoryId());

            // Update quiz question fields
            quizQuestion.setContent(quizRequest.getQuestionContent());
            quizQuestion.setCategoryId(category);
            // Save the updated quiz question
            quizQuestionRepository.save(quizQuestion);

            // update quiz answer
            if (quizRequest.getAnswersEntity() != null && !quizRequest.getAnswersEntity().isEmpty()) {
                List<QuizAnswer> quizAnswers = new ArrayList<>();
                for (QuizAnswerDTO answerRequest : quizRequest.getAnswersEntity()) {
                    QuizAnswer quizAnswer = QuizAnswer.builder()
                            .content(answerRequest.getContent())
                            .isCorrect(answerRequest.isIsCorrect())
                            .createdAt(LocalDateTime.now())
                            .quizQuestion(quizQuestion)
                            .build();

                    quizAnswers.add(quizAnswer);
                }
                quizAnswerRepository.saveAll(quizAnswers);
            }
            // Return the updated quiz question
            return ResponseEntity.ok(quizQuestion);
        } else {
            // If quiz question with the given ID is not found, return not found status
            throw new FormatException("quizQuestionId", "quizQuestionId not exist");

        }

    }

    @Override
    public QuizQuestion getQuizQuestionById(Long quizQuestionId) {
        if (quizQuestionId == null) {
            throw new FormatException("quizQuestionId", "quizQuestionId not exist");
        }
        return quizQuestionRepository.findById(quizQuestionId).orElse(null);
    }


    @Override
    public ResponseEntity<String> deleteQuizById(Long quizQuestionId) {

        Optional<QuizQuestion> optionalQuizQuestion = quizQuestionRepository.findById((long) quizQuestionId);
        if (optionalQuizQuestion.isPresent()) {
            try {
                quizAnswerRepository.deleteByQuizQuestionId(quizQuestionId);
                quizQuestionRepository.deleteQuestionById(quizQuestionId);
                return ResponseEntity.ok().body("Delete successfully quizQuestion ");
            } catch (Exception e) {
                return null;
            }
        } else {
            throw new FormatException("quizQuestionId", "quizQuestionId not exist");
        }
    }
}
