package com.akobir.wsm.dto.preview;

import java.util.UUID;

public record UserPreview(
        UUID id,
        String username,
        String email
) {
}
