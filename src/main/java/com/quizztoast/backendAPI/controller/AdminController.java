package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.dto.CategoryDTO;
import com.quizztoast.backendAPI.model.quiz.Category;
import com.quizztoast.backendAPI.model.user.Role;
import com.quizztoast.backendAPI.model.user.User;
import com.quizztoast.backendAPI.repository.UserRepository;
import com.quizztoast.backendAPI.service.CategoryService;
import com.quizztoast.backendAPI.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    /**
     * Handles administrative tasks using a POST request.
     *
     * @return A message indicating the success of the POST operation.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createResource(@Valid @RequestBody User newUser) {
        try {
            // Check for duplicate email or username
            if (service.doesEmailExist(newUser.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new SimpleErrorResponse(HttpStatus.CONFLICT.value(), "Email already exists."));
            }
            if (service.doesUsernameExist(newUser.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new SimpleErrorResponse(HttpStatus.CONFLICT.value(), "Username already exists."));
            }
            // Validate other business rules if needed

            // Actual implementation for creating a new resource can be added here
            service.addNewUser(newUser);
            return ResponseEntity.ok(newUser);
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
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = User.class))
                    ),
                    @ApiResponse(
                            description = "Not Found. Resource with the provided ID not found.",
                            responseCode = "404",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimpleErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\n" +
                                                            "    \"statusCode\": 404,\n" +
                                                            "    \"message\": \"User not found with ID: 1\"\n" +
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
                                                            "    \"email\": \"Invalid email address\"\n" +
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
                                                            "    \"statusCode\": 422,\n" +
                                                            "    \"message\": \"Conflict: Email already exists.\"\n" +
                                                            "}"
                                            )
                                    })
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "This is the request body",
                    content = @Content(schema = @Schema(implementation = User.class))
            )
    )

    /**
     * Get information using a PUT request.
     *
     * @return A message indicating the success of the PUT operation.
     */
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> put(@PathVariable Long userId, @Valid @RequestBody User updatedUser) {
        try {
            // Validate email format
            if (!isValidEmail(updatedUser.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid email address."));
            }

            // Validate password length
            if (updatedUser.getPassword() != null && updatedUser.getPassword().length() < 6) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new SimpleErrorResponse(HttpStatus.BAD_REQUEST.value(), "Password must be at least 6 characters long."));
            }

            Optional<User> existingUserOptional = userRepository.findById(userId);

            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();

                // Check if the updated username or email already exists
                if (!existingUser.getUsername().equals(updatedUser.getUsername()) && service.doesUsernameExist(updatedUser.getUsername())) {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                            .body(new SimpleErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Conflict: Username already exists."));
                }

                if (!existingUser.getEmail().equals(updatedUser.getEmail()) && service.doesEmailExist(updatedUser.getEmail())) {
                    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                            .body(new SimpleErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Conflict: Email already exists."));
                }

                // Update the existing user with the provided data
                existingUser.setUsername(updatedUser.getUsername()); // Ensure correct username update
                existingUser.setPassword(updatedUser.getPassword());
                existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                existingUser.setEmail(updatedUser.getEmail()); // Ensure correct email update
                existingUser.setTelephone(updatedUser.getTelephone());
                existingUser.setBanned(updatedUser.isBanned());
                existingUser.setGoogleId(updatedUser.getGoogleId());
                existingUser.setRole(updatedUser.getRole());
                existingUser.setPremium(updatedUser.isPremium());

                // Save the updated user
                userRepository.save(existingUser);

                return ResponseEntity.ok(existingUser);
            } else {
                // User with the provided ID not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new SimpleErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found with ID: " + userId));
            }
        } catch (ValidationException e) {
            // Handle validation errors (e.g., invalid data provided)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new SimpleErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (AccessDeniedException e) {
            // Handle access denied errors (e.g., user does not have sufficient privileges)
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new SimpleErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            // Handle other conflicts or exceptions
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new SimpleErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Conflict: " + e.getMessage()));
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SimpleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error: " + e.getMessage()));
        }
    }

    private boolean isValidEmail(String email) {
        // Use your preferred email validation logic or library
        // For simplicity, a basic regex pattern is used here
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
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

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> delete(@RequestParam int userId) {
        try {
            if (service.doesUserExist(userId)) {
                service.deleteUser(userId);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Delete Successful!");
            }
            else {
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
    public Category createCategory(@Valid @RequestBody Category category){
        return categoryService.saveCategory(category);
    }


}
