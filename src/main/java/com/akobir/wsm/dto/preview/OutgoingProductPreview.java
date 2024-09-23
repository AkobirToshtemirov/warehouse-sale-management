package com.akobir.wsm.dto.preview;

import java.util.UUID;

public record OutgoingProductPreview(
        UUID id,
        int num,
        double total
) {
}
