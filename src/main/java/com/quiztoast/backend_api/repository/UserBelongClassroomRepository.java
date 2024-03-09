package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.classroom.Classroom;
import com.quiztoast.backend_api.model.entity.classroom.UserBelongClassroom;
import com.quiztoast.backend_api.model.entity.classroom.UserBelongClassroom.UserBelongClassroomId;
import com.quiztoast.backend_api.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBelongClassroomRepository extends JpaRepository<UserBelongClassroom, UserBelongClassroomId> {

    Long countByIdClassroom(Classroom classroom);
    Long countByIdUser(User user);

    List<UserBelongClassroom> findByIdUser(User user);
    @Query("SELECT u FROM UserBelongClassroom u WHERE u.id.classroom.classroomId = :classroomId")
    List<UserBelongClassroom> findByClassroomId(int classroomId);
    @Modifying
    @Query("DELETE FROM UserBelongClassroom u WHERE u.id.classroom = :classroom")
    void deleteUsersByClassroom(@Param("classroom") Classroom classroom);

    @Modifying
    @Query("DELETE FROM UserBelongClassroom uc WHERE uc.id.classroom.classroomId = :classroomId AND uc.id.user.userId = :userId")
    void deleteUserFromClassroom(int classroomId, int userId);

    @Query("SELECT u FROM UserBelongClassroom u WHERE u.id.classroom.classroomId = :classroomId AND u.id.user.userId = :userId")
    UserBelongClassroom findByIdClassroomAndUser(int classroomId, int userId);

    @Modifying
    @Query("DELETE FROM UserBelongClassroom u WHERE u.id.user.userId = :userId")
    void deleteByUserId(Long userId);
}
