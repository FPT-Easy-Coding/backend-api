package com.quizztoast.backendAPI.model.mapper;

import com.quizztoast.backendAPI.model.entity.folder.Folder;

import com.quizztoast.backendAPI.model.payload.response.FolderResponse;

public class FolderMapper {
    public static FolderResponse mapFolderToFolderResponse(Folder folder, Long numberOfQuizSets) {
        String folderName = folder.getFolderName();
        return FolderResponse.builder()
                .folderName(folderName)
                .numberOfQuizSet(numberOfQuizSets)
                .build();
    }
}
