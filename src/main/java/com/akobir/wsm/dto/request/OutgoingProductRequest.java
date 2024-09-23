package com.akobir.wsm.dto.request;

import java.util.UUID;

public record OutgoingProductRequest(
        UUID orgId,
        UUID invoiceId,
        UUID productId,
        int num,
        double amount,
        double price,
        double total
) {
}
