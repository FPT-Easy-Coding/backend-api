package com.quizztoast.backendAPI.controller;

import com.quizztoast.backendAPI.model.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized or Invalid Token. Access denied.",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
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
                            content = @Content(schema = @Schema(implementation = User.class))
                    ),

                    @ApiResponse(
                            description = "Unauthorized or Invalid Token. Access denied",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Conflict. User with the provided username or email already exists.",
                            responseCode = "422"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "This is the request body",
                    content = @Content(schema = @Schema(implementation = User.class))
            )
    )

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public String post() {
        return "POST:: admin controller";
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
                            content = @Content(schema = @Schema(implementation = User.class))
                    ),
                    @ApiResponse(
                            description = "Not Found. Resource with the provided ID not found.",
                            responseCode = "404"
                    ),

                    @ApiResponse(
                            description = "Unauthorized or Invalid Token. Access denied",
                            responseCode = "403"
                    ),
                    @ApiResponse(
                            description = "Conflict. User with the provided username or email already exists.",
                            responseCode = "422"
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "This is the request body",
                    content = @Content(schema = @Schema(implementation = User.class))
            )
    )
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String put() {
        return "PUT:: admin controller";
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
                            responseCode = "404"
                    )
            }
    )
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete() {
        return "DELETE:: admin controller";
    }
}
