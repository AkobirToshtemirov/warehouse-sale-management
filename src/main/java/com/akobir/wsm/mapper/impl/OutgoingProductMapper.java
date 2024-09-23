package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.OutgoingProductPreview;
import com.akobir.wsm.dto.request.OutgoingProductRequest;
import com.akobir.wsm.dto.response.OutgoingProductResponse;
import com.akobir.wsm.entity.Invoice;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.OutgoingProduct;
import com.akobir.wsm.entity.Product;
import com.akobir.wsm.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutgoingProductMapper implements Mapper<OutgoingProduct, OutgoingProductRequest, OutgoingProductResponse, OutgoingProductPreview> {

    private final OrganizationMapper organizationMapper;
    private final ProductMapper productMapper;
    private final InvoiceMapper invoiceMapper;

    @Override
    public OutgoingProduct mapToEntity(OutgoingProductRequest request) {
        if (request == null) return null;

        OutgoingProduct outgoingProduct = new OutgoingProduct();
        Organization organization = new Organization(); // Fetch from service/repository
        organization.setId(request.orgId());

        Invoice invoice = new Invoice(); // Fetch from service/repository
        invoice.setId(request.invoiceId());

        Product product = new Product(); // Fetch from service/repository
        product.setId(request.productId());

        outgoingProduct.setOrganization(organization);
        outgoingProduct.setInvoice(invoice);
        outgoingProduct.setProduct(product);
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

        return new OutgoingProductResponse(
                outgoingProduct.getId(),
                outgoingProduct.getNum(),
                outgoingProduct.getAmount(),
                outgoingProduct.getPrice(),
                outgoingProduct.getTotal(),
                productMapper.mapToPreview(outgoingProduct.getProduct()),
                invoiceMapper.mapToPreview(outgoingProduct.getInvoice()),
                organizationMapper.mapToPreview(outgoingProduct.getOrganization()),
                outgoingProduct.isDeleted(),
                outgoingProduct.getCreatedAt(),
                outgoingProduct.getUpdatedAt()
        );
    }
}
