package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.WarehousePreview;
import com.akobir.wsm.dto.request.WarehouseRequest;
import com.akobir.wsm.dto.response.WarehouseResponse;
import com.akobir.wsm.service.WarehouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @Operation(summary = "Create a new warehouse",
            description = "Creates a new warehouse with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid warehouse details provided")
    })
    @PostMapping
    public ResponseEntity<WarehouseResponse> createWarehouse(
            @Parameter(description = "Warehouse details to create", required = true)
            @RequestBody WarehouseRequest warehouseRequest) {
        WarehouseResponse response = warehouseService.create(warehouseRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing warehouse",
            description = "Updates the details of an existing warehouse by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse updated successfully"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found"),
            @ApiResponse(responseCode = "400", description = "Invalid warehouse details provided")
    })
    @PutMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResponse> updateWarehouse(
            @Parameter(description = "ID of the warehouse to update", required = true)
            @PathVariable UUID warehouseId,
            @Parameter(description = "Updated warehouse details", required = true)
            @RequestBody WarehouseRequest warehouseRequest) {
        WarehouseResponse response = warehouseService.update(warehouseId, warehouseRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a warehouse by ID",
            description = "Retrieves detailed information about a warehouse by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    @GetMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(
            @Parameter(description = "ID of the warehouse to retrieve", required = true)
            @PathVariable UUID warehouseId) {
        WarehouseResponse response = warehouseService.getById(warehouseId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all warehouses",
            description = "Retrieves a list of all warehouses.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouses retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<WarehousePreview>> getAllWarehouses() {
        List<WarehousePreview> previews = warehouseService.getAllPreviews();
        return ResponseEntity.ok(previews);
    }

    @Operation(summary = "Get warehouses by organization ID",
            description = "Retrieves a list of warehouses associated with a specific organization ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Organization not found")
    })
    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<WarehousePreview>> getWarehousesByOrganization(
            @Parameter(description = "ID of the organization to retrieve warehouses", required = true)
            @PathVariable UUID orgId) {
        List<WarehousePreview> previews = warehouseService.getAllByOrganization(orgId);
        return ResponseEntity.ok(previews);
    }

    @Operation(summary = "Delete a warehouse",
            description = "Deletes the warehouse associated with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Warehouse deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<Void> deleteWarehouse(
            @Parameter(description = "ID of the warehouse to delete", required = true)
            @PathVariable UUID warehouseId) {
        warehouseService.delete(warehouseId);
        return ResponseEntity.noContent().build();
    }
}
