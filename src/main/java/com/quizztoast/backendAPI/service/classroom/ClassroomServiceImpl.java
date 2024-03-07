package com.quizztoast.backendAPI.service.classroom;

import com.quizztoast.backendAPI.model.dto.ClassroomDTO;
import com.quizztoast.backendAPI.model.entity.classroom.*;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.mapper.ClassroomMapper;
import com.quizztoast.backendAPI.model.mapper.ClassroomQuestionMapper;
import com.quizztoast.backendAPI.model.mapper.CommentMapper;
import com.quizztoast.backendAPI.model.payload.request.ClassroomRequest;
import com.quizztoast.backendAPI.model.payload.request.CommentRequest;
import com.quizztoast.backendAPI.model.payload.response.*;
import com.quizztoast.backendAPI.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<Classroom> getAllClassroom() {
        return null;
    }

    @Override
    public Classroom findClassroomById(int classroomId) {
        return classroomRepository.findByClassroomId(classroomId);
    }

    @Override
    public List<Classroom> getListClassroomByUserId(int userId) {
        return null;
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
        return classroomRepository.save(classroom);
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
        userBelongClassroomRepository.deleteUsersByClassroom(classroom);
        quizBelongClassroomRepository.deleteQuizSetsByClassroom(classroom);
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
    public void addQuestionToClassroom(int classroomId, int questionId) {

    }

    @Override
    public void removeQuestionFromClassroom(int classroomId, int questionId) {

    }

    @Override
    public void updateQuestionInClassroom(int classroomId, int questionId, Classroom classroom) {

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
    public void updateClassroomUser(int classroomId, int userId, Classroom classroom) {

    }

    @Override
    public List<Classroom> findClassroomByTeacherId(int teacherId) {
        return null;
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
            System.out.println("classroomQuestion: " + classroomQuestion.getContent());
            classroomQuestionResponses.add(
                    ClassroomQuestionMapper
                            .mapClassroomQuestionToClassroomQuestionResponse(classroomQuestion, user, classroomAnswer)
            );
        }
        return classroomQuestionResponses;
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
        User user = userRepository.findByUserId(commentRequest.getUserId());
        if (user == null) {
            return null;
        }
        ClassroomQuestion classroomQuestion = classroomQuestionRepository.findByQuestionId(commentRequest.getQuestionId());
        if (classroomQuestion == null) {
            return null;
        }
        return commentRepository.save(
                Comment.builder()
                        .content(commentRequest.getContent())
                        .user(user)
                        .classroomQuestion(classroomQuestion)
                        .build()
        );
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
}
