package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.model.dto.ListResponseDTO;
import com.quizztoast.backendAPI.model.entity.folder.Folder;
import com.quizztoast.backendAPI.model.entity.folder.QuizBelongFolder;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.user.User;

import com.quizztoast.backendAPI.model.mapper.QuizMapper;
import com.quizztoast.backendAPI.model.payload.request.FolderRequest;
import com.quizztoast.backendAPI.model.payload.response.FolderResponse;
import com.quizztoast.backendAPI.model.payload.response.MessageResponse;
import com.quizztoast.backendAPI.model.payload.response.QuizSetResponse;

import com.quizztoast.backendAPI.service.folder.FolderServiceImpl;

import com.quizztoast.backendAPI.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/folder")
@RequiredArgsConstructor
@Tag(name = "Folder")
public class FolderController {
    private final UserServiceImpl userServiceImpl;
    private final FolderServiceImpl folderServiceImpl;


    @RequestMapping(value = "created/user-id={userId}", method = RequestMethod.GET)
    public ResponseEntity<ListResponseDTO> getFolderByUser(@PathVariable Long userId) {
        try {
            User user = userServiceImpl.getUserById(userId);
            List<FolderResponse> folderResponses = new ArrayList<>();
            MessageResponse messageResponse;

            if (user == null) {
                messageResponse = MessageResponse.builder()
                        .success(false)
                        .msg("User not found")
                        .build();
            } else {
                List<Folder> folders = folderServiceImpl.getFolderByUser(user);
                if (folders == null || folders.isEmpty()) {
                    messageResponse = MessageResponse.builder()
                            .success(false)
                            .msg("No folders found")
                            .build();
                } else {
                    for (Folder folder : folders) {
                        folderResponses.add(folderServiceImpl.getFolderDetails(folder));
                    }
                    messageResponse = MessageResponse.builder()
                            .success(true)
                            .msg("Success")
                            .build();
                }
            }

            return ResponseEntity.status(getHttpStatus(messageResponse)).body(
                    new ListResponseDTO(messageResponse, folderResponses)
            );
        } catch (Exception e) {
            MessageResponse messageResponse = MessageResponse.builder()
                    .success(false)
                    .msg("Internal server error")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ListResponseDTO(messageResponse, null)
            );
        }
    }

    private HttpStatus getHttpStatus(MessageResponse messageResponse) {
        return messageResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }



    @RequestMapping(value = "/quiz-belong-folder/folder-id={folderId}", method = RequestMethod.GET)
    public ResponseEntity<ListResponseDTO> getQuizBelongFolder(@PathVariable Long folderId) {
        try {
            Folder folder = folderServiceImpl.getFolderById(folderId);
            List<QuizSetResponse> quizSetResponses = new ArrayList<>();
            MessageResponse messageResponse;

            if (folder == null) {
                messageResponse = MessageResponse.builder()
                        .success(false)
                        .msg("Folder not found")
                        .build();
            } else {
                List<QuizBelongFolder> quizSetsBelongFolder = folderServiceImpl.getQuizBelongFolder(folder);
                if (quizSetsBelongFolder == null || quizSetsBelongFolder.isEmpty()) {
                    messageResponse = MessageResponse.builder()
                            .success(false)
                            .msg("No quiz sets found in the folder")
                            .build();
                } else {
                    for (QuizBelongFolder quizBelongFolder : quizSetsBelongFolder) {
                        Quiz quiz = quizBelongFolder.getId().getQuiz();
                        quizSetResponses.add(QuizMapper.mapQuizToQuizSetResponse(quiz));
                    }
                    messageResponse = MessageResponse.builder()
                            .success(true)
                            .msg("Success")
                            .build();
                }
            }

            return ResponseEntity.status(getHttpStatus(messageResponse)).body(
                    new ListResponseDTO(messageResponse, quizSetResponses)
            );
        } catch (Exception e) {
            MessageResponse messageResponse = MessageResponse.builder()
                    .success(false)
                    .msg("Internal server error")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ListResponseDTO(messageResponse, null)
            );
        }
    }

    @RequestMapping(value = "delete/folder-id={folderId}", method = RequestMethod.DELETE)
    public ResponseEntity<MessageResponse> deleteFolder(@PathVariable Long folderId) {
        try {
            Folder folder = folderServiceImpl.getFolderById(folderId);
            if (folder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        MessageResponse.builder()
                                .success(false)
                                .msg("Folder not found")
                                .build());
            }
            folderServiceImpl.deleteFolder(folder);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Folder deleted")
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error deleting folder: " + e.getMessage())
                            .build());
        }
    }

    @PostMapping("/create-folder")
    public ResponseEntity<MessageResponse> createFolder(@RequestBody FolderRequest folderRequest) {
        try {
            Folder folder = folderServiceImpl.createFolder(folderRequest);
            if (folder == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        MessageResponse.builder()
                                .success(false)
                                .msg("Error creating folder. Check if the user exits")
                                .build()
                );
            }
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Folder created")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error creating folder: " + e.getMessage())
                            .build());
        }
    }

    @RequestMapping(value = "add-quiz/{folderId}/quiz-set/{quizId}", method = RequestMethod.POST)
    public ResponseEntity<MessageResponse> addQuizToFolder(@PathVariable Long folderId, @PathVariable Long quizId) {
        try {
            QuizBelongFolder quizBelongFolder = folderServiceImpl.addQuizToFolder(folderId, quizId);
            if (quizBelongFolder == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        MessageResponse.builder()
                                .success(false)
                                .msg("Error adding quiz to folder")
                                .build()
                );
            }
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Add quiz to folder successfully")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error adding quiz to folder: " + e.getMessage())
                            .build());
        }
    }

    @RequestMapping(value = "remove-quiz/{folderId}/quiz-set/{quizId}", method = RequestMethod.DELETE)
    public ResponseEntity<MessageResponse> removeQuizFromFolder(@PathVariable Long folderId, @PathVariable Long quizId) {
        try {
            QuizBelongFolder quizBelongFolder = folderServiceImpl.getQuizBelongFolderById(folderId, quizId);
            if (quizBelongFolder == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        MessageResponse.builder()
                                .success(false)
                                .msg("Quiz not found in folder")
                                .build()
                );
            }
            folderServiceImpl.removeQuizFromFolder(folderId, quizId);
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(true)
                            .msg("Remove quiz from folder successfully")
                            .build());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    MessageResponse.builder()
                            .success(false)
                            .msg("Error removing quiz from folder: " + e.getMessage())
                            .build());
        }
    }

}
