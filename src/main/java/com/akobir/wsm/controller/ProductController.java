package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.ProductPreview;
import com.akobir.wsm.dto.request.ProductRequest;
import com.akobir.wsm.dto.response.ProductResponse;
import com.akobir.wsm.dto.response.RemainingProductByDateResponse;
import com.akobir.wsm.dto.response.RemainingProductByOrgResponse;
import com.akobir.wsm.dto.response.RemainingProductByWarehouseResponse;
import com.akobir.wsm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.create(productRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID productId,
                                                         @RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.update(productId, productRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID productId) {
        ProductResponse response = productService.getById(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductPreview>> getAllProductPreviews() {
        List<ProductPreview> previews = productService.getAllPreviews();
        return ResponseEntity.ok(previews);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/remaining/org/{orgId}")
    public ResponseEntity<RemainingProductByOrgResponse> getRemainingProductsByOrganization(@PathVariable UUID orgId) {
        RemainingProductByOrgResponse response = productService.getRemainingProductsByOrganization(orgId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/remaining/warehouse/{warehouseId}")
    public ResponseEntity<RemainingProductByWarehouseResponse> getRemainingProductsByWarehouse(@PathVariable UUID warehouseId) {
        RemainingProductByWarehouseResponse response = productService.getRemainingProductsByWarehouse(warehouseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/remaining/org/{orgId}/between-dates")
    public ResponseEntity<RemainingProductByDateResponse> getRemainingProductsByOrganizationBetweenDates(
            @PathVariable UUID orgId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        RemainingProductByDateResponse response = productService.getRemainingProductsByOrganizationBetweenDates(orgId, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/remaining/warehouse/{warehouseId}/between-dates")
    public ResponseEntity<RemainingProductByDateResponse> getRemainingProductsByWarehouseBetweenDates(
            @PathVariable UUID warehouseId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        RemainingProductByDateResponse response = productService.getRemainingProductsByWarehouseBetweenDates(warehouseId, startDate, endDate);
        return ResponseEntity.ok(response);
    }
}
