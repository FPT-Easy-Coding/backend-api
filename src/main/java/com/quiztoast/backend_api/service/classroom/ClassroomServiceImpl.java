package com.quiztoast.backend_api.service.classroom;

import com.quiztoast.backend_api.model.dto.ClassroomDTO;
import com.quiztoast.backend_api.model.entity.classroom.Classroom;
import com.quiztoast.backend_api.model.entity.classroom.ClassroomAnswer;
import com.quiztoast.backend_api.model.entity.classroom.ClassroomQuestion;
import com.quiztoast.backend_api.model.entity.classroom.Comment;
import com.quiztoast.backend_api.model.entity.classroom.QuizBelongClassroom;
import com.quiztoast.backend_api.model.entity.classroom.ReplyComment;
import com.quiztoast.backend_api.model.entity.classroom.UserBelongClassroom;
import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.entity.user.User;
import com.quiztoast.backend_api.model.mapper.ClassroomMapper;
import com.quiztoast.backend_api.model.mapper.ClassroomQuestionMapper;
import com.quiztoast.backend_api.model.mapper.CommentMapper;
import com.quiztoast.backend_api.model.payload.request.AnswerRequest;
import com.quiztoast.backend_api.model.payload.request.ClassroomRequest;
import com.quiztoast.backend_api.model.payload.request.CommentRequest;
import com.quiztoast.backend_api.model.payload.request.CreateClassQuestionRequest;
import com.quiztoast.backend_api.model.payload.request.ReplyCommentRequest;
import com.quiztoast.backend_api.model.payload.request.UpdateClassQuestionRequest;
import com.quiztoast.backend_api.model.payload.response.ClassMemberResponse;
import com.quiztoast.backend_api.model.payload.response.ClassroomQuestionResponse;
import com.quiztoast.backend_api.model.payload.response.ClassroomResponse;
import com.quiztoast.backend_api.model.payload.response.ClassroomToProfileResponse;
import com.quiztoast.backend_api.model.payload.response.CommentResponse;
import com.quiztoast.backend_api.repository.ClassroomAnswerRepository;
import com.quiztoast.backend_api.repository.ClassroomQuestionRepository;
import com.quiztoast.backend_api.repository.ClassroomRepository;
import com.quiztoast.backend_api.repository.CommentRepository;
import com.quiztoast.backend_api.repository.QuizBelongClassroomRepository;
import com.quiztoast.backend_api.repository.QuizRepository;
import com.quiztoast.backend_api.repository.ReplyCommentRepository;
import com.quiztoast.backend_api.repository.UserBelongClassroomRepository;
import com.quiztoast.backend_api.repository.UserRepository;
import com.quiztoast.backend_api.service.notification.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final UserBelongClassroomRepository userBelongClassroomRepository;
    private final QuizBelongClassroomRepository quizBelongClassroomRepository;
    private final ClassroomQuestionRepository classroomQuestionRepository;
    private final CommentRepository commentRepository;
    private final ReplyCommentRepository replyCommentRepository;
    private final ClassroomAnswerRepository classroomAnswerRepository;
    private final NotificationServiceImpl notificationServiceImpl;

    @Override
    public List<Classroom> getAllClassroom() {
        return classroomRepository.findAll();
    }

    @Override
    public Classroom findClassroomById(int classroomId) {
        return classroomRepository.findByClassroomId(classroomId);
    }


    @Override
    public List<UserBelongClassroom> getListClassroomByUser(User user) {
        return userBelongClassroomRepository.findByIdUser(user);
    }

    @Override
    public Long countClassroomByUser(User user) {
        return userBelongClassroomRepository.countByIdUser(user);
    }

    @Override
    public Long countUserByClassroom(Classroom classroom) {
        return userBelongClassroomRepository.countByIdClassroom(classroom);
    }

    @Override
    public Long countQuizByClassroom(Classroom classroom) {
        return quizBelongClassroomRepository.countByIdClassroom(classroom);
    }

    @Override
    public List<QuizBelongClassroom> getListQuizByClassroom(Classroom classroom) {
        return quizBelongClassroomRepository.findByIdClassroom(classroom);
    }

    @Override
    public Classroom createClassroom(ClassroomRequest classroomRequest) {
        User user = userRepository.findByUserId(classroomRequest.getUserId());
        Classroom classroom = ClassroomMapper.mapClassroomRequestToClassroom(classroomRequest, user);
        UserBelongClassroom userBelongClassroom = new UserBelongClassroom();
        userBelongClassroom.setId(new UserBelongClassroom.UserBelongClassroomId(user, classroom));
        classroomRepository.save(classroom);
        return classroom;
    }

    @Override
    public ClassroomToProfileResponse getClassroomCardDetails(Classroom classroom) {
        if (classroom == null) {
            // Handle not found scenario
            return null;
        }
        // Fetch the number of students and quiz sets associated with the classroom
        Long numberOfStudents = userBelongClassroomRepository.countByIdClassroom(classroom);
        Long numberOfQuizSets = quizBelongClassroomRepository.countByIdClassroom(classroom);
        // Map the classroom and additional data to response DTO
        return ClassroomMapper.mapClassroomToClassroomCardResponse(classroom, numberOfStudents, numberOfQuizSets);
    }

    public ClassroomResponse getClassroomDetails(Classroom classroom) {
        if (classroom == null) {
            // Handle not found scenario
            return null;
        }
        // Fetch the number of students and quiz sets associated with the classroom
        Long numberOfStudents = userBelongClassroomRepository.countByIdClassroom(classroom);
        Long numberOfQuizSets = quizBelongClassroomRepository.countByIdClassroom(classroom);
        // Map the classroom and additional data to response DTO
        return ClassroomMapper.mapClassroomToClassroomResponse(classroom, numberOfStudents, numberOfQuizSets);
    }

    @Override
    public void deleteClassroom(Classroom classroom) {
        List<UserBelongClassroom> userBelongClassrooms = userBelongClassroomRepository.findByClassroomId(classroom.getClassroomId());
        if (userBelongClassrooms != null && !userBelongClassrooms.isEmpty()) {
            userBelongClassroomRepository.deleteUsersByClassroom(classroom);
        }
       List<QuizBelongClassroom> quizBelongClassrooms = quizBelongClassroomRepository.findByIdClassroom(classroom);
        if (quizBelongClassrooms != null && !quizBelongClassrooms.isEmpty()) {
            quizBelongClassroomRepository.deleteQuizSetsByClassroom(classroom);
        }
        List<ClassroomQuestion> classroomQuestions = classroomQuestionRepository.findByClassroomId(classroom.getClassroomId());
        for (ClassroomQuestion classroomQuestion : classroomQuestions) {
            removeQuestionFromClassroom(classroomQuestion.getClassroom().getClassroomId(), classroomQuestion.getClassQuestionId());
        }
        classroomRepository.deleteById(classroom.getClassroomId());
    }

    @Override
    public void deleteQuizFromClassroom(int classroomId, int quizId) {
        quizBelongClassroomRepository.deleteQuizFromClassroom(classroomId, quizId);
    }


    @Override
    public ClassroomDTO updateClassroom(int classroomId, ClassroomRequest classroomRequest) {
        Classroom classroom = classroomRepository.findByClassroomId(classroomId);
        classroom.setClassroomName(classroomRequest.getClassroomName());
        classroomRepository.save(classroom);
        return ClassroomMapper.mapClassroomToClassroomDto(classroom);
    }

    @Override
    public ClassroomQuestion addQuestionToClassroom(CreateClassQuestionRequest classroomQuestionRequest) {
        int classroomId = classroomQuestionRequest.getClassroomId();
        long userId = classroomQuestionRequest.getUserId();
        String title = classroomQuestionRequest.getTitle();
        String content = classroomQuestionRequest.getContent();

        Classroom classroom = classroomRepository.findByClassroomId(classroomId);
        User user = userRepository.findByUserId(userId);

        return classroomQuestionRepository.save(
                ClassroomQuestion.builder()
                        .classroom(classroom)
                        .user(user)
                        .title(title)
                        .content(content)
                        .createAt(LocalDateTime.now())
                        .isAnswered(false)
                        .build());
    }

    @Override
    public void removeQuestionFromClassroom(int classroomId, int questionId) {
        List<Comment> commentList = commentRepository.findAllCommentsByQuestion(questionId);
        if (commentList != null && !commentList.isEmpty()) {
            for (Comment comment : commentList) {
                deleteComment(comment.getCommentId());
            }
        }
        ClassroomAnswer classroomAnswer = classroomAnswerRepository.findAnswerByQuestionId(questionId);
        if (classroomAnswer != null) {
            classroomAnswerRepository.deleteAnswer(classroomAnswer.getClassAnswerId());
        }
        classroomQuestionRepository.deleteQuestionFromClassroom(classroomId, questionId);
    }

    @Override
    public void updateQuestionInClassroom(UpdateClassQuestionRequest updateRequest) {
        ClassroomQuestion classroomQuestion = classroomQuestionRepository.findByQuestionId(updateRequest.getQuestionId());
        classroomQuestion.setTitle(updateRequest.getTitle());
        classroomQuestion.setContent(updateRequest.getContent());
        classroomQuestionRepository.save(classroomQuestion);
    }

    @Override
    public void addUserToClassroom(int classroomId, Long userId) {
        Classroom classroom = classroomRepository.findByClassroomId(classroomId);
        User user = userRepository.findByUserId(userId);
        UserBelongClassroom userBelongClassroom = new UserBelongClassroom();
        userBelongClassroom.getId().setClassroom(classroom);
        userBelongClassroom.getId().setUser(user);
        userBelongClassroomRepository.save(userBelongClassroom);
    }

    @Override
    public void removeUserFromClassroom(int classroomId, int userId) {
        userBelongClassroomRepository.deleteUserFromClassroom(classroomId, userId);
    }

    @Override
    public List<ClassMemberResponse> getClassMembers(int classroomId) {
        List<UserBelongClassroom> userBelongClassrooms = userBelongClassroomRepository.findByClassroomId(classroomId);
        List<ClassMemberResponse> classMemberResponses = new ArrayList<>();
        for (UserBelongClassroom userBelongClassroom : userBelongClassrooms) {
            classMemberResponses.add(ClassroomMapper.mapUserBelongClassroomToClassMemberResponse(userBelongClassroom));
        }
        return classMemberResponses;
    }

    @Override
    public void addQuizToClassroom(int classroomId, int quizId) {
        Classroom classroom = classroomRepository.findByClassroomId(classroomId);
        Quiz quiz = quizRepository.getQuizById(quizId);
        QuizBelongClassroom quizBelongClassroom = QuizBelongClassroom.builder()
                .id(new QuizBelongClassroom.QuizBeLongClassroomId(quiz, classroom))
                .build();
        quizBelongClassroomRepository.save(quizBelongClassroom);
    }

    @Override
    public List<ClassroomQuestionResponse> getClassroomQuestions(int classroomId) {
        List<ClassroomQuestion> classroomQuestions = classroomQuestionRepository.findByClassroomId(classroomId);
        List<ClassroomQuestionResponse> classroomQuestionResponses = new ArrayList<>();
        for (ClassroomQuestion classroomQuestion : classroomQuestions) {
            User user = classroomQuestion.getUser();
            ClassroomAnswer classroomAnswer = classroomAnswerRepository.findAnswerByQuestionId(classroomQuestion.getClassQuestionId());
            classroomQuestionResponses.add(
                    ClassroomQuestionMapper
                            .mapClassroomQuestionToClassroomQuestionResponse(classroomQuestion, user, classroomAnswer)
            );
        }
        return classroomQuestionResponses;
    }

    @Override
    public ClassroomQuestionResponse getQuestionById(int questionId) {
        ClassroomQuestion classroomQuestion = classroomQuestionRepository.findByQuestionId(questionId);
        User user = classroomQuestion.getUser();
        ClassroomAnswer classroomAnswer = classroomAnswerRepository.findAnswerByQuestionId(classroomQuestion.getClassQuestionId());
        return ClassroomQuestionMapper.mapClassroomQuestionToClassroomQuestionResponse(classroomQuestion, user, classroomAnswer);
    }

    @Override
    public List<CommentResponse> getCommentsByQuestion(int questionId) {
        List<Comment> comments = commentRepository.findAllCommentsByQuestion(questionId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for (Comment comment : comments) {
            List<ReplyComment> replyComments = replyCommentRepository.findAllByComment(comment);
            CommentResponse commentResponse = CommentMapper.mapCommentToCommentResponse(comment, replyComments);
            commentResponses.add(commentResponse);
        }

        return commentResponses;
    }


    @Override
    public Comment addComment(CommentRequest commentRequest) {
        // Retrieve the user who posted the comment
        User user = userRepository.findByUserId(commentRequest.getUserId());
        if (user == null) {
            return null;
        }

        // Retrieve the question/post associated with the comment
        ClassroomQuestion classroomQuestion = classroomQuestionRepository.findByQuestionId(commentRequest.getQuestionId());
        if (classroomQuestion == null) {
            return null;
        }

        // Save the comment
        Comment comment = commentRepository.save(
                Comment.builder()
                        .content(commentRequest.getContent())
                        .user(user)
                        .createAt(LocalDateTime.now())
                        .classroomQuestion(classroomQuestion)
                        .build()
        );

        // Retrieve the post owner
        User postOwner = classroomQuestion.getUser();

        // Retrieve users who have commented on the post
        List<User> usersWhoCommented = commentRepository.findUsersWhoCommentedOnPost(classroomQuestion.getClassQuestionId());

        // Add the post owner to the list if not already present
        if (!usersWhoCommented.contains(postOwner)) {
            usersWhoCommented.add(postOwner);
        }

        // Notify users who have commented on the post, including the post owner
        for (User recipient : usersWhoCommented) {
            notificationServiceImpl.sendNotificationToUser(recipient, "New comment added to your post");
        }

        return comment;
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId);
        List<ReplyComment> replyComments = replyCommentRepository.findAllByComment(comment);
        for (ReplyComment replyComment : replyComments) {
            replyCommentRepository.deleteReply(replyComment.getReplyCommentId());
        }
        commentRepository.deleteComment(commentId);
    }

    @Override
    public void updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findByCommentId(commentId);
        comment.setContent(content);
        commentRepository.save(comment);
    }

    @Override
    public ReplyComment addReply(ReplyCommentRequest replyRequest) {
        User user = userRepository.findByUserId(replyRequest.getUserId());
        if (user == null) {
            return null;
        }
        Comment comment = commentRepository.findByCommentId(replyRequest.getCommentId());
        if (comment == null) {
            return null;
        }
        return replyCommentRepository.save(
                ReplyComment.builder()
                        .content(replyRequest.getContent())
                        .user(user)
                        .createAt(LocalDateTime.now())
                        .comment(comment)
                        .build()
        );
    }

    @Override
    public void deleteReply(Long replyId) {
        replyCommentRepository.deleteReply(replyId);
    }

    @Override
    public void updateReply(Long replyId, String content) {
        ReplyComment replyComment = replyCommentRepository.findByReplyId(replyId);
        replyComment.setContent(content);
        replyCommentRepository.save(replyComment);
    }


    @Override
    public ClassroomAnswer addAnswer(AnswerRequest answerRequest) {
        ClassroomQuestion classroomQuestion = classroomQuestionRepository.findByQuestionId(answerRequest.getQuestionId());
        if (classroomQuestion == null) {
            return null;
        }
        User user = userRepository.findByUserId(answerRequest.getUserId());
        if (user == null) {
            return null;
        }
        classroomQuestion.setAnswered(true);
        return classroomAnswerRepository.save(
                ClassroomAnswer.builder()
                        .classroomQuestion(classroomQuestion)
                        .content(answerRequest.getContent())
                        .user(user)
                        .build()
        );
    }

    @Override
    public ClassroomAnswer getAnswerByQuestionId(int questionId) {
        return classroomAnswerRepository.findAnswerByQuestionId(questionId);
    }

    @Override
    public void deleteAnswer(int answerId) {
        classroomAnswerRepository.deleteAnswer(answerId);
    }

    @Override
    public void updateAnswer(int answerId, String content) {
        ClassroomAnswer classroomAnswer = classroomAnswerRepository.findByAnswerId(answerId);
        classroomAnswer.setContent(content);
        classroomAnswerRepository.save(classroomAnswer);
    }
}
