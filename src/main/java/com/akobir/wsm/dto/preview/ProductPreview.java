package com.akobir.wsm.dto.preview;

import java.util.UUID;

public record ProductPreview(
        UUID id,
        String name,
        double price
) {
}
