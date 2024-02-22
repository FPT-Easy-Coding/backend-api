package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.model.entity.folder.Folder;
import com.quizztoast.backendAPI.model.entity.user.User;

import com.quizztoast.backendAPI.model.payload.response.FolderResponse;
import com.quizztoast.backendAPI.service.folder.FolderServiceImpl;
import com.quizztoast.backendAPI.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/folder")
@RequiredArgsConstructor
@Tag(name = "Folder")
public class FolderController {
    private final UserServiceImpl userServiceImpl;
    private final FolderServiceImpl folderServiceImpl;
    @GetMapping("/create/{userId}")
    @RequestMapping(value = "create/user-id={userId}", method = RequestMethod.GET)
    public ResponseEntity<List<FolderResponse>> getFolderByUser(@PathVariable Long userId) {
        // Assume you have a method to fetch the user by ID from the database
        User user = userServiceImpl.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Folder>  folders = folderServiceImpl.getFolderByUser(user);
        if (folders == null || folders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<FolderResponse> folderResponses = new ArrayList<>();
        for (Folder folder : folders) {
            FolderResponse response = folderServiceImpl.getFolderDetails(folder);
            folderResponses.add(response);
        }

        return ResponseEntity.ok(folderResponses);
    }
}
