package com.quiztoast.backend_api.service;

import com.quiztoast.backend_api.model.entity.classroom.Classroom;
import com.quiztoast.backend_api.model.entity.folder.Folder;
import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.mapper.ClassroomMapper;
import com.quiztoast.backend_api.model.mapper.FolderMapper;
import com.quiztoast.backend_api.model.mapper.QuizMapper;
import com.quiztoast.backend_api.model.payload.response.ClassroomResponse;
import com.quiztoast.backend_api.model.payload.response.FolderResponse;
import com.quiztoast.backend_api.model.payload.response.QuizSetResponse;
import com.quiztoast.backend_api.repository.*;
import com.quiztoast.backend_api.service.quiz.QuizServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final ClassroomRepository classroomRepository;
    private final QuizRepository quizRepository;
    private final FolderRepository folderRepository;
    private final UserBelongClassroomRepository userBelongClassroomRepository;
    private final QuizBelongClassroomRepository quizBelongClassroomRepository;
    private final QuizBelongFolderRepository quizBelongFolderRepository;
    private final QuizServiceImpl quizServiceImpl;

    @Override
    public SearchResultResponse search(String keywords) {
        List<Classroom> classrooms = classroomRepository.searchByName(keywords);
        List<Quiz> quizs = quizRepository.searchByName(keywords);
        List<Folder> folders = folderRepository.searchByName(keywords);
        List<ClassroomResponse> classroomResponses = new ArrayList<>();
        List<QuizSetResponse> quizSetResponses = new ArrayList<>();
        List<FolderResponse> folderResponses = new ArrayList<>();
        for (Classroom classroom : classrooms) {
            Long numberOfStudent = userBelongClassroomRepository.countByIdClassroom(classroom);
            Long numberOfQuizSet = quizBelongClassroomRepository.countByIdClassroom(classroom);
            classroomResponses.add(
                    ClassroomMapper.mapClassroomToClassroomResponse(classroom,numberOfStudent,numberOfQuizSet));
        }
        for (Quiz quiz : quizs) {
            int numberOfQuestion = quizServiceImpl.getNumberOfQuestionsByQuizId(quiz.getQuizId());
            quizSetResponses.add(QuizMapper.mapQuizToQuizSetResponse(quiz,numberOfQuestion));
        }
        for (Folder folder : folders) {
            Long numberOfQuizSets = quizBelongFolderRepository.countByIdFolder(folder);
            folderResponses.add(FolderMapper.mapFolderToFolderResponse(folder,numberOfQuizSets));
        }
        return SearchResultResponse
                .builder()
                .classrooms(classroomResponses)
                .quizzes(quizSetResponses)
                .folders(folderResponses)
                .build();
    }
}
