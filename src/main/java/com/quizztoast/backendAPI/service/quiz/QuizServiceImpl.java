package com.quizztoast.backendAPI.service.quiz;

import com.quizztoast.backendAPI.exception.FormatException;
import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.*;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.mapper.QuizMapper;
import com.quizztoast.backendAPI.model.payload.request.QuizAnswerRequest;
import com.quizztoast.backendAPI.model.payload.request.QuizQuestionRequest;
import com.quizztoast.backendAPI.model.payload.request.QuizRequest;
import com.quizztoast.backendAPI.model.payload.response.QuizQuestionResponse;
import com.quizztoast.backendAPI.model.payload.response.QuestionData;
import com.quizztoast.backendAPI.repository.*;
import com.quizztoast.backendAPI.service.quiz.QuizService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static com.quizztoast.backendAPI.model.mapper.QuizMapper.mapQuizDTOToUser;
import static com.quizztoast.backendAPI.model.mapper.QuizMapper.quizToQuizDTO;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuizAnswerRepository quizAnswerRepository;

    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizQuestionMappingRepository quizQuestionMappingRepository;
    private final DoQuizRepository doQuizRepository;
    private final CreateQuizCategory createQuizCategory;


    public QuizServiceImpl(CategoryRepository categoryRepository, UserRepository userRepository, QuizRepository quizRepository, QuizAnswerRepository quizAnswerRepository, QuizQuestionRepository quizQuestionRepository, QuizQuestionMappingRepository quizQuestionMappingRepository, DoQuizRepository doQuizRepository, CreateQuizCategory createQuizCategory) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizQuestionMappingRepository = quizQuestionMappingRepository;
        this.doQuizRepository = doQuizRepository;
        this.createQuizCategory = createQuizCategory;
    }

    @Override
    public List<QuizDTO> getAllQuiz() {
        List<Quiz> listQuiz = quizRepository.findAll();
        return quizToQuizDTO(listQuiz);
    }

    @Override
    public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        // Check user_id must be in User table
        if (!userRepository.existsById(quizRequest.getUserId())) {
            throw new FormatException("userId", "userId not found");
        }

        // Check category
        if (!categoryRepository.existsById(quizRequest.getCategoryId())) {
            throw new FormatException("categoryId", "categoryId not found");
        }

        // Create Quiz object from quizRequest
        Quiz quiz = new Quiz();
        quiz.setUser(userRepository.findById(quizRequest.getUserId()).orElse(null));
        quiz.setCategory(categoryRepository.findById(quizRequest.getCategoryId()).orElse(null));
        quiz.setQuizName(quizRequest.getQuizName());
        quiz.setViewOfQuiz(0L);
        quiz.setRate(quizRequest.getRate());
        quiz.setNumberOfQuizQuestion(quizRequest.getListQuestion().size());
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setTimeRecentViewQuiz(null);
        // Save the Quiz object to the Quiz table
        quizRepository.save(quiz);
        //save to table Create Quiz
        CreateQuiz createQuiz = new CreateQuiz();
        CreateQuiz.CreateQ createQId = new CreateQuiz.CreateQ();
        createQId.setUser(userRepository.findByUserId(quizRequest.getUserId()));
        createQId.setQuiz(quiz);
        createQuiz.setId(createQId);
        createQuizCategory.save(createQuiz);
        // Iterate through quiz questions
        for (QuizQuestionRequest quizQuestionRequest : quizRequest.getListQuestion()) {
            // Set quizQuestion properties
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setCreatedAt(LocalDateTime.now());
            quizQuestion.setContent(quizQuestionRequest.getQuestionContent());
            quizQuestion.setCategoryId(categoryRepository.findCategoryById(quizQuestionRequest.getCategoryId()));

            // Save quizQuestion to Quiz_question table
            quizQuestionRepository.saveAndFlush(quizQuestion);

            // Iterate through quiz answers
            for (QuizAnswerRequest quizAnswerRequest : quizQuestionRequest.getAnswers()) {
                // Set quizAnswer properties
                QuizAnswer quizAnswer = new QuizAnswer();
                quizAnswer.setContent(quizAnswerRequest.getContent());
                quizAnswer.setIsCorrect(quizAnswerRequest.isCorrect());
                quizAnswer.setCreatedAt(LocalDateTime.now());

                // Set quizQuestion for quizAnswer and save
                quizAnswer.setQuizQuestion(quizQuestion);
                quizAnswerRepository.saveAndFlush(quizAnswer);
            }

            // Create QuizQuestionMapping
            QuizQuestionMapping quizQuestionMapping = new QuizQuestionMapping();
            QuizQuestionMapping.QuizQuestionMappingId id = new QuizQuestionMapping.QuizQuestionMappingId();
            id.setQuizId(quiz);
            id.setQuizQuestionId(quizQuestion);
            quizQuestionMapping.setId(id);

            // Save QuizQuestionMapping
            quizQuestionMappingRepository.save(quizQuestionMapping);
        }

        // Mapper QuizDTO
        QuizDTO quizDTO = mapQuizDTOToUser(quiz);
        // Returns the QuizDTO object after creation
        return ResponseEntity.ok(quizDTO);
    }


    @Override
    public ResponseEntity<String> deleteQuizById(int quizId) {

        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
        // delete quiz
        quizRepository.deleteQuizById(quizId);

        return ResponseEntity.ok().body("Quiz deleted successfully");
    }

    @Override
    public ResponseEntity<QuizDTO> UpdateQuiz(int quizId, @Valid @RequestBody QuizRequest quizRequest) {
        // Check user_id must be in User table
        if (!userRepository.existsById(quizRequest.getUserId())) {
            throw new FormatException("userId", "userId not found");
        }
        //check quiz exist
        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
        //check category
        if (!categoryRepository.existsById(quizRequest.getCategoryId())) {
            throw new FormatException("categoryId", "categoryId not found");
        }
        //update quiz
        Quiz quiz = quizRepository.getQuizById(quizId);
        quiz.setUser(userRepository.findById(quizRequest.getUserId()).orElse(null));
        quiz.setQuizName(quizRequest.getQuizName());
        quiz.setCategory(categoryRepository.findById(quizRequest.getCategoryId()).orElse(null));
        quiz.setRate(quizRequest.getRate());
        quiz.setCreatedAt(quizRequest.getCreateAt());
        //save quiz
        quizRepository.save(quiz);
        //mapper QuizDTO
        QuizDTO quizDTo = mapQuizDTOToUser(quiz);
        // Returns the QuizDTO object after creation
        return ResponseEntity.ok(quizDTo);
    }


    @Override
    public ResponseEntity<?> getQuizQuestionsAndAnswersByQuizId(int quizId) {
        Quiz quiz = quizRepository.getQuizById(quizId);

        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }

        QuizQuestionResponse response = getQuizQuestionResponse(quizId, quiz);

        List<QuizQuestion> quizQuestionIds = quizQuestionMappingRepository.findQuizQuestionIdsByQuizId(quizId);

        for (QuizQuestion quizQuestionIdObject : quizQuestionIds) {
            QuestionData questionData = new QuestionData();
            QuizQuestion quizQuestion = quizQuestionRepository.findByQuizQuestionId(quizQuestionIdObject.getQuizQuestionId());

            questionData.setQuestionContent(quizQuestion.getContent());

            List<QuizAnswerDTO> quizAnswerDTOList = new ArrayList<>();

            List<QuizAnswer> quizAnswersList = quizAnswerRepository.findByQuizQuestion(quizQuestion);
            for (QuizAnswer quizAnswerEntity : quizAnswersList) {
                QuizAnswerDTO quizAnswerDTO = new QuizAnswerDTO();
                quizAnswerDTO.setContent(quizAnswerEntity.getContent());
                quizAnswerDTO.setIsCorrect(quizAnswerEntity.getIsCorrect());
                quizAnswerDTOList.add(quizAnswerDTO);
            }

            questionData.setAnswers(quizAnswerDTOList);
            response.getQuestions().add(questionData);
        }

        return ResponseEntity.ok(response);
    }

    private static QuizQuestionResponse getQuizQuestionResponse(int quizId, Quiz quiz) {
        QuizQuestionResponse response = new QuizQuestionResponse();
        response.setUserId(quiz.getUser().getUserId());
        response.setQuizId(quizId);
        response.setUserName(quiz.getUser().getUserName());
        response.setUserFirstName(quiz.getUser().getFirstName());
        response.setUserLastName(quiz.getUser().getLastName());
        response.setCategoryId(quiz.getCategory().getCategoryId());
        response.setCategoryName(quiz.getCategory().getCategoryName());
        response.setQuizName(quiz.getQuizName());
        response.setRate(quiz.getRate());
        response.setNumberOfQuestions(quiz.getNumberOfQuizQuestion());
        response.setCreateAt(quiz.getCreatedAt());
        response.setView(quiz.getViewOfQuiz());
        response.setTimeRecentViewQuiz(quiz.getTimeRecentViewQuiz());
        response.setQuestions(new ArrayList<>());
        return response;
    }


    @Override
    public List<QuizDTO> GetQuizByContent(String QuizName) {
        if (quizRepository.findByQuizNameContaining(QuizName).isEmpty()) {
            throw new FormatException("quiz_name", "Quiz not exist");
        }
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for (Quiz quiz : quizRepository.findByQuizNameContaining(QuizName)) {
            QuizDTO quizDTO = mapQuizDTOToUser(quiz);
            quizDTOList.add(quizDTO);
        }
        return quizDTOList;
    }

    @Override
    public ResponseEntity<?> increaseView(int quizId) {
        //find quiz by quizid
        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
        Quiz quiz = quizRepository.getQuizById(quizId);
        // update view +1
        quiz.setViewOfQuiz(quiz.getViewOfQuiz() + 1);
        //save to db
        quizRepository.save(quiz);
        return ResponseEntity.ok("increase Succesfull!");
    }

    @Override
    public ResponseEntity<?> upDateTimeQuiz(int quizId) {
        //find quiz by quizid
        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
        Quiz quiz = quizRepository.getQuizById(quizId);
        quiz.setTimeRecentViewQuiz(LocalDateTime.now());
        quizRepository.save(quiz);
        return ResponseEntity.ok(quiz.getTimeRecentViewQuiz());
    }

    @Override
    public List<DoQuiz> getLearnedQuizzesByUser(User user) {
        return doQuizRepository.findByIdUser(user);
    }

    @Override
    public ResponseEntity<?> GetQuizCreateByUser(Long userId) {
        if (userRepository.findByUserId(userId) == null) {
            throw new FormatException("userId", "userId not found");
        }
//get list quizId from CreateQuiz
        List<QuizDTO> listQuizDTO = new ArrayList<>();
        for (Integer quizId : quizRepository.findQuizId(userId)) {
            Quiz quiz = quizRepository.getQuizById(quizId);
            QuizDTO quizDTO = mapQuizDTOToUser(quiz);
            listQuizDTO.add(quizDTO);
        }
        return ResponseEntity.ok(listQuizDTO);
    }
}



