package com.akobir.wsm.service;

import com.akobir.wsm.dto.preview.IncomingProductPreview;
import com.akobir.wsm.dto.request.IncomingProductRequest;
import com.akobir.wsm.dto.response.IncomingProductResponse;
import com.akobir.wsm.entity.IncomingProduct;

import java.util.List;
import java.util.UUID;

public interface IncomingProductService extends BaseService<IncomingProduct, UUID, IncomingProductRequest, IncomingProductResponse, IncomingProductPreview> {
    List<IncomingProductPreview> getAllByOrganization(UUID orgId);
}
