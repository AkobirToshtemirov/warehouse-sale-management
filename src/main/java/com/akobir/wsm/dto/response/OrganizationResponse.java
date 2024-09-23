package com.akobir.wsm.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrganizationResponse(
        UUID id,
        String name,
        String tin,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
