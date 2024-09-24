package com.akobir.wsm.service;

import com.akobir.wsm.dto.preview.OutgoingProductPreview;
import com.akobir.wsm.dto.request.OutgoingProductRequest;
import com.akobir.wsm.dto.response.OutgoingProductResponse;
import com.akobir.wsm.entity.OutgoingProduct;

import java.util.List;
import java.util.UUID;

public interface OutgoingProductService extends BaseService<OutgoingProduct, UUID, OutgoingProductRequest, OutgoingProductResponse, OutgoingProductPreview> {
    List<OutgoingProductPreview> getOutgoingProductsByInvoiceId(UUID invoiceId);

    List<OutgoingProductPreview> getAllByOrganization(UUID orgId);
}
