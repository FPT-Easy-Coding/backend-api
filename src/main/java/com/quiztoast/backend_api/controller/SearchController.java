package com.quiztoast.backend_api.controller;

import com.quiztoast.backend_api.service.SearchRequest;
import com.quiztoast.backend_api.service.SearchResultResponse;
import com.quiztoast.backend_api.service.SearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchServiceImpl searchServiceImpl;

    @GetMapping
    public ResponseEntity<SearchResultResponse> search(@RequestBody SearchRequest keywords) {
        try {
            if (keywords.getKeywords() == null) {
                return ResponseEntity.status(400).body(null);
            }

            return ResponseEntity.ok(searchServiceImpl.search(keywords.getKeywords()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
