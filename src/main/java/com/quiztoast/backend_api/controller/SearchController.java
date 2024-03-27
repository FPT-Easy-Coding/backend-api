package com.quiztoast.backend_api.controller;

import com.quiztoast.backend_api.service.SearchResultResponse;
import com.quiztoast.backend_api.service.SearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchServiceImpl searchServiceImpl;

    @GetMapping()
    public ResponseEntity<SearchResultResponse> search(
            @RequestParam("keywords") String keywords) {
        try {
            if (keywords == null || keywords.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(searchServiceImpl.search(keywords));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
