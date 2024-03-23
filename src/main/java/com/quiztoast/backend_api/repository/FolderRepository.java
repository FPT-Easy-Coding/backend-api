package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.folder.Folder;
import com.quiztoast.backend_api.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.naming.directory.SearchResult;
import java.util.Collection;
import java.util.List;

@Repository
public interface FolderRepository  extends JpaRepository<Folder, Long> {
    public List<Folder> findByUser(User user);

    @Query("SELECT f FROM Folder f WHERE f.folderName LIKE %:keywords%")
    List<Folder> searchByName(String keywords);
}
