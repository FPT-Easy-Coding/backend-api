package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.model.dto.QuizCreationRequestDTO;
import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.quiz.QuizAnswer;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.service.QuizService;
import com.quizztoast.backendAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {

  private final QuizService quizService;
    private final UserService userService;
    @Autowired
    private UserRepository userRepository;
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
    public List<Quiz> getAllQuizs(){
        return quizService.getAllQuiz();
    }
/**
 * creaete Quiz set using a Post request.
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
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                ,
                                examples = {
                                        @ExampleObject(
                                                value = """
                                                        {
                                                            "data": [
                                                                {
                                                                    "fieldName": "quiz_id",
                                                                    "errorMessage": "quiz_id cannot be null"
                                                                },
                                                                {
                                                                    "fieldName": "quiz_name",
                                                                    "errorMessage": "quiz_name cannot be null"
                                                                },
                                                                {
                                                                    "fieldName": "category_id",
                                                                    "errorMessage": "category_id cannot be null"
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
                        description = "",
                        responseCode = "404",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema
                        )
                ),
        }
)
@PostMapping("/create-quiz")
    public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody QuizDTO quizdto)
{
    return quizService.createQuiz(quizdto);
}
    /**
     *  Delete quizquestion and quizanswer by Id using a DELETE request.
     *
     * @return A message indicating the success of the DELETE operation.
     */
    @Operation(
            description = "",
            summary = "Delete Quizquestion and quizAnswer",
            responses = {
                    @ApiResponse(
                            description = "Success. Delete succesfull.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizAnswerDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = "Delete Succesfull"
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "400",
                            content = @Content),
                    @ApiResponse(
                            description = "quizquestionId Not Found",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizAnswerDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "quizquestionId",
                                                                        "errorMessage": "Quiz question with ID 12 not found"
                                                                    }
                                                                ],
                                                                "message": "Validation Failed",
                                                                "error": true
                                                            }"""
                                            )
                                    })
                    )
            }
    )
    @DeleteMapping("/delete-quizquestion/{quizquestionId}")
    public ResponseEntity<String> deleteQuizQuestionById(@PathVariable Long quizquestionId)
    {
        return userService.deleteQuizQuesById(quizquestionId);
    }
    /**
     * Create QuizQuestion and QuizAnswer using a Post request.
     *
     * @return A message indicating the success of the Post operation.
     */
    @Operation(
            description = "",
            summary = "Create QuizQuestion and QuizAnswer",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns essential admin information.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizCreationRequestDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "categoryId": 1,
                                                                "questionContent": "string",
                                                                "answers": [
                                                                    {
                                                                        "content": "string",
                                                                        "correct": true
                                                                    }
                                                                ]
                                                            }"""
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "400",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizCreationRequestDTO.class)
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
                                    })
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizCreationRequestDTO.class)
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
                                    })
                    ),
            }
    )
    @PostMapping("/create-quizquestion")
    public ResponseEntity<QuizCreationRequestDTO> createQuizQuestionAndAnswers(@Valid @RequestBody QuizCreationRequestDTO requestDTO) {
        ResponseEntity<QuizCreationRequestDTO> quizCreationResponse = userService.createQuizQuestionAndAnswers(requestDTO);

        return quizCreationResponse;
    }
    /**
     * Update Answer by Id using a put request.
     *
     * @return A message indicating the success of the put operation.
     */

    @Operation(
            description = "",
            summary = "Update QuizAnswer",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns essential admin information.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizAnswerDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "quiz_answer_id": 2,
                                                                "quizQuestion": {
                                                                    "quiz_question_id": 4,
                                                                    "created_at": "2024-01-27T13:15:53.685639",
                                                                    "content": "qqqq",
                                                                    "category_id": {
                                                                        "category_id": 1,
                                                                        "category_name": "Usernssss"
                                                                    }
                                                                },
                                                                "content": "111111",
                                                                "is_correct": true,
                                                                "created_at": "2024-01-27T13:15:53.719909"
                                                            }"""
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "400",

                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizAnswerDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"content\",\n" +
                                                            "            \"errorMessage\": \"questionContent cannot be blank\"\n" +
                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"Validation Failed\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizAnswerDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "answerId",
                                                                        "errorMessage": "answerId Not Found"
                                                                    }
                                                                ],
                                                                "message": "Validation Failed",
                                                                "error": true
                                                            }"""
                                            )
                                    })
                    ),
            }
    )
    @PutMapping("/update-answer/{answerId}")
    public ResponseEntity<QuizAnswer> updateAnswerById(@PathVariable Long answerId, @Valid @RequestBody QuizAnswerDTO quizAnswerDTO) {
        return  userService.updateQuizAnswer(answerId,quizAnswerDTO);
    }

}
