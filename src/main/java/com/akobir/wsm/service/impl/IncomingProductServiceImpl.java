package com.akobir.wsm.service.impl;

import com.akobir.wsm.dto.preview.IncomingProductPreview;
import com.akobir.wsm.dto.request.IncomingProductRequest;
import com.akobir.wsm.dto.response.IncomingProductResponse;
import com.akobir.wsm.entity.IncomingProduct;
import com.akobir.wsm.entity.Invoice;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.Product;
import com.akobir.wsm.exception.CustomNotFoundException;
import com.akobir.wsm.mapper.impl.IncomingProductMapper;
import com.akobir.wsm.repository.IncomingProductRepository;
import com.akobir.wsm.service.IncomingProductService;
import com.akobir.wsm.service.InvoiceService;
import com.akobir.wsm.service.OrganizationService;
import com.akobir.wsm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncomingProductServiceImpl implements IncomingProductService {

    private final IncomingProductRepository incomingProductRepository;
    private final IncomingProductMapper incomingProductMapper;
    private final OrganizationService organizationService;
    private final InvoiceService invoiceService;
    private final ProductService productService;

    @Override
    public List<IncomingProductPreview> getIncomingProductsByInvoiceId(UUID invoiceId) {
        invoiceService.getInvoiceById(invoiceId);
        return incomingProductRepository.findByInvoiceId(invoiceId)
                .stream()
                .map(incomingProductMapper::mapToPreview)
                .toList();
    }

    @Override
    public List<IncomingProductPreview> getAllByOrganization(UUID orgId) {
        organizationService.getOrganizationById(orgId);
        return incomingProductRepository.findByOrganizationId(orgId)
                .stream()
                .map(incomingProductMapper::mapToPreview)
                .toList();
    }

    @Override
    public IncomingProductResponse create(IncomingProductRequest incomingProductRequest) {
        Organization organization = organizationService.getOrganizationById(incomingProductRequest.orgId());
        Invoice invoice = invoiceService.getInvoiceById(incomingProductRequest.invoiceId());
        Product product = productService.getProductById(incomingProductRequest.productId());

        IncomingProduct incomingProduct = incomingProductMapper.mapToEntity(incomingProductRequest);
        incomingProduct.setOrganization(organization);
        incomingProduct.setInvoice(invoice);
        incomingProduct.setProduct(product);

        return incomingProductMapper.mapToResponse(incomingProductRepository.save(incomingProduct));
    }

    @Override
    public IncomingProductResponse update(UUID uuid, IncomingProductRequest incomingProductRequest) {
        Optional<IncomingProduct> incomingProductOptional = incomingProductRepository.findById(uuid);
        if (incomingProductOptional.isEmpty()) {
            throw new CustomNotFoundException("IncomingProduct not found with id: " + uuid);
        }

        Organization organization = organizationService.getOrganizationById(incomingProductRequest.orgId());
        Invoice invoice = invoiceService.getInvoiceById(incomingProductRequest.invoiceId());
        Product product = productService.getProductById(incomingProductRequest.productId());

        IncomingProduct incomingProduct = incomingProductMapper.mapToEntity(incomingProductRequest);
        incomingProduct.setOrganization(organization);
        incomingProduct.setInvoice(invoice);
        incomingProduct.setProduct(product);
        incomingProduct.setId(uuid);

        return incomingProductMapper.mapToResponse(incomingProductRepository.save(incomingProduct));
    }

    @Override
    public IncomingProductResponse getById(UUID uuid) {
        return incomingProductMapper.mapToResponse(
                incomingProductRepository.findById(uuid)
                        .orElseThrow(() -> new CustomNotFoundException("IncomingProduct not found with id: " + uuid))
        );
    }

    @Override
    public List<IncomingProductPreview> getAllPreviews() {
        return incomingProductRepository.findAll()
                .stream()
                .map(incomingProductMapper::mapToPreview)
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        Optional<IncomingProduct> incomingProductOptional = incomingProductRepository.findById(uuid);
        if (incomingProductOptional.isEmpty()) {
            throw new CustomNotFoundException("IncomingProduct not found with id: " + uuid);
        }
        incomingProductRepository.deleteById(uuid);
    }
}
