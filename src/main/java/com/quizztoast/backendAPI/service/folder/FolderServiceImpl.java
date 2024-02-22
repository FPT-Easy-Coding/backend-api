package com.quizztoast.backendAPI.service.folder;

import com.quizztoast.backendAPI.model.entity.folder.Folder;
import com.quizztoast.backendAPI.model.entity.folder.QuizBelongFolder;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.mapper.FolderMapper;
import com.quizztoast.backendAPI.model.payload.response.FolderResponse;
import com.quizztoast.backendAPI.repository.FolderRepository;
import com.quizztoast.backendAPI.repository.QuizBelongFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final QuizBelongFolderRepository quizBelongFolderRepository;
    @Override
    public List<Folder> getAllFolders() {
        return null;
    }

    @Override
    public Folder createFolder(Folder folder) {
        return null;
    }

    @Override
    public void deleteFolder(Folder folder) {

    }

    @Override
    public Folder updateFolder(Folder folder) {
        return null;
    }

    @Override
    public Folder getFolderById(Long id) {
        return null;
    }

    @Override
    public List<Folder> getFolderByUser(User user) {
        return folderRepository.findByUser(user);
    }

    @Override
    public List<QuizBelongFolder> getQuizBelongFolder(Folder folder) {
        return quizBelongFolderRepository.findByIdFolder(folder);
    }

    @Override
    public FolderResponse getFolderDetails(Folder folder) {
        Long numberOfQuizSet = quizBelongFolderRepository.countByIdFolder(folder);
        return FolderMapper.mapFolderToFolderResponse(folder, numberOfQuizSet);
    }
}
