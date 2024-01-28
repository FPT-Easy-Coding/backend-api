package com.quizztoast.backendAPI.controller;


import com.quizztoast.backendAPI.exception.EmailAlreadyTakenException;
import com.quizztoast.backendAPI.exception.EmailOrUsernameAlreadyTakenException;
import com.quizztoast.backendAPI.model.dto.LoginDTO;
import com.quizztoast.backendAPI.model.dto.QuizDTO;
import com.quizztoast.backendAPI.model.entity.user.User;
import com.quizztoast.backendAPI.security.auth_payload.AuthenticationResponse;
import com.quizztoast.backendAPI.security.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.security.auth_service.AuthenticationService;
import com.quizztoast.backendAPI.service.QuizService;
import com.quizztoast.backendAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/guest")
@RequiredArgsConstructor
public class GuestController {

    private final QuizService quizService;

    private final UserService userService;

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    /**
     * Search Quiz set using a get request.
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
                                                            [
                                                                {
                                                                    "quiz_id": 1,
                                                                    "user_id": 1,
                                                                    "class_id": 0,
                                                                    "category_id": 1,
                                                                    "quiz_name": "aaaaaaaa",
                                                                    "rate": 3.2,
                                                                    "create_at": "2024-01-28T11:52:34.029857",
                                                                    "quiz_ques_ids": [
                                                                        0
                                                                    ]
                                                                },
                                                                {
                                                                    "quiz_id": 2,
                                                                    "user_id": 1,
                                                                    "class_id": 0,
                                                                    "category_id": 1,
                                                                    "quiz_name": "bbbbbaaa",
                                                                    "rate": 3.2,
                                                                    "create_at": "2024-01-28T11:53:02.457388",
                                                                    "quiz_ques_ids": [
                                                                        0
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
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuizDTO.class)
                                    ,
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "Quiz",
                                                                        "errorMessage": "Quiz c Not Found"
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
    @GetMapping("/search-quizset")
    public ResponseEntity<List<QuizDTO>> searchQuizSet(@RequestParam String nameOfQuizSet) {
        // Check name of quiz set
        List<QuizDTO> quizSet = quizService.searchQuizSet(nameOfQuizSet);
        if (quizSet.isEmpty()) {
            throw new EmailOrUsernameAlreadyTakenException("Quiz", "Quiz " + nameOfQuizSet + " Not Found");
        } else {
            return ResponseEntity.ok(quizSet);
        }
    }
    /**
     * register new User using a Post request.
     *
     * @return A message indicating the success of the Post operation.
     */
    @Operation(
            summary = "Registers a new user",
            description = "Registers a new user with the provided details.",
            responses = {
                    @ApiResponse(
                            description = "Success. User registered successfully.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = " {\n" +
                                                            "    \"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmhhbmFuaDEyM0BnbWFpbC5jb20iLCJpYXQiOjE3MDU5NDkzODYsImV4cCI6MTcwNjAzNTc4Nn0.GbOt25veZBXl3YHwJrW101CT-gvNGC20RTQK4uBwdAk\",\n" +
                                                            "    \"refreshToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmhhbmFuaDEyM0BnbWFpbC5jb20iLCJpYXQiOjE3MDU5NDkzODYsImV4cCI6MTcwNjU1NDE4Nn0.BJAeEOcrPjd23_PlJfoMtkq345yxnRhv0eHoObNyjfo\",\n" +
                                                            "    \"mfaEnabled\": true,\n" +
                                                            "    \"secretImageUri\": \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAV4AAAFeAQAAAADlUEq3AAADHElEQVR4Xu2asZHjMAxFoXFw4ZagUlSaWJpKcQkOHXiMw/+AxtSK9vji+wiWMvnI4C9AALLNv7eH/Z75YIJ7E9yb4N4E9/Z/wE+j+eOPX/GAYY2lH99s8jsXL4KHMJ68PS93LBsGfPKb2crJQgQP4NByaaHzbd5Wzm9GOCb9/hPiC/4IQ9Lr0sJFb7PHYBZbBX8Dxweb3RnPi0+I9diKEwS/h+NPxHMqi4sxPtVWiL8jgs+w0cpFTwMXBQ/hg7XJPe9Hempvgnt76VyBvLSpShi4KAYmmknwEI6/cfmtASOeWQTymoytiG7I/YpuwT2cE3Exgko46udILSGw5/1IE3yC0XM445kuyug2qB4Cx40IFxU8hp+cQIMGp2TzVqobz4rb8tC1Ce7TMaKblx/m6bC5tRnsEN2COxh2jbnq0/KaDJ2j4eXyteQWfIbhlIbCGe0agxzLyDD0VD+mY8EvmC6aNyJ0Dosgx2QUNPi0D4JPsGfFbFzOgiaWeT/WQUgtggdw6ZwljNEboWxWMnkQTxR8hi98H4AXK54FjWOZ0Q2/nSG+4DdwuKcx5UY6DpszK88pcIiPQfAIdghcPQfvR3hqVjI4aElE8Al2zMc6PRU6Y4D4cw7W6yz44HW7wFQWg2cjgq3Yg5AXPIaNzcaDAu855YHZvBG3jHzBJziKlvySEc+tghxUqV6fBJ/h0hmU4zsgGru2pfGEX62H4Bd8oZaeLlpOiUrGqnA+RLfgHk6dEd3RZTjCetlPyLfxoASP4Mwb6ZvN8v1KRbehLTlEt+AD7Hi/MrH6W1HXxB7elpDb4aI8R/AY5kS9oQpxV7po/WIAJwh+B8c6vmu0fEOVlQz63j3fHFxU8A6XPdFzbCs6EArMsIaLxvBKx4J7mFrmryxC4KQwj+iurTTBZxj+F1dhlsp4LpixTp0Fv4PviGBUzLEMnbmWnS7+AxXygj/AeP+OrBx7WMmU3BuDXPBH2EvnSM6sZFrnt4JHMB5RwsQduNeCDOslJw85RfAow3LZ0GwsnJyrH9lACR7A35rg3gT3Jrg3wb0J7u3f4L/kiTr3Hk1oVAAAAABJRU5ErkJggg==\"\n" +
                                                            "}"
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "Conflict. User with the provided telephone or username or email already exists.",
                            responseCode = "422",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "data": [
                                                                    {
                                                                        "fieldName": "Email",
                                                                        "errorMessage": " Email already taken"
                                                                    }
                                                                ],
                                                                "message": "Validation Failed",
                                                                "error": true
                                                            }"""
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid data provided.",
                            responseCode = "400",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"data\":[{\"fieldName\":\"email\",\"errorMessage\":\"email must be a gmail account\"}],\"message\":\"Validation Failed\",\"error\":true}"
                                            )
                                    })
                    ), @ApiResponse(
                    description = "",
                    responseCode = "404",
                    content = @Content
            )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            validateEmail(request.getEmail());
            ValidateUsername(request.getUsername());
            ValidatePhoneNumber(request.getTelephone());
            AuthenticationResponse response = authenticationService.register(request);
            return ResponseEntity.ok(response);
        } catch (EmailAlreadyTakenException e) {
            throw new EmailOrUsernameAlreadyTakenException("Email", " Email already taken");
        }
    }

    private void ValidatePhoneNumber(String telephone) {
        if (userService.checkTelephone(telephone).isPresent()) {
            throw new EmailOrUsernameAlreadyTakenException("telephone", " telephone already taken");
        }
    }

    private void ValidateUsername(String username) {
        if (userService.checkUsername(username).isPresent()) {
            throw new EmailOrUsernameAlreadyTakenException("username", " username already taken");
        }
    }

    private void validateEmail(String email) {
        if (userService.userExists(email)) {
            throw new EmailOrUsernameAlreadyTakenException("Email", " Email already taken");
        }
    }
    /**
     * Login new User using a Post request.
     *
     * @return A LoginDTO the success of the Post operation.
     */
    @Operation(
            summary = "Login User",
            description = "Login",
            responses = {
                    @ApiResponse(
                            description = "Success. User login successfully.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = " {\n" +
                                                            "    \"email\": \"HEHHEHEHEE@gmail.com\",\n" +
                                                            "    \"password\": \"Hih@12312\"\n" +
                                                            "}"
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "422",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid data provided.",
                            responseCode = "400",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"password\",\n" +
                                                            "            \"errorMessage\": \"password must contain at least one uppercase letter, one number and one special character\"\n" +
                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"Validation Failed\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
                                            )
                                    })
                    ), @ApiResponse(
                    description = "Email Not Found",
                    responseCode = "404",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            value = "{\n" +
                                                    "    \"data\": [\n" +
                                                    "        {\n" +
                                                    "            \"fieldName\": \"Email\",\n" +
                                                    "            \"errorMessage\": \" Email Not Exist\"\n" +
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO logindto)
    {
        //check email exist
        if (!userService.userExists(logindto.getEmail())) {
            throw new EmailOrUsernameAlreadyTakenException("Email", " Email Not Exist");
        }
        Optional<User> userOptional = userService.getUserbyEmail(logindto.getEmail());
        //check password
        User user = userOptional.get();
        // encode password
        if (!passwordEncoder.matches(logindto.getPassword(), user.getPassword())) {
            throw new EmailOrUsernameAlreadyTakenException("Password", " Password incorrect");
        }
            return ResponseEntity.ok(logindto);

    }

}
