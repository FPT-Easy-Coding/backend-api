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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static com.quizztoast.backendAPI.model.mapper.QuizMapper.mapQuizDTOToUser;


@Service
@Transactional
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuizAnswerRepository quizAnswerRepository;

    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizQuestionMappingRepository quizQuestionMappingRepository;
    private final DoQuizRepository doQuizRepository;
    private final CreateQuizCategoryRepository createQuizCategoryRepository;



    @Override
    public List<QuizDTO> getAllQuiz() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizDTO> quizDTOs = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            int questionCount = quizQuestionMappingRepository.countQuizQuestionByQuizID(quiz.getQuizId());
            QuizDTO quizDTO = QuizMapper.mapQuizToQuizDTO(quiz, questionCount);
            quizDTOs.add(quizDTO);
        }
        return quizDTOs;
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
//        quiz.setNumberOfQuizQuestion(quizRequest.getListQuestion().size());
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
        createQuizCategoryRepository.save(createQuiz);
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
        int numberOfQuestion = quizQuestionMappingRepository.countQuizQuestionByQuizID(quiz.getQuizId());
        // Mapper QuizDTO
        QuizDTO quizDTO = mapQuizDTOToUser(quiz,numberOfQuestion);
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

        // Check quiz exist
        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }

        // Check category
        if (!categoryRepository.existsById(quizRequest.getCategoryId())) {
            throw new FormatException("categoryId", "categoryId not found");
        }



        // Save quiz questions, answers, and mappings
        for (QuizQuestionRequest questionRequest : quizRequest.getListQuestion()) {
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setCategoryId(categoryRepository.findById(quizRequest.getCategoryId()).orElse(null));
            quizQuestion.setContent(questionRequest.getQuestionContent());
            quizQuestion.setCreatedAt(LocalDateTime.now());

            // Save question
            quizQuestionRepository.save(quizQuestion);

            // Save answers
            for (QuizAnswerRequest answerRequest : questionRequest.getAnswers()) {
                QuizAnswer quizAnswer = new QuizAnswer();
                quizAnswer.setContent(answerRequest.getContent());
                quizAnswer.setIsCorrect(answerRequest.isCorrect());
quizAnswer.setCreatedAt(LocalDateTime.now());
quizAnswer.setQuizQuestion(quizQuestion);
                // Save answer
                quizAnswerRepository.save(quizAnswer);

                // Map question and answer
                QuizQuestionMapping.QuizQuestionMappingId mappingId = new QuizQuestionMapping.QuizQuestionMappingId();
                mappingId.setQuizId(quizRepository.getQuizById(quizId));
                mappingId.setQuizQuestionId(quizQuestion);

                QuizQuestionMapping mapping = new QuizQuestionMapping();
                mapping.setId(mappingId);

                // Save mapping
                quizQuestionMappingRepository.save(mapping);


                // Save mapping
                quizQuestionMappingRepository.save(mapping);
            }
        }
        // Update quiz
        Quiz quiz = quizRepository.getQuizById(quizId);
        quiz.setUser(userRepository.findById(quizRequest.getUserId()).orElse(null));
        quiz.setQuizName(quizRequest.getQuizName());
        quiz.setCategory(categoryRepository.findById(quizRequest.getCategoryId()).orElse(null));
        quiz.setRate(quizRequest.getRate());
        quiz.setCreatedAt(quizRequest.getCreateAt());
        quiz.setNumberOfQuizQuestion(quizQuestionMappingRepository.countNumberofquiz(quiz));
        // Save quiz
        quizRepository.save(quiz);
        //mapper QuizDTO
        int numberOfQuestion = quizQuestionMappingRepository.countQuizQuestionByQuizID(quiz.getQuizId());
        QuizDTO quizDTo = mapQuizDTOToUser(quiz,numberOfQuestion);
        // Returns the QuizDTO object after creation
        return ResponseEntity.ok(quizDTO);
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

    private QuizQuestionResponse getQuizQuestionResponse(int quizId, Quiz quiz) {
        int numberOfQuestions = quizQuestionMappingRepository.countQuizQuestionByQuizID(quizId);
        return QuizQuestionResponse.builder()
                .userId(quiz.getUser().getUserId())
                .quizId(quizId)
                .userName(quiz.getUser().getUserName())
                .userFirstName(quiz.getUser().getFirstName())
                .userLastName(quiz.getUser().getLastName())
                .categoryId(quiz.getCategory().getCategoryId())
                .categoryName(quiz.getCategory().getCategoryName())
                .quizName(quiz.getQuizName())
                .rate(quiz.getRate())
                .numberOfQuestions(numberOfQuestions)
                .createAt(quiz.getCreatedAt())
                .view(quiz.getViewOfQuiz())
                .timeRecentViewQuiz(quiz.getTimeRecentViewQuiz())
                .questions(new ArrayList<>())
                .build();
    }



    @Override
    public List<QuizDTO> GetQuizByContent(String QuizName) {
        if (quizRepository.findByQuizNameContaining(QuizName).isEmpty()) {
            throw new FormatException("quiz_name", "Quiz not exist");
        }
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for (Quiz quiz : quizRepository.findByQuizNameContaining(QuizName)) {
            int numberOfQuestion = quizQuestionMappingRepository.countQuizQuestionByQuizID(quiz.getQuizId());
            QuizDTO quizDTO = mapQuizDTOToUser(quiz,numberOfQuestion);
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
            int numberOfQuestions = getNumberOfQuestionsByQuizId(quizId);
            QuizDTO quizDTO = mapQuizDTOToUser(quiz,numberOfQuestions);
            listQuizDTO.add(quizDTO);
        }
        return ResponseEntity.ok(listQuizDTO);
    }

    @Override
    public int getNumberOfQuestionsByQuizId(int quizId) {
        // Your logic to compute the number of questions
        return quizQuestionMappingRepository.countQuizQuestionByQuizID(quizId);
    }

    public ResponseEntity<?> getQuizByCategory(int categoryId) {
        //check category id
        if (categoryRepository.findCategoryById(categoryId) == null) {
            throw new FormatException("CategoryId", "CategoryId is not exist");
        }
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for(Quiz quiz :quizRepository.findQuizByCategory(categoryId))
        {
            QuizDTO quizDTO = mapQuizDTOToUser(quiz);
            quizDTOList.add(quizDTO);
        }
        return ResponseEntity.ok(quizDTOList);
    }
}



