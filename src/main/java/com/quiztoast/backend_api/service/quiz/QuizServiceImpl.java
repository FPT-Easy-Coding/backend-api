package com.quiztoast.backend_api.service.quiz;

import com.quiztoast.backend_api.exception.FormatException;
import com.quiztoast.backend_api.model.dto.QuizAnswerDTO;
import com.quiztoast.backend_api.model.dto.QuizDTO;

import com.quiztoast.backend_api.model.entity.quiz.*;
import com.quiztoast.backend_api.model.entity.user.User;
import com.quiztoast.backend_api.model.mapper.QuizMapper;
import com.quiztoast.backend_api.model.payload.request.*;
import com.quiztoast.backend_api.model.payload.response.QuizQuestionResponse;
import com.quiztoast.backend_api.model.payload.response.QuestionData;
import com.quiztoast.backend_api.model.payload.response.RateQuizResponse;

import com.quiztoast.backend_api.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import static com.quiztoast.backend_api.model.mapper.QuizMapper.mapQuizDTOToUser;


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
    private final RateQuizRepository rateQuizRepository;
    private final TokenRepository tokenRepository;
    private final QuizQuestionServiceImpl quizQuestionServiceImpl;
    private final QuizBelongFolderRepository quizBelongFolderRepository;
    private final QuizBelongClassroomRepository quizBelongClassroomRepository;

    @Override
    public List<QuizDTO> getAllQuiz() {
        List<Quiz> quizzes = quizRepository.findAll();
        List<QuizDTO> quizDTOs = new ArrayList<>();
        for (Quiz quiz : quizzes) {
            int questionCount = quizQuestionMappingRepository.countQuizQuestionByQuizID(quiz.getQuizId());
            QuizDTO quizDTO = QuizMapper.mapQuizDTOToUser(quiz, questionCount, rateQuizRepository);
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
        quiz.setCreatedAt(LocalDateTime.now());
        quiz.setTimeRecentViewQuiz(null);
        quiz.setDescription(quizRequest.getDescription());
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
            id.setQuiz(quiz);
            id.setQuizQuestion(quizQuestion);
            quizQuestionMapping.setId(id);

            // Save QuizQuestionMapping
            quizQuestionMappingRepository.save(quizQuestionMapping);
        }
        int numberOfQuestion = quizQuestionMappingRepository.countQuizQuestionByQuizID(quiz.getQuizId());
        // Mapper QuizDTO
        QuizDTO quizDTO = mapQuizDTOToUser(quiz, numberOfQuestion, rateQuizRepository);
        // Returns the QuizDTO object after creation
        return ResponseEntity.ok(quizDTO);
    }

    @Override
    public Quiz createQuizSet(CreateQuizRequest createQuizRequest) {
        User user = userRepository.findById(createQuizRequest.getUserId()).orElse(null);
        Category category = categoryRepository.findById(createQuizRequest.getCategoryId()).orElse(null);
        Quiz quiz = QuizMapper.mapCreateRequestToQuiz(createQuizRequest, user, category);
        quizRepository.save(quiz);
        for (CreateQuizQuestionRequest createQuizQuestionRequest : createQuizRequest.getQuestions()) {
            QuizQuestion quizQuestion = quizQuestionServiceImpl.createQuizQuestion(createQuizQuestionRequest, category);
            QuizQuestionMapping.QuizQuestionMappingId id = new QuizQuestionMapping.QuizQuestionMappingId(quiz, quizQuestion);
            QuizQuestionMapping quizQuestionMapping = new QuizQuestionMapping(id);
            quizQuestionMappingRepository.save(quizQuestionMapping);
        }
        return quiz;
    }

    public Quiz updateQuiz(UpdateQuizRequest updateQuizRequest) {
        User user = userRepository.findById(updateQuizRequest.getUserId()).orElse(null);
        Category category = categoryRepository.findById(updateQuizRequest.getCategoryId()).orElse(null);
        Quiz quiz = quizRepository.getQuizById(updateQuizRequest.getQuizId());
        if (user != quiz.getUser()) {
            return null;
        }
        Quiz updatedQuiz = QuizMapper.mapUpdateRequestToQuiz(quiz,updateQuizRequest, category);
        quizRepository.save(updatedQuiz);
        for (UpdateQuizQuestionRequest updateQuizQuestionRequest : updateQuizRequest.getQuestions()) {
            quizQuestionServiceImpl.updateQuizQuestion(updateQuizQuestionRequest, category, quiz);
        }
        return updatedQuiz;
    }


    @Override
    public ResponseEntity<String> deleteQuizById(int quizId, Long userId) {
        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
        if (!userRepository.existsById(userId)) {
            throw new FormatException("userId", "userId not found");
        }
        //check user delete quiz
        Quiz quiz = quizRepository.getQuizById(quizId);
        if (!Objects.equals(quiz.getUser().getUserId(), userId)) {
            throw new FormatException("userId", "userId doesn't match! ");
        }

        //delete quiz in quiz_question_mapping
        quizQuestionMappingRepository.deleteByQuizId(quizId);
        // delete quiz in quiz_rating
        if (rateQuizRepository.findQuizByQuizId(quizId) != null) {
            rateQuizRepository.deleteByQuizId(quizId);
        }
        //delete quiz in quiz_belong_folder
        if (quizBelongFolderRepository.findByQuizId(quizId) != null) {
            quizBelongFolderRepository.deleteQuizBelongFolderByQuizID(quizId);
        }
        //delete quiz in quiz_belong_class
        if (quizBelongClassroomRepository.findQuizId(quizId) != null) {
            quizBelongClassroomRepository.deleteQuizSetsByQuizId(quizId);
        }
        // delete quiz
        quizRepository.deleteQuizById(quizId);

        return ResponseEntity.ok().body("Quiz deleted successfully");
    }

    @Override
    public ResponseEntity<QuizDTO> updateQuiz(int quizId, @Valid @RequestBody QuizRequest quizRequest) {
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
                mappingId.setQuiz(quizRepository.getQuizById(quizId));
                mappingId.setQuizQuestion(quizQuestion);

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
        // Save quiz
        quizRepository.save(quiz);
        //mapper QuizDTO
        int numberOfQuestion = quizQuestionMappingRepository.countQuizQuestionByQuizID(quiz.getQuizId());
        QuizDTO quizDTo = mapQuizDTOToUser(quiz, numberOfQuestion, rateQuizRepository);
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
            questionData.setQuestionId(quizQuestion.getQuizQuestionId());
            List<QuizAnswerDTO> quizAnswerDTOList = new ArrayList<>();
            List<QuizAnswer> quizAnswersList = quizAnswerRepository.findByQuizQuestion(quizQuestion);
            for (QuizAnswer quizAnswerEntity : quizAnswersList) {
                QuizAnswerDTO quizAnswerDTO = new QuizAnswerDTO();
                quizAnswerDTO.setAnswerId(quizAnswerEntity.getQuizAnswerId());
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
                .avatar(quiz.getUser().getAvatar())
                .quizName(quiz.getQuizName())
                .rate(quiz.getRate())
                .numberOfQuestions(numberOfQuestions)
                .description(quiz.getDescription())
                .createAt(quiz.getCreatedAt())
                .view(quiz.getViewOfQuiz())
                .timeRecentViewQuiz(quiz.getTimeRecentViewQuiz())
                .questions(new ArrayList<>())
                .build();
    }


    @Override
    public List<QuizDTO> getQuizByContent(String QuizName) {
        if (quizRepository.findByQuizNameContaining(QuizName).isEmpty()) {
            throw new FormatException("quiz_name", "Quiz not exist");
        }
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for (Quiz quiz : quizRepository.findByQuizNameContaining(QuizName)) {
            int numberOfQuestion = quizQuestionMappingRepository.countQuizQuestionByQuizID(quiz.getQuizId());
            QuizDTO quizDTO = mapQuizDTOToUser(quiz, numberOfQuestion, rateQuizRepository);
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
    public ResponseEntity<?> getQuizCreatedByUser(Long userId) {
        if (userRepository.findByUserId(userId) == null) {
            throw new FormatException("userId", "userId not found");
        }
        //get list quizId from CreateQuiz
        List<QuizDTO> listQuizDTO = new ArrayList<>();
        for (Integer quizId : quizRepository.findQuizId(userId)) {
            Quiz quiz = quizRepository.getQuizById(quizId);
            int numberOfQuestions = getNumberOfQuestionsByQuizId(quizId);
            QuizDTO quizDTO = mapQuizDTOToUser(quiz, numberOfQuestions, rateQuizRepository);
            listQuizDTO.add(quizDTO);
        }
        return ResponseEntity.ok(listQuizDTO);
    }

    @Override
    public int getNumberOfQuestionsByQuizId(int quizId) {
        // Your logic to compute the number of questions
        return quizQuestionMappingRepository.countQuizQuestionByQuizID(quizId);
    }

    @Override
    public ResponseEntity<?> getQuizByCategory(int categoryId) {
        //check category id
        if (categoryRepository.findCategoryById(categoryId) == null) {
            throw new FormatException("CategoryId", "CategoryId is not exist");
        }
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for (Quiz quiz : quizRepository.findQuizByCategory(categoryId)) {
            int numberOfQuestions = quizQuestionMappingRepository.countQuizQuestionByQuizID(quiz.getQuizId());
            QuizDTO quizDTO = mapQuizDTOToUser(quiz, numberOfQuestions, rateQuizRepository);
            quizDTOList.add(quizDTO);
        }
        return ResponseEntity.ok(quizDTOList);
    }

    @Override
    public Float getRateByQuiz(int quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
        if (rateQuizRepository.averageRateOfQuiz(quizId) == null) {
            return (float) 0;
        }
        Quiz quiz = quizRepository.getQuizById(quizId);
        return rateQuizRepository.averageRateOfQuiz(quizId);
    }

    @Override
    public ResponseEntity<RateQuizResponse> createRateQuiz(int quizId, long userId, float rate) {
//        //check token
//        if(tokenRepository.findTokenByUserId(userId) != token)
//        {
//
//        }
        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
        if (userRepository.findByUserId(userId) == null) {
            throw new FormatException("userId", "userId not found");
        }
        if (rate < 1 || rate > 5) {
            throw new FormatException("rate", "min rate =1 and max rate =5");
        }
        if (rate % 0.5 != 0) {
            throw new FormatException("rate", "rate of space = 0.5");
        }
//add to rate_quiz
        RateQuiz.RateQuizId rateQuizId = new RateQuiz.RateQuizId();
        rateQuizId.setUserId(userRepository.findByUserId(userId));
        rateQuizId.setQuizId(quizRepository.getQuizById(quizId));
        rateQuizId.setRate(rate);
        rateQuizId.setCreateAt();
        RateQuiz rateQuiz = new RateQuiz();
        rateQuiz.setId(rateQuizId);
        rateQuizRepository.save(rateQuiz);

        RateQuizResponse rateQuizResponse = new RateQuizResponse();
        rateQuizResponse.setQuizId(quizId);
        rateQuizResponse.setUserId(userId);
        rateQuizResponse.setRate(rate);
        rateQuizResponse.setCreateAt(rateQuizId.getCreateAt());
        rateQuizResponse.setIsRated(true);
        return ResponseEntity.ok(rateQuizResponse);
    }

    @Override
    public ResponseEntity<RateQuizResponse> UpdateRateQuiz(int quizId, long userId, float rate) {
        if (!quizRepository.existsById(quizId)) {
            throw new FormatException("quizId", "Quiz with given ID not found");
        }
        if (userRepository.findByUserId(userId) == null) {
            throw new FormatException("userId", "userId not found");
        }
        if (rate < 1 || rate > 5) {
            throw new FormatException("rate", "min rate =1 and max rate =5");
        }
        if (rate % 0.5 != 0) {
            throw new FormatException("rate", "rate of space = 0.5");
        }
        if (rateQuizRepository.findByQuizIdAndUserId(quizId, userId) == null) {
            throw new FormatException("Rate Quiz", "don't exist");
        }
        //delete rateQUiz to rate_quiz
        rateQuizRepository.deleteByQuizIdAndUserId(
                quizId,
                userId
        );
        //add to rate_quiz
        RateQuiz.RateQuizId rateQuizId = new RateQuiz.RateQuizId();
        rateQuizId.setUserId(userRepository.findByUserId(userId));
        rateQuizId.setQuizId(quizRepository.getQuizById(quizId));
        rateQuizId.setRate(rate);
        rateQuizId.setCreateAt();
        RateQuiz rateQuiz = new RateQuiz();
        rateQuiz.setId(rateQuizId);
        rateQuizRepository.save(rateQuiz);

        RateQuizResponse rateQuizResponse = new RateQuizResponse();
        rateQuizResponse.setQuizId(quizId);
        rateQuizResponse.setUserId(userId);
        rateQuizResponse.setRate(rate);
        rateQuizResponse.setCreateAt(rateQuizId.getCreateAt());
        rateQuizResponse.setIsRated(true);
        return ResponseEntity.ok(rateQuizResponse);
    }

    @Override
    public ResponseEntity<?> getUserRateQuiz(@RequestBody Long userId, @RequestBody int quizId) {
        //check userId and quizId in rate_quiz
        RateQuizResponse rateQuizResponse = new RateQuizResponse();
        if (rateQuizRepository.findByQuizIdAndUserId(quizId, userId) != null) {
            RateQuiz rateQuiz = rateQuizRepository.findByQuizIdAndUserId(quizId, userId);
            rateQuizResponse.setUserId(rateQuiz.getId().getUserId());
            rateQuizResponse.setQuizId(rateQuiz.getId().getQuizId());
            rateQuizResponse.setRate(rateQuiz.getId().getRate());
            rateQuizResponse.setIsRated(true);
            rateQuizResponse.setCreateAt(rateQuiz.getId().getCreateAt());
            return ResponseEntity.ok(rateQuizResponse);
        } else {
            rateQuizResponse.setUserId(userId);
            rateQuizResponse.setQuizId(quizId);
            rateQuizResponse.setIsRated(false);
            return ResponseEntity.ok(rateQuizResponse);
        }
    }
}



