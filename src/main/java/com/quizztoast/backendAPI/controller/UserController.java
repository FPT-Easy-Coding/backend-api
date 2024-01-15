package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.security.auth_payload.ChangePasswordRequest;
import com.quizztoast.backendAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//
    /**
     * Changes the user's password.
     *
     * @param  userId       The ID of the user whose password is to be changed.
     * @param changePwdRequest The request containing old and new passwords.
     * @return A ResponseEntity with a status code indicating the result of the operation.
     */
    @Operation(
            summary = "Changes the user's password",
            description = "Changes the password for the currently authenticated user.",
            responses = {
                    @ApiResponse(
                            description = "Success. User account deleted successfully.",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid user ID provided.",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Unauthorized. User not authorized to delete this account.",
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "Not Found. User account with the provided ID not found.",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error.",
                            responseCode = "500"
                    )
            }
    )

    @PatchMapping("/api/user/changepassword/{userId}")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest,
            Principal connectedUser
    ){
        userService.changePassword(changePasswordRequest,connectedUser);
        return ResponseEntity.ok().build();
    }
}
