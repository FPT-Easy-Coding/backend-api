package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.dto.CategoryDTO;
import com.quizztoast.backendAPI.dto.UserDTO;
import com.quizztoast.backendAPI.exception.UserNotFoundException;
import com.quizztoast.backendAPI.model.quiz.Category;
import com.quizztoast.backendAPI.model.user.User;
import com.quizztoast.backendAPI.repository.TokenRepository;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.security.auth_payload.AuthenticationResponse;
import com.quizztoast.backendAPI.security.auth_payload.RegisterRequest;
import com.quizztoast.backendAPI.security.auth_service.AuthenticationService;
import com.quizztoast.backendAPI.security.recaptcha.ReCaptchaRegisterService;
import com.quizztoast.backendAPI.service.CategoryService;
import com.quizztoast.backendAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code AdminController} class defines RESTful endpoints for performing administrative operations.
 * These operations are restricted to users with the 'ADMIN' role and specific authorities.
 * The controller provides endpoints for GET, POST, PUT, and DELETE actions related to administrative tasks.
 *
 * @author Ha Viet Hieu
 * @version 1.0
 * @since 2024-01-14
 */
@RestController
@RequestMapping("api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserService service;
    @Autowired
    CategoryService categoryService;
    private final UserService userService;


    private final AuthenticationService authenticationService;
    private final ReCaptchaRegisterService reCaptchaRegisterService;

    private final TokenRepository tokenRepository;



    public AdminController(UserService userService, AuthenticationService authenticationService, ReCaptchaRegisterService reCaptchaRegisterService, TokenRepository tokenRepository) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.reCaptchaRegisterService = reCaptchaRegisterService;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Retrieves information for administrative tasks using a GET request.
     *
     * @return A message indicating the success of the GET operation.
     */

    @Operation(
            description = "Retrieves essential information for administrative tasks using a GET request.",
            summary = "Get details and data relevant to administrative functionalities.",
            responses = {
                    @ApiResponse(
                            description = "Success. Returns essential admin information.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    ),
                    @ApiResponse(
                            description = "Unauthorized or Invalid Token. Access denied.",
                            responseCode = "403",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "400",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content
                    )
            }
    )
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> get() {
        try {
            List<User> userList = userRepository.findAll();
            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SimpleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
//    public ResponseEntity<AdminController> GetListObject (
//            @RequestBody RegisterRequest request
//    ){
//        return ResponseEntity.ok(authenticationService.register(request));
//    }
    /**
     * Handles administrative tasks using a POST request.
     *
     * @return A message indicating the success of the POST operation.
     */
    @Operation(
            summary = "Create a new resource using a POST request",
            description = "Create a new resource with the provided data. This operation is accessible only to users with Admin privileges.",
            responses = {
                    @ApiResponse(
                            description = "Success. Resource created successfully.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJIaWV1MjgxQGdtYWlsLmNvbSIsImlhdCI6MTcwNjE5MzIzNiwiZXhwIjoxNzA2Mjc5NjM2fQ.FmnWEJzSOSvdMzo8DPgPQbasMmTE9ZoAD_EibhX7CaQ\",\n" +
                                                            "    \"refreshToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJIaWV1MjgxQGdtYWlsLmNvbSIsImlhdCI6MTcwNjE5MzIzNiwiZXhwIjoxNzA2Nzk4MDM2fQ.e4PFiGZ_TcMDmenIsJEfbAH09GXV2hnzVOv85uw8wco\",\n" +
                                                            "    \"mfaEnabled\": true,\n" +
                                                            "    \"secretImageUri\": \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAV4AAAFeAQAAAADlUEq3AAADDElEQVR4Xu2aO3LrMAxFoUmRMkvQUrQ0eWleipaQ0oXHeDgAY1E27Unqd9HQAg9V3OBHTcx/b1d79Lwxwb0J7k1wb4J7+z/gm6X59fN7Pq/x62uzNba+/GyTX3LzQ/AQ5pefbh8Xm8/hv3zFU3i+zdZbPhUieACHlktsI+k6BRVnEk5nqG6C38I3ixB1Fkf1OBgBK/g3sGPndboGFnJfP31bTgSs4Dcw2IkzobOhcwgcZ+Z80WN2C+5ghEXLkHThzHHJTcFDeLcohd4KY1LLYVdwb7vOLZFZcockjzNTjTeT4DGcYbi2JDeGwCiTV5baYxE8ghmcQ+dswO6tMELN9/qYJvgZLrv9lEL3LS4ijISLR8ASooJfwwRlK4w1wjAL5hmsUl7wE1xpTTMpfwhs3HRRnZ+H7Bbcw54C19Cy4MtIjYuIlc5bk1vwAI4ZmTtH3dpIclvJbp6qTO7tWPAjjJZXovFeGOsLVW63RfATHH2YmxlDYPnpInTlbcm9LVuL4CEcYegx9hGNln7O1CRTRx0TPIbDUbeMY3ZPTfWjzoLvMCF64sN7bFs90ZXnEphcxwQ/w6R1yR2JjLK2TzLVo/uxR3A/16Wfba+KWG9AdRbrdRZ8hDODmVZS5zyTyz1Sj/Oz4CNs2YAtz9BTmGusKiKf+gSP4Bvu6CKMfRTGPAPV4rY9CR7AGYZmVR/TzxvqsxVPFb6CBzAzskUGt/qYAcskY21wFvwKpqdEbE75kQAfSx61ugVzVPAIrmhk8ewi+dEFgUN1q79Byi34Gc7PxtbMMXpKvaFCNJ2CBzDznkPO97Gvcr0+MJvgl7DXf6Yk7HuSh8DZqitgBT/DzVo0trTOMkmjcXTue4rgQzvGCrai8JPdjDeoLngMQ/183EtJG5y5njof4llwB1/IYLpyy24Oe910+QsseUbwGzgrYpufM8mb3OdMcsGvYRwRok3nnGROzNbbY+8WvMP83HW+EJY4N96QcNdTBPcwmz9jHxdeYhPnXGWSaVrwEP6tCe5NcG+CexPcm+De/gb/A4F1ywreHRBoAAAAAElFTkSuQmCC\"\n" +
                                                            "}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid data provided",
                            responseCode = "400",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"firstname\",\n" +
                                                            "            \"errorMessage\": \"firstname cannot be blank\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"lastname\",\n" +
                                                            "            \"errorMessage\": \"lastname cannot be blank\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"lastname\",\n" +
                                                            "            \"errorMessage\": \"lastname cannot be null\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"telephone\",\n" +
                                                            "            \"errorMessage\": \"telephone must be a valid phone number\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"password\",\n" +
                                                            "            \"errorMessage\": \"Password must contain at least one uppercase letter, one number, and one special character.\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"firstname\",\n" +
                                                            "            \"errorMessage\": \"firstname cannot be null\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"email\",\n" +
                                                            "            \"errorMessage\": \"email must be a valid email\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"email\",\n" +
                                                            "            \"errorMessage\": \"email must be a gmail account\"\n" +
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
                    @ApiResponse(
                            description = "Unauthorized or Invalid Token. Access denied",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Conflict. User with the provided email already exists.",
                            responseCode = "422",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"email\",\n" +
                                                            "            \"errorMessage\": \"email already exists\"\n" +
                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"Validation Failed\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
                                            )
                                    }
                            )
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "This is the request body",
                    content = @Content(schema = @Schema(implementation = User.class))
            )
    )
    /**
     * Handles administrative tasks using a POST request.
     *
     * @return A message indicating the success of the POST operation.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createResource(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.ok(response);
    }


    /**
     * Updates administrative information using a PUT request.
     *
     * @return A message indicating the success of the PUT operation.
     */

    @Operation(
            summary = "Updates a User by ID",
            description = "Updates an existing resource identified by its ID with the provided data. This operation is accessible to Admins.",
            responses = {
                    @ApiResponse(
                            description = "Success. Resource updated successfully.",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"headers\": {},\n" +
                                                            "    \"body\": {\n" +
                                                            "        \"userId\": 202,\n" +
                                                            "        \"username\": \"Hiiii1@gmail.com\",\n" +
                                                            "        \"password\": \"K(xe<N9!Ed[&d6-+;TiF\",\n" +
                                                            "        \"firstName\": \"string\",\n" +
                                                            "        \"lastName\": \"string\",\n" +
                                                            "        \"email\": \"Hiiii1@gmail.com\",\n" +
                                                            "        \"telephone\": \"--4\",\n" +
                                                            "        \"createdAt\": null,\n" +
                                                            "        \"googleId\": null,\n" +
                                                            "        \"role\": \"USER\",\n" +
                                                            "        \"mfaEnabled\": false,\n" +
                                                            "        \"secret\": null,\n" +
                                                            "        \"tokens\": [\n" +
                                                            "            {\n" +
                                                            "                \"tokenId\": 152,\n" +
                                                            "                \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjJAZ21haWwuY29tIiwiaWF0IjoxNzA2MjcyOTIzLCJleHAiOjE3MDYzNTkzMjN9.qH-gwZgtGJtxgcul1OHlHKy8vWkhAfwbkYe-_CT59Yk\",\n" +
                                                            "                \"tokenType\": \"BEARER\",\n" +
                                                            "                \"expired\": false,\n" +
                                                            "                \"revoked\": false\n" +
                                                            "            }\n" +
                                                            "        ],\n" +
                                                            "        \"enabled\": true,\n" +
                                                            "        \"authorities\": [\n" +
                                                            "            {\n" +
                                                            "                \"authority\": \"ROLE_USER\"\n" +
                                                            "            }\n" +
                                                            "        ],\n" +
                                                            "        \"banned\": false,\n" +
                                                            "        \"premium\": false,\n" +
                                                            "        \"accountNonLocked\": true,\n" +
                                                            "        \"credentialsNonExpired\": true,\n" +
                                                            "        \"accountNonExpired\": true\n" +
                                                            "    },\n" +
                                                            "    \"statusCode\": \"OK\",\n" +
                                                            "    \"statusCodeValue\": 200\n" +
                                                            "}"
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "Not Found. Resource with the provided ID not found.",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"userId\",\n" +
                                                            "            \"errorMessage\": \"User with ID 123 not found\"\n" +
                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"User not found\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
                                            )
                                    })
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
                                                            "            \"fieldName\": \"email\",\n" +
                                                            "            \"errorMessage\": \"email must be a valid email\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"lastname\",\n" +
                                                            "            \"errorMessage\": \"lastname cannot be null\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"password\",\n" +
                                                            "            \"errorMessage\": \"password must contain at least one uppercase letter, one number and one special character\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"lastname\",\n" +
                                                            "            \"errorMessage\": \"lastname cannot be blank\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"telephone\",\n" +
                                                            "            \"errorMessage\": \"telephone must be a valid phone number\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"firstname\",\n" +
                                                            "            \"errorMessage\": \"firstname cannot be blank\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"email\",\n" +
                                                            "            \"errorMessage\": \"email must be a gmail account\"\n" +
                                                            "        },\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"firstname\",\n" +
                                                            "            \"errorMessage\": \"firstname cannot be null\"\n" +
                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"Validation Failed\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "Unauthorized or Invalid Token. Access denied",
                            responseCode = "403",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            description = "Conflict. User with the provided username or email already exists.",
                            responseCode = "422",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"data\": [\n" +
                                                            "        {\n" +
                                                            "            \"fieldName\": \"username\",\n" +
                                                            "            \"errorMessage\": \"username :adasasdas already exist\"\n" +

                                                            "        }\n" +
                                                            "    ],\n" +
                                                            "    \"message\": \"Validation Failed\",\n" +
                                                            "    \"error\": true\n" +
                                                            "}"
                                            )
                                    })
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "This is the request body",
                    content = @Content(schema = @Schema(implementation = RegisterRequest.class))
            )
    )

    /**
     * Get information using a PUT request.
     *
     * @return A message indicating the success of the PUT operation.
     */
    @PutMapping("/{userId}")
   // @PreAuthorize("hasAuthority('admin:update')")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> UpdateUserById(@PathVariable Long userId, @Valid @RequestBody UserDTO request) {
        if (userRepository.findByUserId(userId) == null) {
            // User not found, return a 404 response with JSON format
            throw new UserNotFoundException(userId);
        }
        ResponseEntity<User> response = service.UpdateUser(userId,request);
        return ResponseEntity.ok(response);
    }


    /**
     * Deletes administrative records using a DELETE request.
     *
     * @return A message indicating the success of the DELETE operation.
     */
    @Operation(
            summary = "Deletes a User by ID",
            description = "Deletes a user identified by its ID. This operation is accessible to Admins.",
            responses = {
                    @ApiResponse(
                            description = "Success. Resource deleted successfully.",
                            responseCode = "202",
                            content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SimpleErrorResponse.class),
                            examples = @ExampleObject(
                                    value = "User deleted successfully"
                            )
                    )
                    ),
                    @ApiResponse(
                            description = "Resource not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "    \"data\": [\n" +
                                                    "        {\n" +
                                                    "            \"fieldName\": \"userId\",\n" +
                                                    "            \"errorMessage\": \"User with ID 50 not found\"\n" +
                                                    "        }\n" +
                                                    "    ],\n" +
                                                    "    \"message\": \"User not found\",\n" +
                                                    "    \"error\": true\n" +
                                                    "}"
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Resource not found",
                            responseCode = "400",
                            content = @Content
                            )


            }

    )
    @DeleteMapping("/delete/{userId}")
   // @PreAuthorize("hasAuthority('admin:delete')")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        // Kiểm tra xem userId có tồn tại không
        // Nếu không tồn tại, trả về lỗi 404 theo định dạng JSON
        if (userRepository.findByUserId(userId) == null) {
            // User not found, return a 404 response with JSON format
           throw new UserNotFoundException(userId);
        }
        // Xóa trong token trước khi xóa user
        tokenRepository.deleteTokenByUserId(userId);

        // Xóa user nếu tồn tại
        userRepository.deleteById(userId);

        return ResponseEntity.ok("User deleted successfully");

    }

    /**
     * create category of quiz using a Post request.
     *
     * @return A message indicating the success of the Post operation.
     */
    @Operation(
            summary = "Create New Category",
            //description = "Deletes a user identified by its ID. This operation is accessible to Admins.",
            responses = {
                    @ApiResponse(
                            description = "Success. Resource deleted successfully.",
                            responseCode = "200", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDTO.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"category_id\": 3,\n" +
                                            "    \"category_name\": \"Software Testing\"\n" +
                                            "}"
                            )
                    )
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "400",
                            content = @Content
                    )

            }

    )

    @PostMapping("/create_category")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO category){
        Category response =   categoryService.saveCategory(category);
        return ResponseEntity.ok(response);
    }


}
