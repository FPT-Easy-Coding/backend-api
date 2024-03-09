package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository  extends JpaRepository<Classroom, Integer> {
     Classroom findByClassroomId(int classroomId);
}
