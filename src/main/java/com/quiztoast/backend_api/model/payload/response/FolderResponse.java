package com.quiztoast.backend_api.model.payload.response;

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
    Long authorId;
    String folderName;
    Long numberOfQuizSet;
    String authorName;
    Date createdAt;
}
