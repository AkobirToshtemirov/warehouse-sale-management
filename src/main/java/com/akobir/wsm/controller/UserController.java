package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.UserPreview;
import com.akobir.wsm.dto.request.UserRequest;
import com.akobir.wsm.dto.response.UserResponse;
import com.akobir.wsm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user by username",
            description = "Retrieves detailed information about a user using their username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(
            @Parameter(description = "Username of the user to retrieve", required = true)
            @PathVariable String username) {
        UserResponse userResponse = userService.getCachedUserByUsername(username);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Get users by organization ID",
            description = "Retrieves a list of users associated with a specific organization.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<UserPreview>> getUsersByOrganization(
            @Parameter(description = "ID of the organization to retrieve users", required = true)
            @PathVariable UUID orgId) {
        List<UserPreview> userPreviews = userService.getAllByOrganization(orgId);
        return ResponseEntity.ok(userPreviews);
    }

    @Operation(summary = "Get all user previews",
            description = "Retrieves a list of previews for all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User previews retrieved successfully")
    })
    @GetMapping("/previews")
    public ResponseEntity<List<UserPreview>> getAllUserPreviews() {
        List<UserPreview> userPreviews = userService.getAllPreviews();
        return ResponseEntity.ok(userPreviews);
    }

    @Operation(summary = "Create a new user",
            description = "Creates a new user with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user details provided")
    })
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Parameter(description = "User details to create", required = true)
            @RequestBody UserRequest userRequest) {
        UserResponse createdUser = userService.create(userRequest);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Update an existing user",
            description = "Updates the details of an existing user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid user details provided")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "ID of the user to update", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Updated user details", required = true)
            @RequestBody UserRequest userRequest) {
        UserResponse updatedUser = userService.update(userId, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete a user",
            description = "Deletes the user associated with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true)
            @PathVariable UUID userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get user by ID",
            description = "Retrieves detailed information about a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true)
            @PathVariable UUID userId) {
        UserResponse userResponse = userService.getById(userId);
        return ResponseEntity.ok(userResponse);
    }
}
