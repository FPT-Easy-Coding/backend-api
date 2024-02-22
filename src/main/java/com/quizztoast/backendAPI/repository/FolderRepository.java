package com.quizztoast.backendAPI.repository;

import com.quizztoast.backendAPI.model.entity.folder.Folder;
import com.quizztoast.backendAPI.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository  extends JpaRepository<Folder, Long> {
    public List<Folder> findByUser(User user);
}
