package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.model.dto.ClassroomDTO;
import com.quizztoast.backendAPI.model.dto.ListResponseDTO;
import com.quizztoast.backendAPI.model.entity.classroom.*;

import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.user.User;

import com.quizztoast.backendAPI.model.mapper.QuizMapper;
import com.quizztoast.backendAPI.model.payload.request.ClassroomRequest;
import com.quizztoast.backendAPI.model.payload.request.CommentRequest;
import com.quizztoast.backendAPI.model.payload.response.*;

import com.quizztoast.backendAPI.repository.QuizBelongClassroomRepository;
import com.quizztoast.backendAPI.repository.UserBelongClassroomRepository;
import com.quizztoast.backendAPI.service.classroom.AnswerRequest;
import com.quizztoast.backendAPI.service.classroom.ClassroomServiceImpl;
import com.quizztoast.backendAPI.service.quiz.QuizServiceImpl;
import com.quizztoast.backendAPI.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/classroom")
@RequiredArgsConstructor
@Tag(name = "Classroom")
public class ClassroomController {
    private final UserServiceImpl userServiceImpl;
    private final ClassroomServiceImpl classroomServiceImpl;
    private final QuizServiceImpl quizServiceImpl;
    private final QuizBelongClassroomRepository quizBelongClassroomRepository;
    private final UserBelongClassroomRepository userBelongClassroomRepository;

    @RequestMapping(value = "/class-member/class-id={classId}", method = RequestMethod.GET)
    public ResponseEntity<List<ClassMemberResponse>> getMembersOfClassroom(@PathVariable int classId) {
        return ResponseEntity.ok(classroomServiceImpl.getClassMembers(classId));

    }

    @GetMapping("/learned/{userId}")
    @RequestMapping(value = "learned/user-id={userId}", method = RequestMethod.GET)
    public ResponseEntity<List<ClassroomToProfileResponse>> getLearnedQuizzesByUser(@PathVariable Long userId) {
        User user = userServiceImpl.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<UserBelongClassroom> usersInClassroom = classroomServiceImpl.getListClassroomByUser(user);

        if (usersInClassroom == null || usersInClassroom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ClassroomToProfileResponse> classes = new ArrayList<>();
        for (UserBelongClassroom userBelongClassroom : usersInClassroom) {
            Classroom classroom = userBelongClassroom.getId().getClassroom();
            ClassroomToProfileResponse response = classroomServiceImpl.getClassroomCardDetails(classroom);
            classes.add(response);
        }

        return ResponseEntity.ok(classes);
    }

    @RequestMapping(value = "/quiz-belong-class/class-id={classId}", method = RequestMethod.GET)
    public ResponseEntity<List<QuizSetResponse>> getQuizBelongClassroom(@PathVariable int classId) {
        Classroom classroom = classroomServiceImpl.findClassroomById(classId);
        if (classroom == null) {
            return ResponseEntity.notFound().build();
        }

        List<QuizBelongClassroom> quizSetsInClassroom = classroomServiceImpl.getListQuizByClassroom(classroom);

        if (quizSetsInClassroom == null || quizSetsInClassroom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<QuizSetResponse> quizSetResponses = new ArrayList<>();
        for (QuizBelongClassroom quizBelongClassroom : quizSetsInClassroom) {
            Quiz quiz = quizBelongClassroom.getId().getQuiz();
            int numberOfQuestions = quizServiceImpl.getNumberOfQuestionsByQuizId(quiz.getQuizId());
            QuizSetResponse response = QuizMapper.mapQuizToQuizSetResponse(quiz, numberOfQuestions);
            quizSetResponses.add(response);
        }

        return ResponseEntity.ok(quizSetResponses);
    }

    @PostMapping("/create-classroom")
    public ResponseEntity<String> createClassroom(@RequestBody ClassroomRequest classroomRequest) {
        try {
            Classroom classroom = classroomServiceImpl.createClassroom(classroomRequest);
            return ResponseEntity.ok("Classroom created with ID: " + classroom.getClassroomId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating classroom: " + e.getMessage());
        }
    }

    @RequestMapping(value = "update-classroom/classroom-id={classroomId}", method = RequestMethod.PUT)
    public ResponseEntity<ClassroomDTO> updateClassroom(
            @PathVariable int classroomId,
            @Valid
            @RequestBody ClassroomRequest classroomRequest) {
        return ResponseEntity.ok(classroomServiceImpl.updateClassroom(classroomId, classroomRequest));
    }

    @RequestMapping(value = "/class-id={classId}", method = RequestMethod.GET)
    public ResponseEntity<ClassroomResponse> getClassroomById(@PathVariable int classId) {
        Classroom classroom = classroomServiceImpl.findClassroomById(classId);
        if (classroom == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(classroomServiceImpl.getClassroomDetails(classroom));
    }

    @RequestMapping(value = "/delete-classroom/class-id={classId}", method = RequestMethod.DELETE)
    public ResponseEntity<MessageResponse> deleteClassroom(@PathVariable int classId) {
        Classroom classroom = classroomServiceImpl.findClassroomById(classId);
        classroomServiceImpl.deleteClassroom(classroom);
        return ResponseEntity.ok(MessageResponse.builder()
                .success(true)
                .msg("Delete classroom successfully")
                .build());
    }


    @DeleteMapping("/delete-quiz/{classroomId}/quiz-set/{quizId}")
    public ResponseEntity<MessageResponse> deleteQuizFromClassroom(@PathVariable int classroomId, @PathVariable int quizId) {
        try {
            QuizBelongClassroom quizBelongClassroom = quizBelongClassroomRepository.findByIdClassroomAndQuiz(classroomId, quizId);
            if (quizBelongClassroom == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        MessageResponse.builder()
                                .success(false)
                                .msg("Quiz not found")
                                .build()
                );
            }
            classroomServiceImpl.deleteQuizFromClassroom(classroomId, quizId);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Delete quiz successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error deleting quiz: " + e.getMessage())
                            .build()
            );
        }


    }

    @DeleteMapping("/remove-member/{classroomId}/user/{userId}")
    public ResponseEntity<MessageResponse> deleteClassroomUser
            (
                    @PathVariable int classroomId,
                    @PathVariable int userId
            ) {
        try {
            UserBelongClassroom userBelongClassroom = userBelongClassroomRepository.findByIdClassroomAndUser(classroomId, userId);
            if (userBelongClassroom == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        MessageResponse.builder()
                                .success(false)
                                .msg("User not found")
                                .build()
                );
            }
            classroomServiceImpl.removeUserFromClassroom(classroomId, userId);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Delete user successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error deleting user: " + e.getMessage())
                            .build());
        }
    }

    @PostMapping("/add-member/{classroomId}/user/{userId}")
    public ResponseEntity<MessageResponse> addClassroomUser
            (
                    @PathVariable int classroomId,
                    @PathVariable Long userId
            ) {
        try {
            classroomServiceImpl.addUserToClassroom(classroomId, userId);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Add user successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error adding user: " + e.getMessage())
                            .build());
        }


    }

    @PostMapping("/add-quiz/{classroomId}/quiz/{quizId}")
    public ResponseEntity<MessageResponse> addQuizToClassroom(
            @PathVariable int classroomId,
            @PathVariable int quizId
    ) {
        try {
            classroomServiceImpl.addQuizToClassroom(classroomId, quizId);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Add quiz successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error adding quiz: " + e.getMessage())
                            .build());
        }
    }

    @RequestMapping(value = "/get-classroom-questions/class-id={classroomId}", method = RequestMethod.GET)
    public ResponseEntity<ListResponseDTO> getClassroomQuestions(@PathVariable int classroomId) {

        try {
            Classroom classroom = classroomServiceImpl.findClassroomById(classroomId);
            List<ClassroomQuestionResponse> classroomQuestions = classroomServiceImpl.getClassroomQuestions(classroomId);
            MessageResponse messageResponse;
            if (classroom == null) {
                messageResponse = MessageResponse.builder()
                        .success(false)
                        .msg("Class not found")
                        .build();
            } else {

                if (classroomQuestions == null || classroomQuestions.isEmpty()) {
                    messageResponse = MessageResponse.builder()
                            .success(false)
                            .msg("No questions found")
                            .build();
                } else {
                    messageResponse = MessageResponse.builder()
                            .success(true)
                            .msg("Success")
                            .build();

                }
            }
            return ResponseEntity.status(getHttpStatus(messageResponse)).body(
                    new ListResponseDTO(messageResponse, classroomQuestions)
            );
        } catch (Exception e) {
            MessageResponse messageResponse = MessageResponse.builder()
                    .success(false)
                    .msg("Internal server error")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ListResponseDTO(messageResponse, null)
            );
        }
    }

    private HttpStatus getHttpStatus(MessageResponse messageResponse) {
        return messageResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    @RequestMapping(value = "/add-comment", method = RequestMethod.POST)
    public ResponseEntity<MessageResponse> addComment(@RequestBody CommentRequest commentRequest) {

        try {
            Comment comment = classroomServiceImpl.addComment(commentRequest);
            if (comment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        MessageResponse.builder()
                                .success(false)
                                .msg("User or Question not found")
                                .build());
            }
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Add comment successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error adding comment: " + e.getMessage())
                            .build());
        }
    }

    @RequestMapping(value = "/get-comments/question-id={questionId}", method = RequestMethod.GET)
    public ResponseEntity<ListResponseDTO> getCommentsForQuestion(@PathVariable int questionId) {

        try {
            List<CommentResponse> comments = classroomServiceImpl.getCommentsByQuestion(questionId);
            MessageResponse messageResponse;
            if (comments == null || comments.isEmpty()) {
                messageResponse = MessageResponse.builder()
                        .success(false)
                        .msg("No comments found")
                        .build();
            } else {
                messageResponse = MessageResponse.builder()
                        .success(true)
                        .msg("Success")
                        .build();
            }
            return ResponseEntity.status(getHttpStatus(messageResponse)).body(
                    new ListResponseDTO(messageResponse, comments)
            );
        } catch (Exception e) {
            MessageResponse messageResponse = MessageResponse.builder()
                    .success(false)
                    .msg("Internal server error")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ListResponseDTO(messageResponse, null)
            );
        }
    }

    @RequestMapping(value = "/add-answer", method = RequestMethod.POST)
    public ResponseEntity<MessageResponse> addAnswer(@RequestBody AnswerRequest answerRequest) {

        try {
            ClassroomAnswer classroomAnswer = classroomServiceImpl.addAnswer(answerRequest);
            if (classroomAnswer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        MessageResponse.builder()
                                .success(false)
                                .msg("User or Question not found")
                                .build());
            }
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Add answer successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error adding answer: " + e.getMessage())
                            .build());
        }
    }

}
