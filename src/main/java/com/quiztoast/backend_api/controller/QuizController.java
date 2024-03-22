package com.quiztoast.backend_api.controller;

import com.quiztoast.backend_api.exception.FormatException;
import com.quiztoast.backend_api.model.dto.QuizDTO;
import com.quiztoast.backend_api.model.entity.quiz.DoQuiz;
import com.quiztoast.backend_api.model.entity.quiz.Quiz;
import com.quiztoast.backend_api.model.entity.user.User;
import com.quiztoast.backend_api.model.mapper.QuizMapper;
import com.quiztoast.backend_api.model.payload.request.CreateQuizRequest;
import com.quiztoast.backend_api.model.payload.request.QuizRequest;
import com.quiztoast.backend_api.model.payload.request.RateQuizRequest;
import com.quiztoast.backend_api.model.payload.response.MessageResponse;
import com.quiztoast.backend_api.model.payload.response.QuizSetResponse;
import com.quiztoast.backend_api.model.payload.response.RateQuizResponse;
import com.quiztoast.backend_api.repository.QuizQuestionMappingRepository;
import com.quiztoast.backend_api.service.quiz.QuizServiceImpl;
import com.quiztoast.backend_api.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/quiz")
@RequiredArgsConstructor
@Tag(name = "Quiz")
public class QuizController {

    private final QuizServiceImpl quizServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final QuizQuestionMappingRepository quizQuestionMappingRepository;

    /**
     * Get All Quiz set using a Get request.
     *
     * @return All of quiz.
     */
    @Operation(
            description = "Get All Quiz",
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
                                                                      "userId": 11052,
                                                                      "quizId": 3,
                                                                      "userName": null,
                                                                      "userFirstName": "Admin11",
                                                                      "userLastName": "Demo12",
                                                                      "categoryId": 1,
                                                                      "quizName": "string",
                                                                      "rate": 0.0,
                                                                      "numberOfQuestions": 2,
                                                                      "createAt": "2024-02-14T00:24:25.899801",
                                                                      "view": 3,
                                                                      "timeRecentViewQuiz": "2024-02-14T01:00:28.664278"
                                                                  },
                                                                  {
                                                                      "userId": 11102,
                                                                      "quizId": 4,
                                                                      "userName": "HieuLee",
                                                                      "userFirstName": "hieu",
                                                                      "userLastName": "le",
                                                                      "categoryId": 1,
                                                                      "quizName": "string",
                                                                      "rate": 0.0,
                                                                      "numberOfQuestions": 2,
                                                                      "createAt": "2024-02-14T00:30:47.862129",
                                                                      "view": 3,
                                                                      "timeRecentViewQuiz": "2024-02-14T00:54:59.838241"
                                                                  },
                                                                  {
                                                                      "userId": 11102,
                                                                      "quizId": 5,
                                                                      "userName": "HieuLee",
                                                                      "userFirstName": "hieu",
                                                                      "userLastName": "le",
                                                                      "categoryId": 1,
                                                                      "quizName": "test2",
                                                                      "rate": 0.0,
                                                                      "numberOfQuestions": 2,
                                                                      "createAt": "2024-02-14T00:31:01.345869",
                                                                      "view": 4,
                                                                      "timeRecentViewQuiz": "2024-02-14T00:52:01.176093"
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
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema
                            )
                    ),
            }
    )
    @GetMapping("/get-all-quiz")
    public List<QuizDTO> getAllQuiz() {
        return quizServiceImpl.getAllQuiz();
    }

    /**
     * create Quiz set using a Post request.
     *
     * @return quiz set.
     */
    @Operation(
            description = "Create Quiz",
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
                                                                "userId": 1,
                                                                "quizId": 1,
                                                                "userFirstName": "Admin11",
                                                                "userLastName": "Demo12",
                                                                "categoryId": 2,
                                                                "quizName": "hihi",
                                                                "rate": 0.0,
                                                                "numberOfQuestions": 1,
                                                                "createAt": "2024-02-08T01:36:03.7677883"
                                                            }"""
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(

                            responseCode = "400",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "categoryId",
                                                                        "errorMessage": "categoryId cannot be null"
                                                                    },
                                                                    {
                                                                        "fieldName": "quizName",
                                                                        "errorMessage": "quizName cannot be null"
                                                                    },
                                                                    {
                                                                        "fieldName": "userId",
                                                                        "errorMessage": "userId cannot be null"
                                                                    },
                                                                    {
                                                                        "fieldName": "quizName",
                                                                        "errorMessage": "quizName cannot be blank"
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
                            responseCode = "403",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "userId",
                                                                        "errorMessage": "userId not found"
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
    public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody QuizRequest quizRequest) {
        return quizServiceImpl.createQuiz(quizRequest);
    }

    @PostMapping("/create-quiz-set")
    public ResponseEntity<MessageResponse> createQuizSet(
            @Valid
            @RequestBody CreateQuizRequest quizRequest) {
        try {
            Quiz quiz = quizServiceImpl.createQuizSet(quizRequest);
            if (quiz == null) {
                return ResponseEntity.ok(
                        MessageResponse.builder()
                                .success(false)
                                .msg("Quiz created successfully").build());

            } else {
                return ResponseEntity.ok(
                        MessageResponse.builder()
                                .success(true)
                                .msg("Quiz created successfully").build());
            }
        } catch (Exception e) {
            return ResponseEntity.ok(
                    MessageResponse.builder()
                            .success(false)
                            .msg(e.getMessage()).build());
        }
    }



/**
 * delete Quiz set using a delete request.
 *
 * @return delete successfully.
 */
@Operation(
        description = "Delete Quiz",
        responses = {
                @ApiResponse(
                        description = "Delete successfully",
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
                        responseCode = "400",
                        content = @Content
                ),
                @ApiResponse(
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

@DeleteMapping("/delete-quiz")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public ResponseEntity<String> deleteQuiz(
        @RequestParam(name = "id") int quizId
        ,  @RequestParam("user-id") long userId
) {
    return quizServiceImpl.deleteQuizById(quizId,userId);
}

/**
 * Update Quiz set using a Put request.
 *
 * @return quiz set.
 */
@Operation(
        description = "Update Quiz by QuizId",
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
                                                            "userId": 1,
                                                            "quizId": 1,
                                                            "userFirstName": "Admin11",
                                                            "userLastName": "Demo12",
                                                            "categoryId": 1,
                                                            "quizName": "string",
                                                            "rate": 0.0,
                                                            "numberOfQuestions": 1,
                                                            "createAt": "2024-02-07T18:42:38.645"
                                                        }"""
                                        )
                                }
                        )
                ),
                @ApiResponse(

                        responseCode = "400",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
                                ,
                                examples = {
                                        @ExampleObject(
                                                value = """
                                                        {
                                                            "data": [
                                                                {
                                                                    "fieldName": "categoryId",
                                                                    "errorMessage": "categoryId cannot be null"
                                                                },
                                                                {
                                                                    "fieldName": "userId",
                                                                    "errorMessage": "userId cannot be null"
                                                                },
                                                                {
                                                                    "fieldName": "quizName",
                                                                    "errorMessage": "quizName cannot be blank"
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
                        responseCode = "403",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "404",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = FormatException.class)
                                ,
                                examples = {
                                        @ExampleObject(
                                                value = """
                                                        {
                                                            "data": [
                                                                {
                                                                    "fieldName": "userId",
                                                                    "errorMessage": "userId not found"
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
@PutMapping(value = "update-quiz")
public ResponseEntity<QuizDTO> updateQuiz(
        @RequestParam(name = "quizId") int quizId,
        @Valid
        @RequestBody QuizRequest quizRequest) {
    return quizServiceImpl.updateQuiz(quizId, quizRequest);
}

/**
 * get Quiz and answer by name  QuizId a Get request.
 *
 * @return QuizQuestionResponse .
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
                        description = "Bad Request",

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
@GetMapping(value = "get-quiz")
public ResponseEntity<?> getQuizQuestionByQuizId(@RequestParam(name = "id") int quizId) {
    return quizServiceImpl.getQuizQuestionsAndAnswersByQuizId(quizId);
}

/**
 * get Quiz by name  using a Get request.
 *
 * @return Quiz .
 */
@Operation(
        description = "Get Quiz by QuizName",
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
                                                              "userId": 11052,
                                                              "quizId": 3,
                                                              "userName": null,
                                                              "userFirstName": "Admin11",
                                                              "userLastName": "Demo12",
                                                              "categoryId": 1,
                                                              "quizName": "string",
                                                              "rate": 0,
                                                              "numberOfQuestions": 2,
                                                              "createAt": "2024-02-14T00:24:25.899801",
                                                              "view": 3,
                                                              "timeRecentViewQuiz": "2024-02-14T01:00:28.664278"
                                                            },
                                                            {
                                                              "userId": 11102,
                                                              "quizId": 4,
                                                              "userName": "HieuLee",
                                                              "userFirstName": "hieu",
                                                              "userLastName": "le",
                                                              "categoryId": 1,
                                                              "quizName": "string",
                                                              "rate": 0,
                                                              "numberOfQuestions": 2,
                                                              "createAt": "2024-02-14T00:30:47.862129",
                                                              "view": 3,
                                                              "timeRecentViewQuiz": "2024-02-14T00:54:59.838241"
                                                            },
                                                            {
                                                              "userId": 11102,
                                                              "quizId": 5,
                                                              "userName": "HieuLee",
                                                              "userFirstName": "hieu",
                                                              "userLastName": "le",
                                                              "categoryId": 1,
                                                              "quizName": "test2",
                                                              "rate": 0,
                                                              "numberOfQuestions": 2,
                                                              "createAt": "2024-02-14T00:31:01.345869",
                                                              "view": 4,
                                                              "timeRecentViewQuiz": "2024-02-14T00:52:01.176093"
                                                            }
                                                          ]"""
                                        )
                                }
                        )
                ),
                @ApiResponse(
                        description = "Bad Request",

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
                                                                    "fieldName": "quiz_name",
                                                                    "errorMessage": "Quiz not exist"
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

@GetMapping(value = "get-quiz-by-name")
public List<QuizDTO> getQuizzesByContent(@RequestParam(name = "name") String quizName) {
    return quizServiceImpl.getQuizByContent(quizName);
}

/**
 * Increase view of quiz by quizId using a PUT request.
 *
 * @return message successfully .
 */
@Operation(
        description = "Get Quiz by QuizName",
        responses = {
                @ApiResponse(
                        description = "Success. Returns Quiz By Name QuizQuestion .",
                        responseCode = "200",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                ,
                                examples = {
                                        @ExampleObject(
                                                value = """
                                                        increase successfully!"""
                                        )
                                }
                        )
                ),
                @ApiResponse(
                        description = "Bad Request",

                        responseCode = "400",
                        content = @Content
                ),
                @ApiResponse(
                        description = "\t\n" +
                                "Unauthorized or Invalid Token. Access denied.",
                        responseCode = "403",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "404",

                        content = @Content

                )

        }
)
@PutMapping(value = "increase-view")
public ResponseEntity<?> increaseView(@RequestParam(name = "quiz-id") int quizId) {
    return quizServiceImpl.increaseView(quizId);
}

/**
 * Update TimeRecent  of quiz by quizId using a PUT request.
 *
 * @return TimeRecent .
 */
@Operation(
        description = "Get Quiz by QuizName",
        responses = {
                @ApiResponse(
                        description = "Success. Returns Quiz By Name QuizQuestion .",
                        responseCode = "200",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                ,
                                examples = {
                                        @ExampleObject(
                                                value = """
                                                        "2024-02-14T01:00:28.6642776"""
                                        )
                                }
                        )
                ),
                @ApiResponse(
                        description = "Bad Request",

                        responseCode = "400",
                        content = @Content
                ),
                @ApiResponse(
                        description = "\t\n" +
                                "Unauthorized or Invalid Token. Access denied.",
                        responseCode = "403",
                        content = @Content
                ),
                @ApiResponse(
                        responseCode = "404",

                        content = @Content

                )

        }
)
@PutMapping(value = "update-time-quiz")
public ResponseEntity<?> updateTimeQuiz(@RequestParam(name = "id") int quizId) {
    return quizServiceImpl.upDateTimeQuiz(quizId);
}

/**
 * get quiz by categoryId a Get request.
 *
 * @return List quiz .
 */

@GetMapping("/get-quiz-create-by-user/user-id={userId}")
public ResponseEntity<?> getQuizCreateByUser(@PathVariable Long userId) {
    return quizServiceImpl.getQuizCreatedByUser(userId);
}


@GetMapping(value = "learned/user-id={userId}")
public ResponseEntity<List<QuizSetResponse>> getLearnedQuizzesByUser(@PathVariable Long userId) {
    // Assume you have a method to fetch the user by ID from the database
    User user = userServiceImpl.getUserById(userId);

    if (user == null) {
        return ResponseEntity.notFound().build();
    }

    List<DoQuiz> learnedQuizzes = quizServiceImpl.getLearnedQuizzesByUser(user);

    if (learnedQuizzes == null || learnedQuizzes.isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    List<QuizSetResponse> quizSets = new ArrayList<>();
    for (DoQuiz doQuiz : learnedQuizzes) {
        Quiz quiz = doQuiz.getId().getQuiz();
        int numberOfQuestions = quizServiceImpl.getNumberOfQuestionsByQuizId(quiz.getQuizId());
        QuizSetResponse response = QuizMapper.mapQuizToQuizSetResponse(quiz, numberOfQuestions);
        quizSets.add(response);
    }

    return ResponseEntity.ok(quizSets);
}

@GetMapping(value = "get-quiz-by-category")
public ResponseEntity<?> getQuizzesByCategory(@RequestParam(name = "id") int categoryId) {
    return quizServiceImpl.getQuizByCategory(categoryId);
}

@GetMapping(value = "get-average-rate-quiz")
public Float getRateQuiz(@RequestParam(name = "id") int quizId) {
    return quizServiceImpl.getRateByQuiz(quizId);
}

@PostMapping("/create-rating")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public ResponseEntity<RateQuizResponse> createRatingQuiz(@RequestBody RateQuizRequest request
) {
    return quizServiceImpl.createRateQuiz(request.getQuizId(), request.getUserId(), request.getRate());
}

@PutMapping("/update-rating")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public ResponseEntity<RateQuizResponse> updateRatingQuiz
        (
                @RequestBody RateQuizRequest request
        ) {
    return quizServiceImpl.UpdateRateQuiz(request.getQuizId(), request.getUserId(), request.getRate());
}

@GetMapping("/get-rating")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public ResponseEntity<?> getUserRateQuiz(
        @RequestParam("user-id") long userId,
        @RequestParam("quiz-id") int quizId) {
    return quizServiceImpl.getUserRateQuiz(userId, quizId);
}

}