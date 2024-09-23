package com.akobir.wsm.dto.request;

import java.util.UUID;

public record UserRequest(
        UUID orgId,
        String username,
        String password,
        UUID warehouseId,
        String email,
        String mainPhoto
) {
}
