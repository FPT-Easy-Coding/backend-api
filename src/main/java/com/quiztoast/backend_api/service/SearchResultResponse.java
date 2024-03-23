package com.quiztoast.backend_api.service;

import com.quiztoast.backend_api.model.entity.classroom.Classroom;
import com.quiztoast.backend_api.model.entity.folder.Folder;
import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.payload.response.ClassroomResponse;
import com.quiztoast.backend_api.model.payload.response.FolderResponse;
import com.quiztoast.backend_api.model.payload.response.QuizSetResponse;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResultResponse {
    List <ClassroomResponse> classrooms;
    List <QuizSetResponse> quizzes;
    List <FolderResponse> folders;
}
