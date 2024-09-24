package com.akobir.wsm.controller;

import com.akobir.wsm.dto.preview.WarehousePreview;
import com.akobir.wsm.dto.request.WarehouseRequest;
import com.akobir.wsm.dto.response.WarehouseResponse;
import com.akobir.wsm.service.WarehouseService;
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

    @PostMapping
    public ResponseEntity<WarehouseResponse> createWarehouse(@RequestBody WarehouseRequest warehouseRequest) {
        WarehouseResponse response = warehouseService.create(warehouseRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResponse> updateWarehouse(@PathVariable UUID warehouseId,
                                                             @RequestBody WarehouseRequest warehouseRequest) {
        WarehouseResponse response = warehouseService.update(warehouseId, warehouseRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(@PathVariable UUID warehouseId) {
        WarehouseResponse response = warehouseService.getById(warehouseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<WarehousePreview>> getAllWarehouses() {
        List<WarehousePreview> previews = warehouseService.getAllPreviews();
        return ResponseEntity.ok(previews);
    }

    @GetMapping("/organization/{orgId}")
    public ResponseEntity<List<WarehousePreview>> getWarehousesByOrganization(@PathVariable UUID orgId) {
        List<WarehousePreview> previews = warehouseService.getAllByOrganization(orgId);
        return ResponseEntity.ok(previews);
    }

    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable UUID warehouseId) {
        warehouseService.delete(warehouseId);
        return ResponseEntity.noContent().build();
    }
}
