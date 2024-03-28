package com.quiztoast.backend_api.controller;

import com.quiztoast.backend_api.model.dto.ClassroomDTO;
import com.quiztoast.backend_api.model.dto.ListResponseDTO;

import com.quiztoast.backend_api.model.entity.classroom.Classroom;
import com.quiztoast.backend_api.model.entity.classroom.ClassroomAnswer;
import com.quiztoast.backend_api.model.entity.classroom.Comment;
import com.quiztoast.backend_api.model.entity.classroom.QuizBelongClassroom;
import com.quiztoast.backend_api.model.entity.classroom.ReplyComment;
import com.quiztoast.backend_api.model.entity.classroom.UserBelongClassroom;
import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.entity.user.User;

import com.quiztoast.backend_api.model.mapper.QuizMapper;
import com.quiztoast.backend_api.model.payload.request.*;

import com.quiztoast.backend_api.model.payload.response.ClassMemberResponse;
import com.quiztoast.backend_api.model.payload.response.ClassroomQuestionResponse;
import com.quiztoast.backend_api.model.payload.response.ClassroomResponse;
import com.quiztoast.backend_api.model.payload.response.ClassroomToProfileResponse;
import com.quiztoast.backend_api.model.payload.response.CommentResponse;
import com.quiztoast.backend_api.model.payload.response.MessageResponse;
import com.quiztoast.backend_api.model.payload.response.QuizSetResponse;
import com.quiztoast.backend_api.repository.QuizBelongClassroomRepository;
import com.quiztoast.backend_api.repository.UserBelongClassroomRepository;
import com.quiztoast.backend_api.service.classroom.ClassroomServiceImpl;
import com.quiztoast.backend_api.service.quiz.QuizServiceImpl;
import com.quiztoast.backend_api.service.user.UserServiceImpl;
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

    @GetMapping("/class-member/class-id={classId}")
    public ResponseEntity<List<ClassMemberResponse>> getMembersOfClassroom(@PathVariable int classId) {
        return ResponseEntity.ok(classroomServiceImpl.getClassMembers(classId));

    }

    @GetMapping("/learned/user-id={userId}")
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

    @GetMapping("/quiz-belong-class/class-id={classId}")
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

    @PutMapping("/update-classroom/classroom-id={classroomId}")
    public ResponseEntity<ClassroomDTO> updateClassroom(
            @PathVariable int classroomId,
            @Valid
            @RequestBody ClassroomRequest classroomRequest) {
        return ResponseEntity.ok(classroomServiceImpl.updateClassroom(classroomId, classroomRequest));
    }

    @GetMapping("/class-id={classId}")
    public ResponseEntity<ClassroomResponse> getClassroomById(@PathVariable int classId) {
        Classroom classroom = classroomServiceImpl.findClassroomById(classId);
        if (classroom == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(classroomServiceImpl.getClassroomDetails(classroom));
    }

    @DeleteMapping("/delete-classroom/class-id={classId}")
    public ResponseEntity<MessageResponse> deleteClassroom(@PathVariable int classId) {
        try {
            Classroom classroom = classroomServiceImpl.findClassroomById(classId);
            classroomServiceImpl.deleteClassroom(classroom);
            return ResponseEntity.ok(MessageResponse.builder()
                    .success(true)
                    .msg("Delete classroom successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error deleting classroom: " + e.getMessage())
                            .build()
            );
        }
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

    @PostMapping("/invite-members")
    public ResponseEntity<MessageResponse> inviteMembers(
            @RequestBody InviteMemberRequest inviteMembersRequest
    ) {
        try {
            classroomServiceImpl.inviteMemberToClassroom(inviteMembersRequest);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Invite members successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error inviting members: " + e.getMessage())
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

    @GetMapping("/get-classroom-questions/class-id={classroomId}")
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

    @GetMapping("get-classroom-question/question-id={questionId}")
    public ResponseEntity<ClassroomQuestionResponse> getQuestionById(@PathVariable int questionId) {
        try {
            ClassroomQuestionResponse classroomQuestionResponse = classroomServiceImpl.getQuestionById(questionId);
            if (classroomQuestionResponse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(classroomQuestionResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add-question")
    public ResponseEntity<MessageResponse> addQuestion(@RequestBody CreateClassQuestionRequest createClassQuestionRequest) {
        try {
            classroomServiceImpl.addQuestionToClassroom(createClassQuestionRequest);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Add question successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error adding question: " + e.getMessage())
                            .build());
        }

    }

    @PutMapping("/update-question")
    public ResponseEntity<MessageResponse> updateQuestion(
            @RequestBody UpdateClassQuestionRequest updateClassQuestionRequest) {
        try {
            classroomServiceImpl.updateQuestionInClassroom(updateClassQuestionRequest);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Update question successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error updating question: " + e.getMessage())
                            .build());
        }

    }

    @DeleteMapping("/delete-question/question-id={questionId}&classroom-id={classroomId}")
    public ResponseEntity<MessageResponse> deleteQuestion
            (
                    @PathVariable int questionId,
                    @PathVariable int classroomId
            ) {
        try {
            classroomServiceImpl.removeQuestionFromClassroom(classroomId, questionId);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Delete question successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error deleting question: " + e.getMessage())
                            .build());
        }
    }

    private HttpStatus getHttpStatus(MessageResponse messageResponse) {
        return messageResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    @PostMapping("/add-comment")
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

    @DeleteMapping("/delete-comment/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId) {

        try {
            classroomServiceImpl.deleteComment(commentId);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Delete comment successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error deleting comment: " + e.getMessage())
                            .build());
        }
    }

    @PutMapping("/update-comment/{commentId}")
    public ResponseEntity<MessageResponse> updateComment(@PathVariable Long commentId, @RequestBody CommonUpdateRequest commonUpdateRequest) {
        try {
            classroomServiceImpl.updateComment(commentId, commonUpdateRequest.getContent());
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Update comment successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error updating comment: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/get-comments/question-id={questionId}")
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

    @PostMapping("/add-reply")
    public ResponseEntity<MessageResponse> addReply(@RequestBody ReplyCommentRequest replyRequest) {
        try {
            ReplyComment replyComment = classroomServiceImpl.addReply(replyRequest);
            if (replyComment == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        MessageResponse.builder()
                                .success(false)
                                .msg("User or Comment not found")
                                .build());
            }
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Add reply successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageResponse.builder()
                    .success(false)
                    .msg("Error adding reply: " + e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/delete-reply/{replyId}")
    public ResponseEntity<MessageResponse> deleteReply(@PathVariable Long replyId) {
        try {
            classroomServiceImpl.deleteReply(replyId);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Delete reply successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error deleting reply: " + e.getMessage())
                            .build());
        }
    }

    @PutMapping("/update-reply/{replyId}")
    public ResponseEntity<MessageResponse> updateReply(@PathVariable Long replyId, @RequestBody CommonUpdateRequest commonUpdateRequest) {
        try {
            classroomServiceImpl.updateReply(replyId, commonUpdateRequest.getContent());
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Update reply successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error updating reply: " + e.getMessage())
                            .build());
        }
    }

    @PostMapping("/add-answer")
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

    @GetMapping("/get-answers/question-id={questionId}")
    public ResponseEntity<ClassroomAnswer> getAnswersByQuestion(@PathVariable int questionId) {

        try {
            ClassroomAnswer classroomAnswer = classroomServiceImpl.getAnswerByQuestionId(questionId);
            if (classroomAnswer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(classroomAnswer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete-answer/{answerId}")
    public ResponseEntity<MessageResponse> deleteAnswer(@PathVariable int answerId) {
        try {
            classroomServiceImpl.deleteAnswer(answerId);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Delete answer successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error deleting answer: " + e.getMessage())
                            .build());
        }
    }

    @PutMapping("/update-answer/{answerId}")
    public ResponseEntity<MessageResponse> updateAnswer(@PathVariable int answerId, @RequestBody CommonUpdateRequest commonUpdateRequest) {
        try {
            classroomServiceImpl.updateAnswer(answerId, commonUpdateRequest.getContent());
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Update answer successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error updating answer: " + e.getMessage())
                            .build());
        }
    }
}
