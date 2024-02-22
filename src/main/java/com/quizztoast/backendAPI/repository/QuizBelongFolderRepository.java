package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.folder.Folder;
import com.quizztoast.backendAPI.model.entity.folder.QuizBelongFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.quizztoast.backendAPI.model.entity.folder.QuizBelongFolder.QuizBelongFolderId;

import java.util.List;

@Repository
public interface QuizBelongFolderRepository extends JpaRepository<QuizBelongFolder, QuizBelongFolderId> {
    Long countByIdFolder(Folder folder);
    List<QuizBelongFolder> findByIdFolder(Folder folder);
}
