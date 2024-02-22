package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.model.entity.classroom.Classroom;
import com.quizztoast.backendAPI.model.entity.classroom.UserBelongClassroom;

import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.mapper.ClassroomMapper;

import com.quizztoast.backendAPI.model.payload.response.ClassroomResponse;

import com.quizztoast.backendAPI.service.classroom.ClassroomServiceImpl;
import com.quizztoast.backendAPI.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/learned/{userId}")
    @RequestMapping(value = "learned/user-id={userId}", method = RequestMethod.GET)
    public ResponseEntity<List<ClassroomResponse>> getLearnedQuizzesByUser(@PathVariable Long userId) {
        // Assume you have a method to fetch the user by ID from the database
        User user = userServiceImpl.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<UserBelongClassroom>  usersInClassroom = classroomServiceImpl.getListClassroomByUser(user);

        if (usersInClassroom == null || usersInClassroom.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ClassroomResponse> classes = new ArrayList<>();
        for (UserBelongClassroom userBelongClassroom : usersInClassroom) {
            Classroom classroom = userBelongClassroom.getId().getClassroom();
            ClassroomResponse response = classroomServiceImpl.getClassroomDetails(classroom);
            classes.add(response);
        }

        return ResponseEntity.ok(classes);
    }
}
