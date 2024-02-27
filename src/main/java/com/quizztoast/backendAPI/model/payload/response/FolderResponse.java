package com.quizztoast.backendAPI.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class FolderResponse {
    Long folderId;
    String folderName;
    Long numberOfQuizSet;
    String authorName;
    Date createdAt;
}
