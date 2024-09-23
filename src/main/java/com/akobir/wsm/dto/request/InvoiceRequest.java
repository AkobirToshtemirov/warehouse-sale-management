package com.akobir.wsm.dto.request;

import java.util.UUID;

public record InvoiceRequest(
        UUID orgId,
        UUID customerId,
        int num,
        UUID warehouseId
) {
}
