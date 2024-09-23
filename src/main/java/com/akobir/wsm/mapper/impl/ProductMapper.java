package com.akobir.wsm.mapper.impl;

import com.akobir.wsm.dto.preview.ProductPreview;
import com.akobir.wsm.dto.request.ProductRequest;
import com.akobir.wsm.dto.response.ProductResponse;
import com.akobir.wsm.entity.Product;
import com.akobir.wsm.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper implements Mapper<Product, ProductRequest, ProductResponse, ProductPreview> {

    private final OrganizationMapper organizationMapper;

    @Override
    public Product mapToEntity(ProductRequest productRequest) {
        if (productRequest == null) return null;

        Product product = new Product();
        product.setName(productRequest.name());
        return product;
    }

    @Override
    public ProductPreview mapToPreview(Product product) {
        if (product == null) return null;

        return new ProductPreview(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    @Override
    public ProductResponse mapToResponse(Product product) {
        if (product == null) return null;

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                organizationMapper.mapToPreview(product.getOrganization()),
                product.isDeleted(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
