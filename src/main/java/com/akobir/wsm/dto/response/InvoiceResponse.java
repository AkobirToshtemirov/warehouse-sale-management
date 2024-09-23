package com.akobir.wsm.dto.response;

import com.akobir.wsm.dto.preview.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record InvoiceResponse(
        UUID id,
        int num,
        String status,
        CustomerPreview customer,
        OrganizationPreview organization,
        WarehousePreview warehouse,
        List<IncomingProductPreview> incomingProducts,
        List<OutgoingProductPreview> outgoingProducts,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
