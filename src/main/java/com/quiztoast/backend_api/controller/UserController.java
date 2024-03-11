package com.quiztoast.backend_api.controller;

import com.quizztoast.backendAPI.exception.FormatException;
import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.dto.UserDTO;
import com.quizztoast.backendAPI.model.payload.request.UserUpdateRequest;
import com.quizztoast.backendAPI.model.payload.response.QuizSetResponse;
import com.quizztoast.backendAPI.model.payload.response.SimpleErrorResponse;
import com.quizztoast.backendAPI.model.payload.response.UserProfileResponse;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.mapper.UserMapper;
import com.quizztoast.backendAPI.model.payload.request.ChangePasswordRequest;

import com.quiztoast.backend_api.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User")
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
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
        if(connectedUser == null){
            return ResponseEntity.badRequest().build();
        }
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
    @GetMapping("/fetch-all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<User> users = userServiceImpl.getAllUsers();
        List<UserDTO> userDTOs = UserMapper.usersToUserDTOs(users);
        return ResponseEntity.ok(userDTOs);
    }


    /**
     * Get profile by id using a Post request.
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

    @GetMapping(value = "profile/user-id={id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable("id") Long id) {
        User user = userServiceImpl.getUserById(id);

        // Convert User entity to UserDTO
        UserProfileResponse userProfile = UserMapper.mapToUserProfile(user);

        return ResponseEntity.ok(userProfile);
    }

    /**
     * Get User create quizset by quiz-id using a Get request.
     *
     * @return User .
     */
    @Operation(
            description = "Get User Create Quiz",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                                       "userId": 11152,
                                                                                       "userName": "aaaaaa",
                                                                                       "firstName": "hhhhh",
                                                                                       "lastName": "llllll",
                                                                                       "email": "user2@gmail.com",
                                                                                       "telephone": "0987654321",
                                                                                       "createdAt": "2024-02-15T07:53:00.299+00:00",
                                                                                       "role": "USER",
                                                                                       "banned": false,
                                                                                       "premium": false
                                                                                     }"""
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

    @GetMapping("/profile-user-create-quiz")
    public ResponseEntity<?> deleteQuiz(@RequestParam(name = "quizId") int quizId)
    {
        return userServiceImpl.getProfileUserCreateQuiz(quizId);
    }

    @GetMapping("/created-quiz-set/user-id={userId}")
    public ResponseEntity<List<QuizSetResponse>> getCreatedQuiz(
            @PathVariable Long userId)
    {
        try {
            List<QuizSetResponse> quizSetResponses = userServiceImpl.getCreatedQuizByUserId(userId);
            if (quizSetResponses.isEmpty())
            {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(quizSetResponses);
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
    }
    @RequestMapping(value="/update-profile-user", method = RequestMethod.PUT)
    public ResponseEntity<UserUpdateRequest> updateProfileUser(
            @RequestParam(name = "id") long userId,
            @RequestBody UserUpdateRequest request)
    {
        return userServiceImpl.updateProfileUser(userId,request);
    }

    @RequestMapping(value="/update-avatar-user", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAvaatarUser(
            @RequestParam(name = "id") long userId,
            @RequestBody String avatar)
    {
        return userServiceImpl.updateAvatarUser(userId,avatar);
    }

}
