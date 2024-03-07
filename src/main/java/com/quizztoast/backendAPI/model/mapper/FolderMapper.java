package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.entity.folder.Folder;

import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.payload.request.FolderRequest;
import com.quizztoast.backendAPI.model.payload.response.FolderResponse;

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
