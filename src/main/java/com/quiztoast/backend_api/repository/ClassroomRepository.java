package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.naming.directory.SearchResult;
import java.util.Collection;
import java.util.List;

@Repository
public interface ClassroomRepository  extends JpaRepository<Classroom, Integer> {
     Classroom findByClassroomId(int classroomId);

    @Query("SELECT c FROM Classroom c WHERE c.classroomName LIKE %:keywords%")
    List<Classroom> searchByName(String keywords);
}
