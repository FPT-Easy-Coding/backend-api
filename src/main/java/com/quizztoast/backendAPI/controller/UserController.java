package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.model.dto.UserDTO;
import com.quizztoast.backendAPI.model.payload.response.SimpleErrorResponse;
import com.quizztoast.backendAPI.model.payload.response.UserProfileResponse;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.model.mapper.UserMapper;
import com.quizztoast.backendAPI.model.payload.request.ChangePasswordRequest;

import com.quizztoast.backendAPI.service.user.UserServiceImpl;
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

    @GetMapping("/profile/{id}")
    @RequestMapping(value = "profile/user-id={id}", method = RequestMethod.GET)
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable("id") Long id) {
        User user = userServiceImpl.getUserById(id);

        // Convert User entity to UserDTO
        UserProfileResponse userProfile = UserMapper.mapToUserProfile(user);

        return ResponseEntity.ok(userProfile);
    }

//    private String passwordResetEmailLink(User user, String applicationUrl, String token) {
//        String url = applicationUrl + "/reset-password?token=" + token;
//
//    }
//    public String resetPasswordRequest(
//            @RequestBody PasswordResetRequest passwordResetRequest,
//            final HttpServletRequest request
//            ) {
//        Optional<User> user = userServiceImpl.getUserByEmail(passwordResetRequest.getEmail());
//        String passwordResetUrl = "";
//        if (user.isPresent()) {
//            String passwordResetToken = UUID.randomUUID().toString();
//            userServiceImpl.createPasswordResetTokenForUser(user.get(), passwordResetToken);
//            passwordResetUrl = passwordResetEmailLink(user.get(), applicationUrl(request), passwordResetToken);
//        }
//        return passwordResetUrl;
//    }
}
