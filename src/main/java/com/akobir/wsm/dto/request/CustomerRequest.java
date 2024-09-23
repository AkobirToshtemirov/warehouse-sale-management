package com.akobir.wsm.dto.request;

import java.util.UUID;

public record CustomerRequest(
        UUID orgId,
        String name
) {
}
