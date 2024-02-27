package com.quizztoast.backendAPI.service.folder;

import com.quizztoast.backendAPI.model.entity.folder.Folder;
import com.quizztoast.backendAPI.model.entity.folder.QuizBelongFolder;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.payload.request.FolderRequest;
import com.quizztoast.backendAPI.model.payload.response.FolderResponse;

import java.util.List;

public interface FolderService {
    public List<Folder> getAllFolders();

    public Folder createFolder(FolderRequest folderRequest);

    public void deleteFolder(Folder folder);

    public Folder updateFolder(Folder folder);

    public Folder getFolderById(Long id);
    public List<Folder> getFolderByUser(User user);

    public List<QuizBelongFolder> getQuizBelongFolder(Folder folder);
   public FolderResponse getFolderDetails(Folder folder);

    public void removeQuizFromFolder(Long folderId, Long quizId);
    public QuizBelongFolder addQuizToFolder(Long folderId, Long quizId);
    public QuizBelongFolder getQuizBelongFolderById(Long folderId, Long quizId);
}
