package com.akobir.wsm.dto.preview;

import java.util.UUID;

public record InvoicePreview(
        UUID id,
        int num,
        String status
) {
}
