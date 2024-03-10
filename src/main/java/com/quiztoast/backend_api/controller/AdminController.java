package com.quiztoast.backend_api.controller;

import com.quiztoast.backend_api.model.dto.CategoryDTO;
import com.quiztoast.backend_api.model.dto.QuizQuestionDTO;
import com.quiztoast.backend_api.model.dto.UserDTO;
import com.quiztoast.backend_api.model.entity.quiz.Category;
import com.quiztoast.backend_api.model.entity.user.User;
import com.quiztoast.backend_api.model.mapper.UserMapper;

import com.quiztoast.backend_api.model.payload.request.CategoryRequest;
import com.quiztoast.backend_api.model.payload.request.QuizQuestionRequest;
import com.quiztoast.backend_api.model.payload.response.SimpleErrorResponse;
import com.quiztoast.backend_api.service.category.CategoryServiceImpl;
import com.quiztoast.backend_api.service.quiz.QuizQuestionServiceImpl;
import com.quiztoast.backend_api.service.user.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The {@code AdminController} class defines REST API endpoints for performing administrative operations.
 * These operations are restricted to users with the 'ADMIN' role and specific authorities.
 * The controller provides endpoints for GET, POST, PUT, and DELETE actions related to administrative tasks.
 *
 * @author Ha Viet Hieu
 * @version 1.0
 * @since 2024-01-14
 */
@RestController
@RequestMapping("api/v1/admin")
//@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
@Tag(name = "Admin")
public class AdminController {

    private UserServiceImpl userServiceImpl;

    private CategoryServiceImpl categoryServiceImpl;

    private QuizQuestionServiceImpl quizQuestionServiceImpl;

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
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping("/users-dashboard")
//    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> get() {
        try {
            List<User> users = userServiceImpl.getAllUsers();
            List<UserDTO> userDTOs = UserMapper.usersToUserDTOs(users);
            return ResponseEntity.ok(userDTOs);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SimpleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

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
                            description = "Conflict. User with the provided username already exists.",
                            responseCode = "422",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"message\": \"Username already exists\" }"
                                            )
                                    }
                            )
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
                                                    value = "{ \"message\": \"Email already exists.\" }"
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

    @PostMapping("users-dashboard")
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createResource(@Valid @RequestBody User user) {
        try {
            // Check for duplicate email or username
            if (userServiceImpl.doesEmailExist(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new SimpleErrorResponse(HttpStatus.CONFLICT.value(), "Email already exists."));
            }
            if (userServiceImpl.doesUsernameExist(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new SimpleErrorResponse(HttpStatus.CONFLICT.value(), "Username already exists."));
            }
            // Validate other business rules if needed

            // Actual implementation for creating a new resource can be added here
            User newUser = userServiceImpl.addNewUser(user);
            UserDTO userDTO = UserMapper.mapUserDtoToAdmin(newUser);
            return ResponseEntity.ok(userDTO);
        } catch (ValidationException e) {
            // Handle validation errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SimpleErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SimpleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
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
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    ),
                    @ApiResponse(
                            description = "Not Found. Resource with the provided ID not found.",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "statusCode": 404,
                                                                "message": "User not found with ID: 1"
                                                            }"""
                                            )
                                    })
                    ),
                    @ApiResponse(
                            description = "Bad Request. Invalid data provided.",
                            responseCode = "400",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "email": "Invalid email address"
                                                            }"""
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
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = """
                                                            {
                                                                "statusCode": 422,
                                                                "message": "Conflict: Email already exists."
                                                            }"""
                                            )
                                    })
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "This is the request body",
                    content = @Content(schema = @Schema(implementation = User.class))
            )
    )

    @GetMapping("/users-dashboard/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        User user = userServiceImpl.getUserById(userId);
        UserDTO userDTO = UserMapper.mapUserDtoToAdmin(user);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/users-dashboard/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody User user) {
        User updatedUser = userServiceImpl.updateUser(userId, user);
        UserDTO userDTODTO = UserMapper.mapUserDtoToAdmin(updatedUser);
        return ResponseEntity.ok(userDTODTO);
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
                            responseCode = "202"
                    ),
                    @ApiResponse(
                            description = "Resource not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"statusCode\":404, \"message\":\"User not found with ID: 10286\"}"
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

    @DeleteMapping("/users-dashboard/{userId}")
//    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> delete(@PathVariable("userId") Long userId) {
        try {
            if (userServiceImpl.doesUserExist(userId)) {
                userServiceImpl.deleteUser(userId);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete Successful!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SimpleErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found with ID: " + userId));
            }
        } catch (Exception e) {
            // Handle other exceptions if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SimpleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error: " + e.getMessage()));
        }
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
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                         "categoryName": "testCategorylan3",
                                                         "createAt": "2024-02-19T13:31:12.0675008",
                                                         "totalQuiz": 0
                                                     }"""
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "data": [
                                                            {
                                                                "fieldName": "Category_name",
                                                                "errorMessage": "Category_name is exist"
                                                            }
                                                        ],
                                                        "message": "Validation Failed",
                                                        "error": true
                                                    }"""
                                    )
                            )
                    )

            }

    )

    @PostMapping("/create-category")
    public CategoryDTO createCategory(@Valid @RequestBody CategoryRequest category) {
        return categoryServiceImpl.saveCategory(category);
    }

    /**
     * get all category of quiz using a get request.
     *
     * @return list category.
     */
    @Operation(
            summary = "get all Category",
            //description = "Deletes a user identified by its ID. This operation is accessible to Admins.",
            responses = {
                    @ApiResponse(
                            description = "Success.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    [
                                                         {
                                                             "categoryId": 1,
                                                             "categoryName": "string",
                                                             "createAt": null,
                                                             "totalQuiz": 2
                                                         },
                                                         {
                                                             "categoryId": 2,
                                                             "categoryName": "string",
                                                             "createAt": null,
                                                             "totalQuiz": 3
                                                         },
                                                         {
                                                             "categoryId": 3,
                                                             "categoryName": "Software Testing",
                                                             "createAt": null,
                                                             "totalQuiz": 0
                                                         },
                                                         {
                                                             "categoryId": 4,
                                                             "categoryName": "testCategory",
                                                             "createAt": null,
                                                             "totalQuiz": 0
                                                         },
                                                         {
                                                             "categoryId": 5,
                                                             "categoryName": "testCategorylan2",
                                                             "createAt": "2024-02-19T13:06:15.754194",
                                                             "totalQuiz": 0
                                                         }
                                                     ]"""
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "",
                            responseCode = "404",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content
                    )

            }

    )
    @GetMapping("get-all-category")
    public List<Category> getAllCategory() {
        return categoryServiceImpl.getAllCategory();
    }

    /**
     * update category by id of quiz using a put request.
     *
     * @return category.
     */
    @Operation(
            summary = "update Category by id",
            //description = "Deletes a user identified by its ID. This operation is accessible to Admins.",
            responses = {
                    @ApiResponse(
                            description = "Success.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "categoryId": 7,
                                                        "categoryName": "testUpdate",
                                                        "createAt": "2024-02-19T13:31:12.067501",
                                                        "totalQuiz": 0
                                                    }"""
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "CategoryId not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "data": [
                                                            {
                                                                "fieldName": "CategoryId",
                                                                "errorMessage": "CategoryId is not exist"
                                                            }
                                                        ],
                                                        "message": "Validation Failed",
                                                        "error": true
                                                    }"""
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                         "data": [
                                                             {
                                                                 "fieldName": "categoryName",
                                                                 "errorMessage": "categoryName cannot be null"
                                                             },
                                                             {
                                                                 "fieldName": "categoryName",
                                                                 "errorMessage": "categoryName cannot be Blank"
                                                             }
                                                         ],
                                                         "message": "Validation Failed",
                                                         "error": true
                                                     }"""
                                    )
                            )
                    )

            }
    )

    @PutMapping("/update-category")
    public Category updateCategory(@RequestParam(name = "id") int id, @Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryServiceImpl.updateCategory(id, categoryRequest);
    }

    /**
     * delete category by id of quiz using a put request.
     *
     * @return String Delete succesfull.
     */
    @Operation(
            summary = "delete Category by id",
            //description = "Deletes a user identified by its ID. This operation is accessible to Admins.",
            responses = {
                    @ApiResponse(
                            description = "Success.",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                       delete successfull
                                                    }"""
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "CategoryId not found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "data": [
                                                            {
                                                                "fieldName": "CategoryId",
                                                                "errorMessage": "CategoryId is not exist"
                                                            }
                                                        ],
                                                        "message": "Validation Failed",
                                                        "error": true
                                                    }"""
                                    )
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDTO.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "data": [
                                                            {
                                                                "fieldName": "CategoryId",
                                                                "errorMessage": "exist quiz have categoryId"
                                                            }
                                                        ],
                                                        "message": "Validation Failed",
                                                        "error": true
                                                    }"""
                                    )
                            )
                    )

            }
    )

    @DeleteMapping("/delete-category")
    public ResponseEntity<?> deleteCategory(@RequestParam(name = "id") int id) {
        return categoryServiceImpl.deleteCategory(id);
    }

    @Operation(
            description = "Retrieves essential information for administrative list quiz question using a GET request."
    )
    @GetMapping("/quiz-question")
    public ResponseEntity<?> getAllQuizQuestion() {
        try {
            List<QuizQuestionDTO> quizQuestions = quizQuestionServiceImpl.getAllQuizDTO();
            return ResponseEntity.ok(quizQuestions);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SimpleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @Operation(
            description = "Retrieves essential information for administrative quiz question using a GET request."
    )
    @GetMapping("/quiz-question/{id}")
    public ResponseEntity<?> getQuizQuestion(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(quizQuestionServiceImpl.getQuizQuestionById(id));
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SimpleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @Operation(
            description = "Retrieves essential information for administrative quiz question using a GET request."
    )
    @PutMapping("/update-quiz-question")
    public ResponseEntity<?> updateQuizQuestion(
            @RequestParam(name = "id") int id,
            @Valid @RequestBody QuizQuestionRequest quizQuestionRequest
    ) {
        try {
            return ResponseEntity.ok(quizQuestionServiceImpl.updateQuizQuestion(id, quizQuestionRequest));
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SimpleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @Operation(
            description = "Retrieves essential information for administrative quiz question using a GET request."
    )
    @PostMapping("/create-quiz-question")
    public ResponseEntity<?> createQuizQuestion(
            @RequestBody QuizQuestionDTO quizQuestionDTO
    ) {
        try {
            return quizQuestionServiceImpl.createQuizQuestionAndAnswers(quizQuestionDTO);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SimpleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @Operation(
            description = "Retrieves essential information for administrative quiz question using a GET request."
    )
    @DeleteMapping("/delete-quiz-question")
    public ResponseEntity<?> deleteQuizQuestion(
            @RequestParam(name = "id") Long id
    ) {
        try {
            return ResponseEntity.ok(quizQuestionServiceImpl.deleteQuizById(id));
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SimpleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
}