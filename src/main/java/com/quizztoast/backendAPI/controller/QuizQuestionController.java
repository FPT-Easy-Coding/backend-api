package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.exception.FormatException;
import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.dto.QuizQuestionDTO;
import com.quizztoast.backendAPI.model.entity.quiz.QuizQuestion;
import com.quizztoast.backendAPI.model.payload.Request.QuizQuestionRequest;
import com.quizztoast.backendAPI.model.payload.Request.QuizRequest;
import com.quizztoast.backendAPI.service.impl.QuizQuestionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quizquestion")
@RequiredArgsConstructor
@Tag(name = "Quiz-Question")
public class QuizQuestionController {
  private final QuizQuestionServiceImpl quizQuestionServiceImpl;
    /**
     * Create QuizQuestion and answer  using a Post request.
     *
     * @return  QuizQuestion and answer .
     */
    @Operation(
            description = "Create QuizQuestion and answer ",
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
                                                                 "categoryId": 1,
                                                                 "questionContent": "bbbbbbbaaa",
                                                                 "answers": [
                                                                     {
                                                                         "content": "aaaaaa",
                                                                         "correct": true
                                                                     }
                                                                 ],
                                                                 "isCorrect": null
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
                                                                        "fieldName": "questionContent",
                                                                        "errorMessage": "questionContent cannot be blank"
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
                            description = "BadRequest",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "CategoryId",
                                                                        "errorMessage": "CategoryId Not exist"
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
    @PostMapping("create_quizquestion")
    public ResponseEntity<QuizQuestionDTO> createQuizQuestionAndAnswers(@Valid @RequestBody QuizQuestionRequest Quizrequest) {

        return quizQuestionServiceImpl.createQuizQuestionAndAnswers(Quizrequest);
    }
    /**
     * get all QuizQuestion and answer  using a Post request.
     *
     * @return  QuizQuestion and answer .
     */
    @Operation(
            description = "Get All QuizQuestion",
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
                                                            [
                                                                {
                                                                    "quiz_question_id": 2,
                                                                    "created_at": "2024-01-30T13:15:22.464031",
                                                                    "content": "bbbbbbbaaa",
                                                                    "category_id": {
                                                                        "category_id": 1,
                                                                        "category_name": "string"
                                                                    }
                                                                },
                                                                {
                                                                    "quiz_question_id": 3,
                                                                    "created_at": "2024-01-30T13:16:21.040623",
                                                                    "content": "bbbbbbbaaa",
                                                                    "category_id": {
                                                                        "category_id": 1,
                                                                        "category_name": "string"
                                                                    }
                                                                },
                                                                {
                                                                    "quiz_question_id": 5,
                                                                    "created_at": "2024-01-30T13:20:29.802077",
                                                                    "content": "bbbbbbbaaa",
                                                                    "category_id": {
                                                                        "category_id": 1,
                                                                        "category_name": "string"
                                                                    }
                                                                },
                                                                {
                                                                    "quiz_question_id": 6,
                                                                    "created_at": "2024-01-30T13:21:57.1021",
                                                                    "content": "bbbbbbbaaa",
                                                                    "category_id": {
                                                                        "category_id": 1,
                                                                        "category_name": "string"
                                                                    }
                                                                }
                                                            ]"""
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
    @GetMapping("/getallquizquestion")
    public List<QuizQuestion> getAllQuizQues(){
        return quizQuestionServiceImpl.getAllQuiz();
    }
    /**
     * get QuizQuestion and answer by name  using a Post request.
     *
     * @return  QuizQuestion and answer .
     */
    @Operation(
            description = "Get All QuizQuestion",
            summary = "",
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
                                                                    "quiz_question_id": 2,
                                                                    "created_at": "2024-01-30T13:15:22.464031",
                                                                    "content": "bbbbbbbaaa",
                                                                    "category_id": {
                                                                        "category_id": 1,
                                                                        "category_name": "string"
                                                                    }
                                                                },
                                                                {
                                                                    "quiz_question_id": 3,
                                                                    "created_at": "2024-01-30T13:16:21.040623",
                                                                    "content": "bbbbbbbaaa",
                                                                    "category_id": {
                                                                        "category_id": 1,
                                                                        "category_name": "string"
                                                                    }
                                                                },
                                                                {
                                                                    "quiz_question_id": 5,
                                                                    "created_at": "2024-01-30T13:20:29.802077",
                                                                    "content": "bbbbbbbaaa",
                                                                    "category_id": {
                                                                        "category_id": 1,
                                                                        "category_name": "string"
                                                                    }
                                                                },
                                                                {
                                                                    "quiz_question_id": 6,
                                                                    "created_at": "2024-01-30T13:21:57.1021",
                                                                    "content": "bbbbbbbaaa",
                                                                    "category_id": {
                                                                        "category_id": 1,
                                                                        "category_name": "string"
                                                                    }
                                                                }
                                                            ]"""
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request" ,

                            responseCode = "400",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "questionContent",
                                                                        "errorMessage": "questionContent cannot be blank"
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

                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "content",
                                                                        "errorMessage": "QuizQuestion_Content not exist"
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
    @GetMapping("getquizquestion/{content}")
    public List<QuizQuestion> findByContent(@PathVariable String content)
    {
        return quizQuestionServiceImpl.GetByContent(content);
    }
    /**
     * UPdate QuizQuestion and answer using a Post request.
     *
     * @return  QuizQuestion and answer .
     */
    @Operation(
            description = "Update QuizQuestion",
            summary = "",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns Quiz By Name QuizQuestion .",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "quiz_question_id": 3,
                                                                "created_at": "2024-01-30T13:16:21.040623",
                                                                "content": "helloEveryone",
                                                                "category_id": {
                                                                    "category_id": 1,
                                                                    "category_name": "string"
                                                                }
                                                            }"""
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

                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                  "data": [
                                                                      {
                                                                          "fieldName": "quizquestionId",
                                                                          "errorMessage": "quizquestionId not exist"
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
    @PutMapping("updatequizquestion/{quizquestion_id}")
    public ResponseEntity<QuizQuestion> UpdateQuizQuestion(@PathVariable int quizquestion_id,@Valid @RequestBody QuizQuestionRequest quizRequest)
    {
        return quizQuestionServiceImpl.UpdateQuizQuestion(quizquestion_id,quizRequest);
    }

    /**
     * UPdate QuizQuestion and answer using a Post request.
     *
     * @return  QuizQuestion and answer .
     */
    @Operation(
            description = "Delete QuizQuestion",
            summary = "",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns Quiz By Name QuizQuestion .",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            QuizQuestion deleted successfully"""
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

                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "quizquestionId",
                                                                        "errorMessage": "quizquestionId not exist"
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
    @DeleteMapping("delete/{quizquestionId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long quizquestionId)
    {
        return quizQuestionServiceImpl.deleteQuizById(quizquestionId);
    }
}
