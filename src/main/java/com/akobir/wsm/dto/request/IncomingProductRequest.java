package com.akobir.wsm.dto.request;

import java.util.UUID;

public record IncomingProductRequest(
        UUID orgId,
        UUID invoiceId,
        UUID productId,
        int num,
        double amount,
        double price,
        double total
) {
}
