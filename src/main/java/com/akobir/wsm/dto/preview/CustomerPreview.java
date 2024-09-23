package com.akobir.wsm.dto.preview;

import java.util.UUID;

public record CustomerPreview(
        UUID id,
        String name
) {
}
