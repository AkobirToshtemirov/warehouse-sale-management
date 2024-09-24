package com.akobir.wsm.dto.response;

import com.akobir.wsm.dto.preview.UserPreview;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        UserPreview user
) {
}
