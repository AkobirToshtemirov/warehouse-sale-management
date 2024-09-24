package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.InvoicePreview;
import com.akobir.wsm.dto.preview.OutgoingProductPreview;
import com.akobir.wsm.dto.request.OutgoingProductRequest;
import com.akobir.wsm.dto.response.OutgoingProductResponse;
import com.akobir.wsm.entity.Invoice;
import com.akobir.wsm.entity.OutgoingProduct;
import com.akobir.wsm.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutgoingProductMapper implements Mapper<OutgoingProduct, OutgoingProductRequest, OutgoingProductResponse, OutgoingProductPreview> {

    private final OrganizationMapper organizationMapper;
    private final ProductMapper productMapper;

    @Override
    public OutgoingProduct mapToEntity(OutgoingProductRequest request) {
        if (request == null) return null;

        OutgoingProduct outgoingProduct = new OutgoingProduct();
        outgoingProduct.setNum(request.num());
        outgoingProduct.setAmount(request.amount());
        outgoingProduct.setPrice(request.price());
        outgoingProduct.setTotal(request.total());

        return outgoingProduct;
    }

    @Override
    public OutgoingProductPreview mapToPreview(OutgoingProduct outgoingProduct) {
        if (outgoingProduct == null) return null;

        return new OutgoingProductPreview(
                outgoingProduct.getId(),
                outgoingProduct.getNum(),
                outgoingProduct.getTotal()
        );
    }

    @Override
    public OutgoingProductResponse mapToResponse(OutgoingProduct outgoingProduct) {
        if (outgoingProduct == null) return null;

        Invoice invoice = outgoingProduct.getInvoice();
        return new OutgoingProductResponse(
                outgoingProduct.getId(),
                outgoingProduct.getNum(),
                outgoingProduct.getAmount(),
                outgoingProduct.getPrice(),
                outgoingProduct.getTotal(),
                productMapper.mapToPreview(outgoingProduct.getProduct()),
                new InvoicePreview(invoice.getId(), invoice.getNum(), invoice.getStatus().name()),
                organizationMapper.mapToPreview(outgoingProduct.getOrganization()),
                outgoingProduct.isDeleted(),
                outgoingProduct.getCreatedAt(),
                outgoingProduct.getUpdatedAt()
        );
    }
}
