package com.quizztoast.backendAPI.service.classroom;

import com.quizztoast.backendAPI.model.entity.classroom.Classroom;
import com.quizztoast.backendAPI.model.entity.classroom.QuizBelongClassroom;
import com.quizztoast.backendAPI.model.entity.classroom.UserBelongClassroom;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.mapper.ClassroomMapper;
import com.quizztoast.backendAPI.model.payload.response.ClassroomResponse;
import com.quizztoast.backendAPI.repository.ClassroomRepository;
import com.quizztoast.backendAPI.repository.QuizBelongClassroomRepository;
import com.quizztoast.backendAPI.repository.UserBelongClassroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;
    private final UserBelongClassroomRepository userBelongClassroomRepository;
    private final QuizBelongClassroomRepository quizBelongClassroomRepository;
    @Override
    public List<Classroom> getAllClassroom() {
        return null;
    }

    @Override
    public Classroom findClassroomById(int classroomId) {
        return null;
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
    public Classroom createClassroom(Classroom classroom) {
        return null;
    }
    @Override
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
}
