package com.quiztoast.backend_api.service.classroom;


import com.quiztoast.backend_api.model.dto.ClassroomDTO;
import com.quiztoast.backend_api.model.entity.classroom.Classroom;
import com.quiztoast.backend_api.model.entity.classroom.ClassroomAnswer;
import com.quiztoast.backend_api.model.entity.classroom.ClassroomQuestion;
import com.quiztoast.backend_api.model.entity.classroom.Comment;
import com.quiztoast.backend_api.model.entity.classroom.QuizBelongClassroom;
import com.quiztoast.backend_api.model.entity.classroom.ReplyComment;
import com.quiztoast.backend_api.model.entity.classroom.UserBelongClassroom;
import com.quiztoast.backend_api.model.entity.user.User;
import com.quiztoast.backend_api.model.payload.request.AnswerRequest;
import com.quiztoast.backend_api.model.payload.request.ClassroomRequest;
import com.quiztoast.backend_api.model.payload.request.CommentRequest;
import com.quiztoast.backend_api.model.payload.request.CreateClassQuestionRequest;
import com.quiztoast.backend_api.model.payload.request.ReplyCommentRequest;
import com.quiztoast.backend_api.model.payload.request.UpdateClassQuestionRequest;
import com.quiztoast.backend_api.model.payload.response.ClassMemberResponse;
import com.quiztoast.backend_api.model.payload.response.ClassroomQuestionResponse;
import com.quiztoast.backend_api.model.payload.response.ClassroomToProfileResponse;
import com.quiztoast.backend_api.model.payload.response.CommentResponse;

import java.util.List;

public interface ClassroomService {
    List<Classroom> getAllClassroom();

    Classroom findClassroomById(int classroomId);

    List<UserBelongClassroom> getListClassroomByUser(User user);

    Long countClassroomByUser(User user);

    Long countUserByClassroom(Classroom classroom);

    Long countQuizByClassroom(Classroom classroom);

    List<QuizBelongClassroom> getListQuizByClassroom(Classroom classroom);

    Classroom createClassroom(ClassroomRequest classroomRequest);

    ClassroomToProfileResponse getClassroomCardDetails(Classroom classroom);

    void deleteClassroom(Classroom classroom);

    void deleteQuizFromClassroom(int classroomId, int quizId);

    ClassroomDTO updateClassroom(int classroomId, ClassroomRequest classroomRequest);

    ClassroomQuestion addQuestionToClassroom(CreateClassQuestionRequest classroomQuestionRequest);

    void removeQuestionFromClassroom(int classroomId, int questionId);

    void updateQuestionInClassroom(UpdateClassQuestionRequest updateRequest);

    void addUserToClassroom(int classroomId, Long userId);

    void removeUserFromClassroom(int classroomId, int userId);
    List<ClassMemberResponse> getClassMembers(int classroomId);

    void addQuizToClassroom(int classroomId, int quizId);

    List<ClassroomQuestionResponse> getClassroomQuestions(int classroomId);

    List<CommentResponse> getCommentsByQuestion(int questionId);

    Comment addComment(CommentRequest commentRequest);

    void deleteComment(Long commentId);

    void updateComment(Long commentId, String content);

    ReplyComment addReply(ReplyCommentRequest replyRequest);

    void deleteReply(Long replyId);

    void updateReply(Long replyId, String content);

    ClassroomAnswer addAnswer(AnswerRequest answerRequest);

    ClassroomAnswer getAnswerByQuestionId(int questionId);

    void deleteAnswer(int answerId);

    void updateAnswer(int answerId, String content);

}
