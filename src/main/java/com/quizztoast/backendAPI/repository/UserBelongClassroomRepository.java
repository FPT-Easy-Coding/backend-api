package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.classroom.Classroom;
import com.quizztoast.backendAPI.model.entity.classroom.UserBelongClassroom;
import com.quizztoast.backendAPI.model.entity.classroom.UserBelongClassroom.UserBelongClassroomId;
import com.quizztoast.backendAPI.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBelongClassroomRepository extends JpaRepository<UserBelongClassroom, UserBelongClassroomId> {

    Long countByIdClassroom(Classroom classroom);
    Long countByIdUser(User user);

    List<UserBelongClassroom> findByIdUser(User user);
    List<UserBelongClassroom> findByIdClassroom(Classroom classroom);
}
