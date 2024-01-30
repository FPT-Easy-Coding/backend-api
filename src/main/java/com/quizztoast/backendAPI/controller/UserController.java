package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.model.dto.UserDTO;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.mapper.UserMapper;
import com.quizztoast.backendAPI.security.auth.auth_payload.ChangePasswordRequest;
import com.quizztoast.backendAPI.model.dto.QuizAnswerDTO;
import com.quizztoast.backendAPI.model.dto.QuizCreationRequestDTO;

import com.quizztoast.backendAPI.service.impl.CategoryServiceImpl;
import com.quizztoast.backendAPI.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

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
                                                    value = "{ \"message\": \"Invalid email address\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid data provided (Email)",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"message\": \"Invalid email address\" }"
                                            )
                                    }
                            )
                    ),
            }
    )
    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest,
            Principal connectedUser
    ){
        userServiceImpl.changePassword(changePasswordRequest,connectedUser);
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
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<User> users = userServiceImpl.getAllUsers();
        List<UserDTO> userDTOs = UserMapper.usersToUserDTOs(users);
        return ResponseEntity.ok(userDTOs);
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
                                                    value = """
                                                            {
                                                                "userId": 10802,
                                                                "username": "hieu111@gmail.com",
                                                                "firstName": "hieu111@gmail.com",
                                                                "lastName": "string",
                                                                "email": "hieu111@gmail.com"
                                                            }"""
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid data provided (Email)",
                            responseCode = "400",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"message\": \"Invalid email address\" }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            description = "Resource not found",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"message\": \"User not found with id: 123123\" }"
                                            )
                                    }
                            )
                    ),
            }
    )

    @GetMapping("/profile/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        User user = userServiceImpl.getUserById(id);

        // Convert User entity to UserDTO
        UserDTO userDTO = UserMapper.mapToUserDto(user);

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
                                                    value = """
                                                            {
                                                                "questionContent": "1+1=",
                                                                "answers": [
                                                                    {
                                                                        "content": "2",
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
                            content = @Content),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content
                    ),
            }
    )
    @PostMapping("/create-quizquestion")
    public ResponseEntity<QuizCreationRequestDTO> createQuizQuestionAndAnswers(@RequestBody QuizCreationRequestDTO requestDTO) {
        ResponseEntity<QuizCreationRequestDTO> QuizCreationRequestDTO = userServiceImpl.createQuizQuestionAndAnswers(requestDTO);
        return QuizCreationRequestDTO;
    }
    /**
     *  Update Answer by Id using a put request.
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
                                                    value = "QuizAnswer updated successfully"
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "400",
                            content = @Content),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizAnswerDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = "QuizAnswer with ID 123 not found"
                                            )
                                    })
                    ),
            }
    )
    @PutMapping("/update-answer/{answerId}")
    public ResponseEntity<String> updateAnswerById(@PathVariable Long answerId, @RequestBody QuizAnswerDTO quizAnswerDTO) {
        return  userServiceImpl.updateQuizAnswer(answerId,quizAnswerDTO);
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
                            description = "",
                            responseCode = "404",
                            content = @Content)
            }
    )
    @DeleteMapping("/delete-quizquestion/{quizquestionId}")
public ResponseEntity<String> deleteQuizQuestionById(@PathVariable Long quizquestionId)
    {
        return userServiceImpl.deleteQuizQuesById(quizquestionId);
    }
}
