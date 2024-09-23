package com.akobir.wsm.dto.request;

import java.util.UUID;

public record ProductRequest(
        UUID orgId,
        String name,
        double price
) {
}
