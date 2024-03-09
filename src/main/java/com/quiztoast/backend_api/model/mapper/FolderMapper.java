package com.quiztoast.backend_api.model.mapper;

import com.quiztoast.backend_api.model.entity.folder.Folder;

import com.quiztoast.backend_api.model.entity.user.User;
import com.quiztoast.backend_api.model.payload.request.FolderRequest;
import com.quiztoast.backend_api.model.payload.response.FolderResponse;

import java.util.Date;

public class FolderMapper {
    public static FolderResponse mapFolderToFolderResponse(Folder folder, Long numberOfQuizSets) {
        String folderName = folder.getFolderName();
        return FolderResponse.builder()
                .folderId(folder.getFolderId())
                .folderName(folderName)
                .authorName(folder.getUser().getUserName())
                .numberOfQuizSet(numberOfQuizSets)
                .createdAt(folder.getCreatedAt())
                .build();
    }

    public static Folder mapFolderRequestToFolder(FolderRequest folderRequest, User user) {
        return Folder.builder()
                .folderName(folderRequest.getFolderName())
                .createdAt(new Date())
                .user(user)
                .build();
    }
}
