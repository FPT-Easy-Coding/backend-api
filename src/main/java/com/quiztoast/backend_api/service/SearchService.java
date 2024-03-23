package com.quiztoast.backend_api.service;

import javax.naming.directory.SearchResult;
import java.util.List;

public interface SearchService {
    SearchResultResponse search(String keywords);
}
