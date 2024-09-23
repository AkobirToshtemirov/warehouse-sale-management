package com.akobir.wsm.dto.request;

import java.util.UUID;

public record WarehouseRequest(
        UUID orgId,
        String name
) {
}
