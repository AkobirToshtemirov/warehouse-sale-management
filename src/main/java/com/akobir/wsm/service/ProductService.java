package com.akobir.wsm.service;

import com.akobir.wsm.dto.preview.ProductPreview;
import com.akobir.wsm.dto.request.ProductRequest;
import com.akobir.wsm.dto.response.ProductResponse;
import com.akobir.wsm.dto.response.RemainingProductByDateResponse;
import com.akobir.wsm.dto.response.RemainingProductByOrgResponse;
import com.akobir.wsm.dto.response.RemainingProductByWarehouseResponse;
import com.akobir.wsm.entity.Product;

import java.time.LocalDate;
import java.util.UUID;

public interface ProductService extends BaseService<Product, UUID, ProductRequest, ProductResponse, ProductPreview> {
    RemainingProductByOrgResponse getRemainingProductsByOrganization(UUID orgId);

    RemainingProductByWarehouseResponse getRemainingProductsByWarehouse(UUID warehouseId);

    RemainingProductByDateResponse getRemainingProductsByOrganizationBetweenDates(UUID orgId, LocalDate startDate, LocalDate endDate);

    RemainingProductByDateResponse getRemainingProductsByWarehouseBetweenDates(UUID warehouseId, LocalDate startDate, LocalDate endDate);

    Product getProductById(UUID id);
}

