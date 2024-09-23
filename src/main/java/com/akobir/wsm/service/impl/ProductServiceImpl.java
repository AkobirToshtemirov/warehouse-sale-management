package com.akobir.wsm.service.impl;

import com.akobir.wsm.dto.preview.ProductPreview;
import com.akobir.wsm.dto.request.ProductRequest;
import com.akobir.wsm.dto.response.ProductResponse;
import com.akobir.wsm.dto.response.RemainingProductByDateResponse;
import com.akobir.wsm.dto.response.RemainingProductByOrgResponse;
import com.akobir.wsm.dto.response.RemainingProductByWarehouseResponse;
import com.akobir.wsm.entity.Organization;
import com.akobir.wsm.entity.Product;
import com.akobir.wsm.entity.Warehouse;
import com.akobir.wsm.exception.CustomNotFoundException;
import com.akobir.wsm.mapper.impl.ProductMapper;
import com.akobir.wsm.mapper.impl.RemainingProductsMapper;
import com.akobir.wsm.repository.ProductRepository;
import com.akobir.wsm.service.OrganizationService;
import com.akobir.wsm.service.ProductService;
import com.akobir.wsm.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RemainingProductsMapper remainingProductsMapper;
    private final OrganizationService organizationService;
    private final WarehouseService warehouseService;

    @Override
    public RemainingProductByOrgResponse getRemainingProductsByOrganization(UUID orgId) {
        Organization organization = organizationService.getOrganizationById(orgId);
        Integer quantity = productRepository.getRemainingProductsByOrg(orgId);
        return remainingProductsMapper.toOrgResponse(organization, quantity);
    }

    @Override
    public RemainingProductByWarehouseResponse getRemainingProductsByWarehouse(UUID warehouseId) {
        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        Integer quantity = productRepository.getRemainingProductsByWarehouse(warehouseId);
        return remainingProductsMapper.toWarehouseResponse(warehouse, quantity);
    }

    @Override
    public RemainingProductByDateResponse getRemainingProductsByOrganizationBetweenDates(UUID orgId, LocalDate startDate, LocalDate endDate) {
        organizationService.getOrganizationById(orgId);
        Integer quantity = productRepository.getRemainingProductsByOrganizationBetweenDates(orgId, startDate, endDate);
        return remainingProductsMapper.toDateResponse(startDate.atStartOfDay(), endDate.atTime(23, 59, 59), quantity);
    }

    @Override
    public RemainingProductByDateResponse getRemainingProductsByWarehouseBetweenDates(UUID warehouseId, LocalDate startDate, LocalDate endDate) {
        warehouseService.getWarehouseById(warehouseId);
        Integer quantity = productRepository.getRemainingProductsByWarehouseBetweenDates(warehouseId, startDate, endDate);
        return remainingProductsMapper.toDateResponse(startDate.atStartOfDay(), endDate.atTime(23, 59, 59), quantity);
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Product not found with id: " + id));
    }

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Organization organization = organizationService.getOrganizationById(productRequest.orgId());
        Product product = productMapper.mapToEntity(productRequest);
        product.setOrganization(organization);
        return productMapper.mapToResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(UUID uuid, ProductRequest productRequest) {
        Product existingProduct = getProductById(uuid);
        Organization organization = organizationService.getOrganizationById(productRequest.orgId());
        Product updatedProduct = productMapper.mapToEntity(productRequest);
        updatedProduct.setId(existingProduct.getId());
        updatedProduct.setOrganization(organization);
        return productMapper.mapToResponse(productRepository.save(updatedProduct));
    }

    @Override
    public ProductResponse getById(UUID uuid) {
        return productMapper.mapToResponse(getProductById(uuid));
    }

    @Override
    public List<ProductPreview> getAllPreviews() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapToPreview)
                .toList();
    }

    @Override
    public void delete(UUID uuid) {
        productRepository.delete(getProductById(uuid));
    }
}
