package com.akobir.wsm.service;

import com.akobir.wsm.dto.request.AuthRequest;
import com.akobir.wsm.dto.response.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);

    AuthResponse generateAccessToken(HttpServletRequest request);
}
