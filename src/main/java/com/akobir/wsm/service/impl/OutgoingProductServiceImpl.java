package com.akobir.wsm.service.impl;

import com.akobir.wsm.dto.preview.OutgoingProductPreview;
import com.akobir.wsm.dto.request.OutgoingProductRequest;
import com.akobir.wsm.dto.response.OutgoingProductResponse;
import com.akobir.wsm.entity.Invoice;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.OutgoingProduct;
import com.akobir.wsm.entity.Product;
import com.akobir.wsm.exception.CustomNotFoundException;
import com.akobir.wsm.mapper.impl.OutgoingProductMapper;
import com.akobir.wsm.repository.OutgoingProductRepository;
import com.akobir.wsm.service.InvoiceService;
import com.akobir.wsm.service.OrganizationService;
import com.akobir.wsm.service.OutgoingProductService;
import com.akobir.wsm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutgoingProductServiceImpl implements OutgoingProductService {

    private final OutgoingProductRepository outgoingProductRepository;
    private final OutgoingProductMapper outgoingProductMapper;
    private final OrganizationService organizationService;
    private final InvoiceService invoiceService;
    private final ProductService productService;

    @Override
    public List<OutgoingProductPreview> getOutgoingProductsByInvoiceId(UUID invoiceId) {
        invoiceService.getInvoiceById(invoiceId);
        return outgoingProductRepository.findByInvoiceId(invoiceId)
                .stream()
                .map(outgoingProductMapper::mapToPreview)
                .toList();
    }

    @Override
    public List<OutgoingProductPreview> getAllByOrganization(UUID orgId) {
        organizationService.getOrganizationById(orgId);
        return outgoingProductRepository.findByOrganizationId(orgId)
                .stream()
                .map(outgoingProductMapper::mapToPreview)
                .toList();
    }

    @Override
    public OutgoingProductResponse create(OutgoingProductRequest outgoingProductRequest) {
        Organization organization = organizationService.getOrganizationById(outgoingProductRequest.orgId());
        Invoice invoice = invoiceService.getInvoiceById(outgoingProductRequest.invoiceId());
        Product product = productService.getProductById(outgoingProductRequest.productId());

        OutgoingProduct outgoingProduct = outgoingProductMapper.mapToEntity(outgoingProductRequest);
        outgoingProduct.setOrganization(organization);
        outgoingProduct.setInvoice(invoice);
        outgoingProduct.setProduct(product);

        return outgoingProductMapper.mapToResponse(outgoingProductRepository.save(outgoingProduct));
    }

    @Override
    public OutgoingProductResponse update(UUID uuid, OutgoingProductRequest outgoingProductRequest) {
        Optional<OutgoingProduct> outgoingProductOptional = outgoingProductRepository.findById(uuid);
        if (outgoingProductOptional.isEmpty()) {
            throw new CustomNotFoundException("OutgoingProduct not found with id: " + uuid);
        }

        Organization organization = organizationService.getOrganizationById(outgoingProductRequest.orgId());
        Invoice invoice = invoiceService.getInvoiceById(outgoingProductRequest.invoiceId());
        Product product = productService.getProductById(outgoingProductRequest.productId());

        OutgoingProduct outgoingProduct = outgoingProductMapper.mapToEntity(outgoingProductRequest);
        outgoingProduct.setOrganization(organization);
        outgoingProduct.setInvoice(invoice);
        outgoingProduct.setProduct(product);
        outgoingProduct.setId(uuid);

        return outgoingProductMapper.mapToResponse(outgoingProductRepository.save(outgoingProduct));
    }

    @Override
    public OutgoingProductResponse getById(UUID uuid) {
        return outgoingProductMapper.mapToResponse(
                outgoingProductRepository.findById(uuid)
                        .orElseThrow(() -> new CustomNotFoundException("OutgoingProduct not found with id: " + uuid))
        );
    }

    @Override
    public List<OutgoingProductPreview> getAllPreviews() {
        return outgoingProductRepository.findAll()
                .stream()
                .map(outgoingProductMapper::mapToPreview)
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        Optional<OutgoingProduct> outgoingProductOptional = outgoingProductRepository.findById(uuid);
        if (outgoingProductOptional.isEmpty()) {
            throw new CustomNotFoundException("OutgoingProduct not found with id: " + uuid);
        }
        outgoingProductRepository.deleteById(uuid);
    }
}
