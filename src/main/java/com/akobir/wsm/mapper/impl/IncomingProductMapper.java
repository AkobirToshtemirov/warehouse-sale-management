package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.IncomingProductPreview;
import com.akobir.wsm.dto.request.IncomingProductRequest;
import com.akobir.wsm.dto.response.IncomingProductResponse;
import com.akobir.wsm.entity.IncomingProduct;
import com.akobir.wsm.entity.Invoice;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.Product;
import com.akobir.wsm.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncomingProductMapper implements Mapper<IncomingProduct, IncomingProductRequest, IncomingProductResponse, IncomingProductPreview> {

    private final OrganizationMapper organizationMapper;
    private final ProductMapper productMapper;
    private final InvoiceMapper invoiceMapper;

    @Override
    public IncomingProduct mapToEntity(IncomingProductRequest request) {
        if (request == null) return null;

        IncomingProduct incomingProduct = new IncomingProduct();
        Organization organization = new Organization(); // Fetch from service/repository
        organization.setId(request.orgId());

        Invoice invoice = new Invoice(); // Fetch from service/repository
        invoice.setId(request.invoiceId());

        Product product = new Product(); // Fetch from service/repository
        product.setId(request.productId());

        incomingProduct.setOrganization(organization);
        incomingProduct.setInvoice(invoice);
        incomingProduct.setProduct(product);
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

        return new IncomingProductResponse(
                incomingProduct.getId(),
                incomingProduct.getNum(),
                incomingProduct.getAmount(),
                incomingProduct.getPrice(),
                incomingProduct.getTotal(),
                productMapper.mapToPreview(incomingProduct.getProduct()),
                invoiceMapper.mapToPreview(incomingProduct.getInvoice()),
                organizationMapper.mapToPreview(incomingProduct.getOrganization()),
                incomingProduct.isDeleted(),
                incomingProduct.getCreatedAt(),
                incomingProduct.getUpdatedAt()
        );
    }
}
