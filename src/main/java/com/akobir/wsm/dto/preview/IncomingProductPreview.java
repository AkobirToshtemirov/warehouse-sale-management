package com.akobir.wsm.dto.preview;

import java.util.UUID;

public record IncomingProductPreview(
        UUID id,
        int num,
        double total
) {
}
