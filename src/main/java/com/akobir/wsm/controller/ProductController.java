package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.ProductPreview;
import com.akobir.wsm.dto.request.ProductRequest;
import com.akobir.wsm.dto.response.ProductResponse;
import com.akobir.wsm.dto.response.RemainingProductByDateResponse;
import com.akobir.wsm.dto.response.RemainingProductByOrgResponse;
import com.akobir.wsm.dto.response.RemainingProductByWarehouseResponse;
import com.akobir.wsm.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Create a new product",
            description = "Creates a new product with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product details provided")
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(description = "Product details to create", required = true)
            @RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.create(productRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing product",
            description = "Updates the details of an existing product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid product details provided")
    })
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID of the product to update", required = true)
            @PathVariable UUID productId,
            @Parameter(description = "Updated product details", required = true)
            @RequestBody ProductRequest productRequest) {
        ProductResponse response = productService.update(productId, productRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a product by ID",
            description = "Retrieves detailed information about a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "ID of the product to retrieve", required = true)
            @PathVariable UUID productId) {
        ProductResponse response = productService.getById(productId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all product previews",
            description = "Retrieves a list of previews for all products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product previews retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<ProductPreview>> getAllProductPreviews() {
        List<ProductPreview> previews = productService.getAllPreviews();
        return ResponseEntity.ok(previews);
    }

    @Operation(summary = "Delete a product",
            description = "Deletes the product associated with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to delete", required = true)
            @PathVariable UUID productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get remaining products by organization ID",
            description = "Retrieves remaining products associated with a specific organization ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remaining products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/remaining/org/{orgId}")
    public ResponseEntity<RemainingProductByOrgResponse> getRemainingProductsByOrganization(
            @Parameter(description = "ID of the organization to retrieve remaining products", required = true)
            @PathVariable UUID orgId) {
        RemainingProductByOrgResponse response = productService.getRemainingProductsByOrganization(orgId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get remaining products by warehouse ID",
            description = "Retrieves remaining products associated with a specific warehouse ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remaining products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    @GetMapping("/remaining/warehouse/{warehouseId}")
    public ResponseEntity<RemainingProductByWarehouseResponse> getRemainingProductsByWarehouse(
            @Parameter(description = "ID of the warehouse to retrieve remaining products", required = true)
            @PathVariable UUID warehouseId) {
        RemainingProductByWarehouseResponse response = productService.getRemainingProductsByWarehouse(warehouseId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get remaining products by organization ID between dates",
            description = "Retrieves remaining products associated with a specific organization ID between given dates.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remaining products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/remaining/org/{orgId}/between-dates")
    public ResponseEntity<RemainingProductByDateResponse> getRemainingProductsByOrganizationBetweenDates(
            @Parameter(description = "ID of the organization to retrieve remaining products", required = true)
            @PathVariable UUID orgId,
            @Parameter(description = "Start date for filtering remaining products", required = true)
            @RequestParam LocalDate startDate,
            @Parameter(description = "End date for filtering remaining products", required = true)
            @RequestParam LocalDate endDate) {
        RemainingProductByDateResponse response = productService.getRemainingProductsByOrganizationBetweenDates(orgId, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get remaining products by warehouse ID between dates",
            description = "Retrieves remaining products associated with a specific warehouse ID between given dates.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Remaining products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    @GetMapping("/remaining/warehouse/{warehouseId}/between-dates")
    public ResponseEntity<RemainingProductByDateResponse> getRemainingProductsByWarehouseBetweenDates(
            @Parameter(description = "ID of the warehouse to retrieve remaining products", required = true)
            @PathVariable UUID warehouseId,
            @Parameter(description = "Start date for filtering remaining products", required = true)
            @RequestParam LocalDate startDate,
            @Parameter(description = "End date for filtering remaining products", required = true)
            @RequestParam LocalDate endDate) {
        RemainingProductByDateResponse response = productService.getRemainingProductsByWarehouseBetweenDates(warehouseId, startDate, endDate);
        return ResponseEntity.ok(response);
    }
}
