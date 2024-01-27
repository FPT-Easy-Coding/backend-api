package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.dto.QuizCreationRequestDTO;
import com.quizztoast.backendAPI.dto.UserDTO;
import com.quizztoast.backendAPI.exception.UserNotFoundException;
import com.quizztoast.backendAPI.model.quiz.QuizAnswer;
import com.quizztoast.backendAPI.model.user.User;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth_payload.ChangePasswordRequest;
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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Autowired
    private UserRepository userRepository;

    /**
     * Change password using a Patch request.
     *
     * @return A message indicating the success of the Patch operation.
     */
    @Operation(
            description = "",
            summary = "Change Password",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns essential admin information.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid data provided (Email)",
                            responseCode = "400",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"currentPassword\",\n" +
                                                            "            \"errorMessage\": \"currentPassword : incorrect\"\n" +
                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"Validation Failed\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content
                    ),
            }
    )
    @PatchMapping()
    public ResponseEntity<?> changePassword(

            @RequestBody ChangePasswordRequest changePasswordRequest,
            Principal connectedUser
    ){
        userService.changePassword(changePasswordRequest,connectedUser);
        return ResponseEntity.ok().build();
    }
    /**
     * Get All User using a get request.
     *
     * @return A message indicating the success of the Get operation.
     */

    @Operation(
            description = "",
            summary = "get All User",

            responses = {
                    @ApiResponse(
                            description = ".",
                            responseCode = "404",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = ".",
                            responseCode = "400",
                            content = @Content
                    )
            }
    )
    @GetMapping("/fetchAll")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
   /**
     * Get profile by Id using a Post request.
     *
     * @return A message indicating the success of the Post operation.
     */
    @Operation(
            description = "",
            summary = "get profile by id",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns essential admin information.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "  \"firstname\": \"string\",\n" +
                                                            "  \"lastname\": \"string\",\n" +
                                                            "  \"email\": \"Hiiii1@gmail.com\",\n" +
                                                            "  \"password\": null,\n" +
                                                            "  \"telephone\": \"--4\",\n" +
                                                            "  \"username\": \"Hiiii1@gmail.com\",\n" +
                                                            "  \"createdAt\": null,\n" +
                                                            "  \"googleId\": null,\n" +
                                                            "  \"role\": null,\n" +
                                                            "  \"mfaEnabled\": false,\n" +
                                                            "  \"secret\": null,\n" +
                                                            "  \"banned\": false,\n" +
                                                            "  \"premium\": false\n" +
                                                            "}"
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "400",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Resource not found",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "  \"data\": [\n" +
                                                            "    {\n" +
                                                            "      \"fieldName\": \"userId\",\n" +
                                                            "      \"errorMessage\": \"User with ID 11 not found\"\n" +
                                                            "    }\n" +
                                                            "  ],\n" +
                                                            "  \"message\": \"User not found\",\n" +
                                                            "  \"error\": true\n" +
                                                            "}"
                                            )
                                    }
                            )
                    ),
            }
    )

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        if (userRepository.findByUserId(id) == null) {
            // User not found, return a 404 response with JSON format
            throw new UserNotFoundException(id);
        }
        User user = userRepository.findByUserId(id);

        // Convert User entity to UserDTO
        UserDTO userDTO = UserDTO.builder()
                .username(user.getUsername())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .build();

        return ResponseEntity.ok(userDTO);
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
                                                    value = "{\n" +
                                                            "    \"categoryId\": 1,\n" +
                                                            "    \"questionContent\": \"string\",\n" +
                                                            "    \"answers\": [\n" +
                                                            "        {\n" +
                                                            "            \"content\": \"string\",\n" +
                                                            "            \"correct\": true\n" +
                                                            "        }\n" +
                                                            "    ]\n" +
                                                            "}"
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
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"questionContent\",\n" +
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
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizCreationRequestDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"CategoryId\",\n" +
                                                            "            \"errorMessage\": \"CategoryId Not exist\"\n" +
                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"Validation Failed\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
                                            )
                                    })
                    ),
            }
    )
    @PostMapping("/create-quizquestion")
    public ResponseEntity<QuizCreationRequestDTO> createQuizQuestionAndAnswers(@Valid @RequestBody QuizCreationRequestDTO requestDTO) {
        ResponseEntity<QuizCreationRequestDTO> QuizCreationRequestDTO = userService.createQuizQuestionAndAnswers(requestDTO);
        return QuizCreationRequestDTO;
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
                                                    value = "{\n" +
                                                            "    \"quiz_answer_id\": 2,\n" +
                                                            "    \"quizQuestion\": {\n" +
                                                            "        \"quiz_question_id\": 4,\n" +
                                                            "        \"created_at\": \"2024-01-27T13:15:53.685639\",\n" +
                                                            "        \"content\": \"qqqq\",\n" +
                                                            "        \"category_id\": {\n" +
                                                            "            \"category_id\": 1,\n" +
                                                            "            \"category_name\": \"Usernssss\"\n" +
                                                            "        }\n" +
                                                            "    },\n" +
                                                            "    \"content\": \"111111\",\n" +
                                                            "    \"is_correct\": true,\n" +
                                                            "    \"created_at\": \"2024-01-27T13:15:53.719909\"\n" +
                                                            "}"
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
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"answerId\",\n" +
                                                            "            \"errorMessage\": \"answerId Not Found\"\n" +
                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"Validation Failed\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
                                            )
                                    })
                    ),
            }
    )
    @PutMapping("/update-answer/{answerId}")
    public ResponseEntity<QuizAnswer> updateAnswerById(@PathVariable Long answerId, @Valid @RequestBody QuizAnswerDTO quizAnswerDTO) {
        return  userService.updateQuizAnswer(answerId,quizAnswerDTO);
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
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"quizquestionId\",\n" +
                                                            "            \"errorMessage\": \"Quiz question with ID 12 not found\"\n" +
                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"Validation Failed\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
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
}
