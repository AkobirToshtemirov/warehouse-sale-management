package com.akobir.wsm.dto.response;

import com.akobir.wsm.dto.preview.OrganizationPreview;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        double price,
        OrganizationPreview organization,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
