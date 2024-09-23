package com.akobir.wsm.dto.response;

import com.akobir.wsm.dto.preview.OrganizationPreview;
import com.akobir.wsm.dto.preview.WarehousePreview;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String mainPhoto,
        OrganizationPreview organization,
        WarehousePreview warehouse,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
