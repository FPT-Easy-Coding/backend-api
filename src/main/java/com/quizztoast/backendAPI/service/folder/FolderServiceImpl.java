package com.quizztoast.backendAPI.service.folder;

import com.quizztoast.backendAPI.model.entity.folder.Folder;
import com.quizztoast.backendAPI.model.entity.folder.QuizBelongFolder;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.mapper.FolderMapper;
import com.quizztoast.backendAPI.model.payload.request.FolderRequest;
import com.quizztoast.backendAPI.model.payload.response.FolderResponse;
import com.quizztoast.backendAPI.repository.FolderRepository;
import com.quizztoast.backendAPI.repository.QuizBelongFolderRepository;
import com.quizztoast.backendAPI.repository.QuizRepository;
import com.quizztoast.backendAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final QuizBelongFolderRepository quizBelongFolderRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    @Override
    public List<Folder> getAllFolders() {
        return null;
    }

    @Override
    public Folder createFolder(FolderRequest folderRequest) {
        User user = userRepository.findById(folderRequest.getUserId()).orElse(null);
        if (user == null) {
            return null;
        }
        Folder folder = FolderMapper.mapFolderRequestToFolder(folderRequest,user);
       return folderRepository.save(folder);
    }

    @Override
    public void deleteFolder(Folder folder) {
        folderRepository.delete(folder);
    }

    @Override
    public Folder updateFolder(Folder folder) {
        return null;
    }

    @Override
    public Folder getFolderById(Long id) {
        return folderRepository.findById(id).orElse(null);
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

    @Override
    public void removeQuizFromFolder(Long folderId, Long quizId) {
        quizBelongFolderRepository.deleteQuizBelongFolder(folderId, quizId);
    }

    @Override
    public QuizBelongFolder addQuizToFolder(Long folderId, Long quizId) {
        Folder folder = folderRepository.findById(folderId).orElse(null);
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (folder == null || quiz == null) {
            return null;
        }
        QuizBelongFolder quizBelongFolder = new QuizBelongFolder();
        quizBelongFolder.getId().setFolder(folder);
        quizBelongFolder.getId().setQuiz(quiz);
        return quizBelongFolderRepository.save(quizBelongFolder);
    }

    @Override
    public QuizBelongFolder getQuizBelongFolderById(Long folderId, Long quizId) {
        return quizBelongFolderRepository.findQuizBelongFolderById(folderId, quizId);
    }
}
