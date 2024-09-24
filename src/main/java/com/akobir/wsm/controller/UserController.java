package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.UserPreview;
import com.akobir.wsm.dto.request.UserRequest;
import com.akobir.wsm.dto.response.UserResponse;
import com.akobir.wsm.service.UserService;
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

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        UserResponse userResponse = userService.getCachedUserByUsername(username);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<UserPreview>> getUsersByOrganization(@PathVariable UUID orgId) {
        List<UserPreview> userPreviews = userService.getAllByOrganization(orgId);
        return ResponseEntity.ok(userPreviews);
    }

    @GetMapping("/previews")
    public ResponseEntity<List<UserPreview>> getAllUserPreviews() {
        List<UserPreview> userPreviews = userService.getAllPreviews();
        return ResponseEntity.ok(userPreviews);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse createdUser = userService.create(userRequest);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID userId, @RequestBody UserRequest userRequest) {
        UserResponse updatedUser = userService.update(userId, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID userId) {
        UserResponse userResponse = userService.getById(userId);
        return ResponseEntity.ok(userResponse);
    }
}
