package com.akobir.wsm.service.impl;

import com.akobir.wsm.config.security.service.CustomUserDetails;
import com.akobir.wsm.config.security.service.CustomUserDetailsService;
import com.akobir.wsm.config.security.service.JwtService;
import com.akobir.wsm.dto.request.AuthRequest;
import com.akobir.wsm.dto.response.AuthResponse;
import com.akobir.wsm.entity.User;
import com.akobir.wsm.exception.AuthenticationFailedException;
import com.akobir.wsm.exception.InvalidTokenException;
import com.akobir.wsm.mapper.impl.UserMapper;
import com.akobir.wsm.service.AuthService;
import com.akobir.wsm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserMapper userMapper;
    private final UserService userService;

    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Invalid username or password.");
        }

        User user = userService.getByUsername(request.username());
        String accessToken = jwtService.generateAccessToken(new CustomUserDetails(user));
        String refreshToken = jwtService.generateRefreshToken(new CustomUserDetails(user));

        return new AuthResponse(accessToken, refreshToken, userMapper.mapToPreview(user));
    }

    @Override
    public AuthResponse generateAccessToken(HttpServletRequest request) {
        String refreshToken = extractBearerToken(request);

        if (refreshToken == null) {
            throw new InvalidTokenException("Token format is invalid.");
        }

        if (jwtService.isTokenExpired(refreshToken)) {
            throw new InvalidTokenException("Refresh token is expired.");
        }

        try {
            String username = jwtService.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtService.generateAccessToken(userDetails);

            User user = userService.getByUsername(username);
            return new AuthResponse(newAccessToken, refreshToken, userMapper.mapToPreview(user));
        } catch (Exception e) {
            throw new InvalidTokenException("Failed to generate access token from refresh token.");
        }
    }

    private String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
