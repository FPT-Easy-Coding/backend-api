package com.quiztoast.backend_api.controller;

import com.quiztoast.backend_api.model.dto.QuizDTO;
import com.quiztoast.backend_api.service.category.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {
    private final CategoryServiceImpl categoryServiceimp;
    /**
     * get category by categoryId a Get request.
     *
     * @return  Category .
     */
    @Operation(
            description = "Get QuizQuestion And QuizAnswer by QuizId",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns Quiz By Name QuizQuestion .",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            [
                                                                {
                                                                    "questionContent": "cau 1",
                                                                    "answers": [
                                                                        {
                                                                            "content": "hello",
                                                                            "isCorrect": true
                                                                        },
                                                                        {
                                                                            "content": "xin chao",
                                                                            "isCorrect": false
                                                                        },
                                                                        {
                                                                            "content": "xin chao",
                                                                            "isCorrect": false
                                                                        },
                                                                        {
                                                                            "content": "xin chao",
                                                                            "isCorrect": false
                                                                        }
                                                                    ]
                                                                },
                                                                {
                                                                    "questionContent": "cau 2",
                                                                    "answers": [
                                                                        {
                                                                            "content": "1",
                                                                            "isCorrect": true
                                                                        },
                                                                        {
                                                                            "content": "2",
                                                                            "isCorrect": false
                                                                        },
                                                                        {
                                                                            "content": "3",
                                                                            "isCorrect": false
                                                                        },
                                                                        {
                                                                            "content": "4",
                                                                            "isCorrect": false
                                                                        }
                                                                    ]
                                                                }
                                                            ]"""
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request" ,

                            responseCode = "400",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "\t\n" +
                                    "Unauthorized or Invalid Token. Access denied.",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",

                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                  "data": [
                                                                      {
                                                                          "fieldName": "quizId",
                                                                          "errorMessage": "Quiz with given ID not found"
                                                                      }
                                                                  ],
                                                                  "message": "Validation Failed",
                                                                  "error": true
                                                              }"""
                                            )
                                    }
                            )

                    )

            }
    )
    @GetMapping("/get-category")
    public ResponseEntity<?> getQuizQestionByQuizId (@RequestParam(name = "id") int categoryId) {
        return categoryServiceimp.getCategoryBycategoryId(categoryId);
    }
}
