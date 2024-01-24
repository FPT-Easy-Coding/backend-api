package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.quiz.Quiz;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {

  private final QuizService quizService;

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
                                                value = "{\n" +
                                                        "    \"quiz_id\": 1,\n" +
                                                        "    \"user_id\": 10952,\n" +
                                                        "    \"class_id\": 1,\n" +
                                                        "    \"category_id\": 1,\n" +
                                                        "    \"quiz_name\": \"ABC\",\n" +
                                                        "    \"rate\": 3,\n" +
                                                        "    \"create_at\": \"2024-01-23T09:55:17.447\",\n" +
                                                        "    \"quiz_ques_ids\": [\n" +
                                                        "        0\n" +
                                                        "    ]\n" +
                                                        "}"
                                        )
                                }
                        )
                ),
                @ApiResponse(
                        description = "" ,

                        responseCode = "400",
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
@PostMapping("/create-quiz")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizdto)
{
    return quizService.createQuiz(quizdto);
}
}
