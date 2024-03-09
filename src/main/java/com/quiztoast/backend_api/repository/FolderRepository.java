package com.quiztoast.backend_api.repository;

import com.quiztoast.backend_api.model.entity.folder.Folder;
import com.quiztoast.backend_api.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository  extends JpaRepository<Folder, Long> {
    public List<Folder> findByUser(User user);
}
