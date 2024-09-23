package com.akobir.wsm.dto.response;

import com.akobir.wsm.dto.preview.InvoicePreview;
import com.akobir.wsm.dto.preview.OrganizationPreview;
import com.akobir.wsm.dto.preview.ProductPreview;

import java.time.LocalDateTime;
import java.util.UUID;

public record OutgoingProductResponse(
        UUID id,
        int num,
        double amount,
        double price,
        double total,
        ProductPreview product,
        InvoicePreview invoice,
        OrganizationPreview organization,
        boolean isDeleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
