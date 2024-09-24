package com.akobir.wsm.controller;

import com.akobir.wsm.dto.request.AuthRequest;
import com.akobir.wsm.dto.response.AuthResponse;
import com.akobir.wsm.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Authenticate user",
            description = "Authenticate a user with a username and password and return an access token and refresh token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @Parameter(description = "Authentication request containing username and password", required = true)
            @RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Generate new access token using refresh token",
            description = "Generates a new access token when provided with a valid refresh token."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access token generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token or token expired"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> generateAccessToken(
            @Parameter(description = "Request containing the refresh token", required = true)
            HttpServletRequest request) {
        AuthResponse response = authService.generateAccessToken(request);
        return ResponseEntity.ok(response);
    }
}
