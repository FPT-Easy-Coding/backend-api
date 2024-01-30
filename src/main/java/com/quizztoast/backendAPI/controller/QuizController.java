package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.exception.FormatException;
import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.payload.Request.QuizRequest;
import com.quizztoast.backendAPI.service.impl.QuizServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizServiceImpl quizServiceImpl;

    /**
     * Get All Quiz set using a Get request.
     *
     * @return All of quiz.
     */
    @Operation(
            description = "Get All Quiz",
            summary = "",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns All Quiz .",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = "[{\"quiz_id\":1,\"user_id\":null,\"class_id\":12,\"category_id\":null,\"quiz_name\":\"test Quiz\",\"rate\":5,\"created_at\":\"2024-01-22T12:30:00\",\"quiz_ques_id\":0}]"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            description = ".",
                            responseCode = "400",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema
                            )
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
                            description = "",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema
                            )
                    ),
            }
    )
    @GetMapping("/getAll-quiz")
    public List<QuizDTO> getAllQuizs(){
        return quizServiceImpl.getAllQuiz();
    }
    /**
     * create Quiz set using a Post request.
     *
     * @return quiz set.
     */
    @Operation(
            description = "Create Quiz",
            summary = "",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns All Quiz .",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                        {
                                                            "quiz_id": 1,
                                                            "user_id": 10952,
                                                            "class_id": 1,
                                                            "category_id": 1,
                                                            "quiz_name": "ABC",
                                                            "rate": 3,
                                                            "create_at": "2024-01-23T09:55:17.447",
                                                            "quiz_ques_ids": [
                                                                0
                                                            ]
                                                        }"""
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            description = "" ,

                            responseCode = "400",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                 "data": [
                                                                     {
                                                                         "fieldName": "user_id",
                                                                         "errorMessage": "user_id cannot be null"
                                                                     },
                                                                     {
                                                                         "fieldName": "category_id",
                                                                         "errorMessage": "category_id cannot be null"
                                                                     },
                                                                     {
                                                                         "fieldName": "quiz_name",
                                                                         "errorMessage": "quiz_name cannot be blank"
                                                                     }
                                                                 ],
                                                                 "message": "Validation Failed",
                                                                 "error": true
                                                             }"""
                                            )
                                    }

                            )
                    ),
                    @ApiResponse(
                            description = "" ,
                            responseCode = "403",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "User_id",
                                                                        "errorMessage": "User_id not found"
                                                                    }
                                                                ],
                                                                "message": "Validation Failed",
                                                                "error": true
                                                            }"""
                                            )
                                    }

                            )
                    ),
            }
    )
    @PostMapping("/create-quiz")
    public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody QuizRequest quizRequest)
    {
        return quizServiceImpl.createQuiz(quizRequest);
    }


    /**
     * delete Quiz set using a delete request.
     *
     * @return delete succesfull.
     */
    @Operation(
            description = "Delete Quiz",
            summary = "",
            responses = {
                    @ApiResponse(
                            description = "Delete succesfull",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                        Quiz deleted successfully"""
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            description = "" ,
                            responseCode = "400",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "" ,
                            responseCode = "403",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
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
                    ),
            }
    )
@DeleteMapping("/delete/{quiz_id}")
public ResponseEntity<String> deleteQuiz(@PathVariable int quiz_id)
{
    return quizServiceImpl.deleteQuizById(quiz_id);
}

    /**
     * Update Quiz set using a Put request.
     *
     * @return quiz set.
     */
    @Operation(
            description = "Get All Quiz",
            summary = "",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns All Quiz .",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "user_id": 202,
                                                                "category_id": 1,
                                                                "quiz_name": "111111111",
                                                                "rate": 0.0,
                                                                "create_at": "2024-01-29T15:24:46.975"
                                                            }"""
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            description = "" ,

                            responseCode = "400",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                 "data": [
                                                                     {
                                                                         "fieldName": "user_id",
                                                                         "errorMessage": "user_id cannot be null"
                                                                     },
                                                                     {
                                                                         "fieldName": "category_id",
                                                                         "errorMessage": "category_id cannot be null"
                                                                     },
                                                                     {
                                                                         "fieldName": "quiz_name",
                                                                         "errorMessage": "quiz_name cannot be blank"
                                                                     }
                                                                 ],
                                                                 "message": "Validation Failed",
                                                                 "error": true
                                                             }"""
                                            )
                                    }

                            )
                    ),
                    @ApiResponse(
                            description = "" ,
                            responseCode = "403",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                 "data": [
                                                                     {
                                                                         "fieldName": "User_id",
                                                                         "errorMessage": "User_id not found"
                                                                     }
                                                                 ],
                                                                 "message": "Validation Failed",
                                                                 "error": true
                                                             }"""
                                            )
                                    }

                            )
                    ),
            }
    )
@PutMapping("/updateQuiz/{quiz_id}")
public ResponseEntity<QuizDTO> UpdateQuiz(@PathVariable int quiz_id,@Valid @RequestBody QuizRequest quizRequest)
{
    return quizServiceImpl.UpdateQuiz(quiz_id,quizRequest);
}
}
