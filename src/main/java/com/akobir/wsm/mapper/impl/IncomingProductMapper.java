package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.IncomingProductPreview;
import com.akobir.wsm.dto.preview.InvoicePreview;
import com.akobir.wsm.dto.request.IncomingProductRequest;
import com.akobir.wsm.dto.response.IncomingProductResponse;
import com.akobir.wsm.entity.IncomingProduct;
import com.akobir.wsm.entity.Invoice;
import com.akobir.wsm.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncomingProductMapper implements Mapper<IncomingProduct, IncomingProductRequest, IncomingProductResponse, IncomingProductPreview> {

    private final OrganizationMapper organizationMapper;
    private final ProductMapper productMapper;

    @Override
    public IncomingProduct mapToEntity(IncomingProductRequest request) {
        if (request == null) return null;

        IncomingProduct incomingProduct = new IncomingProduct();
        incomingProduct.setNum(request.num());
        incomingProduct.setAmount(request.amount());
        incomingProduct.setPrice(request.price());
        incomingProduct.setTotal(request.total());

        return incomingProduct;
    }

    @Override
    public IncomingProductPreview mapToPreview(IncomingProduct incomingProduct) {
        if (incomingProduct == null) return null;

        return new IncomingProductPreview(
                incomingProduct.getId(),
                incomingProduct.getNum(),
                incomingProduct.getTotal()
        );
    }

    @Override
    public IncomingProductResponse mapToResponse(IncomingProduct incomingProduct) {
        if (incomingProduct == null) return null;

        Invoice invoice = incomingProduct.getInvoice();
        return new IncomingProductResponse(
                incomingProduct.getId(),
                incomingProduct.getNum(),
                incomingProduct.getAmount(),
                incomingProduct.getPrice(),
                incomingProduct.getTotal(),
                productMapper.mapToPreview(incomingProduct.getProduct()),
                new InvoicePreview(invoice.getId(), incomingProduct.getNum(), invoice.getStatus().name()),
                organizationMapper.mapToPreview(incomingProduct.getOrganization()),
                incomingProduct.isDeleted(),
                incomingProduct.getCreatedAt(),
                incomingProduct.getUpdatedAt()
        );
    }
}
