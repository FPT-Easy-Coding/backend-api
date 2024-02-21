package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.exception.FormatException;
import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.payload.request.QuizRequest;
import com.quizztoast.backendAPI.model.payload.response.QuizQuestionResponse;
import com.quizztoast.backendAPI.service.quiz.QuizServiceImpl;
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
@RequestMapping("api/v1/quiz")
@RequiredArgsConstructor
@Tag(name = "Quiz")
public class QuizController {

    private final QuizServiceImpl quizServiceImpl;

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
    @RequestMapping(value = "delete-quiz/", method = RequestMethod.DELETE)
public ResponseEntity<String> deleteQuiz(@RequestParam(name = "id") int quiz_id)
{
    return quizServiceImpl.deleteQuizById(quiz_id);
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
    @RequestMapping(value = "update-quiz", method = RequestMethod.PUT)
public ResponseEntity<QuizDTO> UpdateQuiz(@RequestParam(name = "id") int quiz_id,@Valid @RequestBody QuizRequest quizRequest)
{
    return quizServiceImpl.UpdateQuiz(quiz_id,quizRequest);
}

    /**
     * get Quiz and answer by name  QuizId a Get request.
     *
     * @return  QuizQuestionResponse .
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
    @RequestMapping(value = "get-quiz", method = RequestMethod.GET)
    public ResponseEntity<?> getQuizQestionByQuizId (@RequestParam(name = "id") int quizId) {
        return quizServiceImpl.getQuizQuestionsAndAnswersByQuizId(quizId);
    }

    /**
     * get Quiz by name  using a Get request.
     *
     * @return  Quiz .
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
    @RequestMapping(value = "get-quiz-by-name", method = RequestMethod.GET)
    public List<QuizDTO> getQuizsByContent(@RequestParam(name = "name") String QuizName) {
        return quizServiceImpl.GetQuizByContent(QuizName);
    }

    /**
     * Increase view of quiz by quizid using a PUT request.
     *
     * @return  message succesfull .
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
                                                            increase Succesfull!"""
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
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",

                            content = @Content

                    )

            }
    )
    @RequestMapping(value = "/increase-view", method = RequestMethod.PUT)
    public ResponseEntity<?> increaseView(@RequestParam(name = "quiz-id") int quizId){
       return quizServiceImpl.increaseView(quizId);
    }

    /**
     * Update TimeRecent  of quiz by quizid using a PUT request.
     *
     * @return  TimeRecent .
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
                            description = "Bad Request" ,

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
    @RequestMapping(value = "/update-time-quiz", method = RequestMethod.PUT)
    public ResponseEntity<?> updateTimeQuiz(@RequestParam(name = "id") int quizId){
        return quizServiceImpl.upDateTimeQuiz(quizId);
    }

}
